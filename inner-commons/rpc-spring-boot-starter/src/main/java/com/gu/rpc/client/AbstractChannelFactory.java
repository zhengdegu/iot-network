package com.gu.rpc.client;


import com.gu.rpc.properties.GrpcChannelProperties;
import com.gu.rpc.properties.GrpcClientProperties;
import io.grpc.Channel;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.unit.DataSize;

import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

/**
 * @author g130016
 */
@Slf4j
public abstract class AbstractChannelFactory<T extends ManagedChannelBuilder<T>> implements GrpcChannelFactory {

    private final GrpcClientProperties properties;
    private final Map<String, ManagedChannel> channels = new ConcurrentHashMap<>();
    private final Map<String, ConnectivityState> channelStates = new ConcurrentHashMap<>();
    private boolean shutdown = false;

    protected AbstractChannelFactory(GrpcClientProperties properties) {
        this.properties = requireNonNull(properties, "properties");
    }

    protected abstract T newChannelBuilder(String name);

    protected ManagedChannel newManagedChannel(final String name) {
        final T builder = newChannelBuilder(name);
        configure(builder, name);
        final ManagedChannel channel = builder.build();
        final Duration timeout = properties.getChannel(name).getImmediateConnectTimeout();
        if (!timeout.isZero()) {
            connectOnStartup(name, channel, timeout);
        }
        watchConnectivityState(name, channel);
        return channel;
    }

    @Override
    public Channel createChannel(String name) {
        final Channel channel;
        synchronized (this) {
            if (this.shutdown) {
                throw new IllegalStateException("GrpcChannelFactory is already closed!");
            }
            channel = this.channels.computeIfAbsent(name, this::newManagedChannel);
        }
        return channel;
    }

    protected final GrpcChannelProperties getPropertiesFor(final String name) {
        return this.properties.getChannel(name);
    }


    protected void configure(final T builder, final String name) {
        configureKeepAlive(builder, name);
        configureLimits(builder, name);
        configureCompression(builder, name);
    }


    protected void configureKeepAlive(final T builder, final String name) {
        final GrpcChannelProperties properties = getPropertiesFor(name);
        if (properties.isEnableKeepAlive()) {
            builder.keepAliveTime(properties.getKeepAliveTime().toNanos(), TimeUnit.NANOSECONDS)
                    .keepAliveTimeout(properties.getKeepAliveTimeout().toNanos(), TimeUnit.NANOSECONDS)
                    .keepAliveWithoutCalls(properties.isKeepAliveWithoutCalls());
        }
    }


    protected boolean isNonNullAndNonBlank(final String value) {
        return value != null && !value.trim().isEmpty();
    }


    protected void configureLimits(final T builder, final String name) {
        final GrpcChannelProperties properties = getPropertiesFor(name);
        final DataSize maxInboundMessageSize = properties.getMaxInboundMessageSize();
        if (maxInboundMessageSize != null) {
            builder.maxInboundMessageSize((int) maxInboundMessageSize.toBytes());
        }
    }


    protected void configureCompression(final T builder, final String name) {
        final GrpcChannelProperties properties = getPropertiesFor(name);
        if (properties.isFullStreamDecompression()) {
            builder.enableFullStreamDecompression();
        }
    }

    @Override
    public Map<String, ConnectivityState> getConnectivityState() {
        return Collections.unmodifiableMap(this.channelStates);
    }

    @Override
    public Channel getChannel(String name) {
        return this.channels.get(name);
    }

    protected void watchConnectivityState(final String name, final ManagedChannel channel) {
        final ConnectivityState state = channel.getState(false);
        this.channelStates.put(name, state);
        if (state != ConnectivityState.SHUTDOWN) {
            channel.notifyWhenStateChanged(state, () -> watchConnectivityState(name, channel));
        }
    }

    private void connectOnStartup(String name, ManagedChannel channel, Duration timeout) {
        log.debug("Initiating connection to channel {}", name);
        channel.getState(true);

        final CountDownLatch readyLatch = new CountDownLatch(1);
        waitForReady(channel, readyLatch);
        boolean connected;
        try {
            log.debug("Waiting for connection to channel {}", name);
            connected = !readyLatch.await(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            connected = false;
        }
        if (connected) {
            throw new IllegalStateException("Can't connect to channel " + name);
        }
        log.info("Successfully connected to channel {}", name);
    }

    private void waitForReady(ManagedChannel channel, CountDownLatch readySignal) {
        final ConnectivityState state = channel.getState(false);
        log.debug("Waiting for ready state. Currently in {}", state);
        if (state == ConnectivityState.READY) {
            readySignal.countDown();
        } else {
            channel.notifyWhenStateChanged(state, () -> waitForReady(channel, readySignal));
        }
    }


    @Override
    @PreDestroy
    public synchronized void close() {
        if (this.shutdown) {
            return;
        }
        this.shutdown = true;
        for (final ManagedChannel channel : this.channels.values()) {
            channel.shutdown();
        }
        try {
            final long waitLimit = System.currentTimeMillis() + 60_000; // wait 60 seconds at max
            for (final ManagedChannel channel : this.channels.values()) {
                int i = 0;
                do {
                    log.debug("Awaiting channel shutdown: {} ({}s)", channel, i++);
                } while (System.currentTimeMillis() < waitLimit && !channel.awaitTermination(1, TimeUnit.SECONDS));
            }
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            log.debug("We got interrupted - Speeding up shutdown process");
        } finally {
            for (final ManagedChannel channel : this.channels.values()) {
                if (!channel.isTerminated()) {
                    log.debug("Channel not terminated yet - force shutdown now: {} ", channel);
                    channel.shutdownNow();
                }
            }
        }
        final int channelCount = this.channels.size();
        this.channels.clear();
        this.channelStates.clear();
        log.debug("GrpcCannelFactory closed (including {} channels)", channelCount);
    }

    @Override
    public void close(String name) {
        if (channels.containsKey(name)) {
            this.channels.remove(name);
            this.channels.get(name).shutdownNow();
            this.channelStates.remove(name);
        }
    }
}
