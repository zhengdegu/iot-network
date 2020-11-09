package com.gu.rpc.properties;

import io.grpc.netty.shaded.io.grpc.netty.NegotiationType;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author FastG
 * @date 2020/11/9 11:54
 */

public class GrpcChannelProperties {
    private static final boolean DEFAULT_ENABLE_KEEP_ALIVE = false;
    private static final Duration DEFAULT_KEEP_ALIVE_TIME = Duration.of(60, ChronoUnit.SECONDS);
    private static final Duration DEFAULT_KEEP_ALIVE_TIMEOUT = Duration.of(20, ChronoUnit.SECONDS);
    private static final boolean DEFAULT_KEEP_ALIVE_WITHOUT_CALLS = false;
    private static final boolean DEFAULT_FULL_STREAM_DECOMPRESSION = false;
    private static final NegotiationType DEFAULT_NEGOTIATION_TYPE = NegotiationType.TLS;
    private static final Duration DEFAULT_IMMEDIATE_CONNECT = Duration.ZERO;
    private String address = "127.0.0.1";
    private int port = 9999;
    private Boolean enableKeepAlive;
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration keepAliveTime;
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration keepAliveTimeout;
    private Boolean keepAliveWithoutCalls;
    @DataSizeUnit(DataUnit.BYTES)
    private DataSize maxInboundMessageSize = null;
    private Boolean fullStreamDecompression;
    private NegotiationType negotiationType;
    private Duration immediateConnectTimeout;
    private String serverName = "network";

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Boolean getEnableKeepAlive() {
        return enableKeepAlive;
    }

    public Boolean getKeepAliveWithoutCalls() {
        return keepAliveWithoutCalls;
    }

    public Boolean getFullStreamDecompression() {
        return fullStreamDecompression;
    }

    public boolean isEnableKeepAlive() {
        return this.enableKeepAlive == null ? DEFAULT_ENABLE_KEEP_ALIVE : this.enableKeepAlive;
    }

    public void setEnableKeepAlive(final Boolean enableKeepAlive) {
        this.enableKeepAlive = enableKeepAlive;
    }

    public Duration getKeepAliveTime() {
        return this.keepAliveTime == null ? DEFAULT_KEEP_ALIVE_TIME : this.keepAliveTime;
    }

    public void setKeepAliveTime(final Duration keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public Duration getKeepAliveTimeout() {
        return this.keepAliveTimeout == null ? DEFAULT_KEEP_ALIVE_TIMEOUT : this.keepAliveTimeout;
    }

    public void setKeepAliveTimeout(final Duration keepAliveTimeout) {
        this.keepAliveTimeout = keepAliveTimeout;
    }

    public boolean isKeepAliveWithoutCalls() {
        return this.keepAliveWithoutCalls == null ? DEFAULT_KEEP_ALIVE_WITHOUT_CALLS : this.keepAliveWithoutCalls;
    }

    public void setKeepAliveWithoutCalls(final Boolean keepAliveWithoutCalls) {
        this.keepAliveWithoutCalls = keepAliveWithoutCalls;
    }

    public DataSize getMaxInboundMessageSize() {
        return this.maxInboundMessageSize;
    }

    public void setMaxInboundMessageSize(final DataSize maxInboundMessageSize) {
        if (maxInboundMessageSize == null || maxInboundMessageSize.toBytes() >= 0) {
            this.maxInboundMessageSize = maxInboundMessageSize;
        } else if (maxInboundMessageSize.toBytes() == -1) {
            this.maxInboundMessageSize = DataSize.ofBytes(Integer.MAX_VALUE);
        } else {
            throw new IllegalArgumentException("Unsupported maxInboundMessageSize: " + maxInboundMessageSize);
        }
    }

    public boolean isFullStreamDecompression() {
        return this.fullStreamDecompression == null ? DEFAULT_FULL_STREAM_DECOMPRESSION : this.fullStreamDecompression;
    }

    public void setFullStreamDecompression(final Boolean fullStreamDecompression) {
        this.fullStreamDecompression = fullStreamDecompression;
    }

    public NegotiationType getNegotiationType() {
        return this.negotiationType == null ? DEFAULT_NEGOTIATION_TYPE : this.negotiationType;
    }

    public void setNegotiationType(final NegotiationType negotiationType) {
        this.negotiationType = negotiationType;
    }

    public Duration getImmediateConnectTimeout() {
        return this.immediateConnectTimeout == null ? DEFAULT_IMMEDIATE_CONNECT : this.immediateConnectTimeout;
    }

    public void setImmediateConnectTimeout(final Duration immediateConnectTimeout) {
        if (immediateConnectTimeout.isNegative()) {
            throw new IllegalArgumentException("Timeout can't be negative");
        }
        this.immediateConnectTimeout = immediateConnectTimeout;
    }

    public void copyDefaultsFrom(final GrpcChannelProperties config) {
        if (this == config) {
            return;
        }
        if (this.address == null) {
            this.address = config.address;
        }
        if (this.enableKeepAlive == null) {
            this.enableKeepAlive = config.enableKeepAlive;
        }
        if (this.keepAliveTime == null) {
            this.keepAliveTime = config.keepAliveTime;
        }
        if (this.keepAliveTimeout == null) {
            this.keepAliveTimeout = config.keepAliveTimeout;
        }
        if (this.keepAliveWithoutCalls == null) {
            this.keepAliveWithoutCalls = config.keepAliveWithoutCalls;
        }
        if (this.maxInboundMessageSize == null) {
            this.maxInboundMessageSize = config.maxInboundMessageSize;
        }
        if (this.fullStreamDecompression == null) {
            this.fullStreamDecompression = config.fullStreamDecompression;
        }
        if (this.negotiationType == null) {
            this.negotiationType = config.negotiationType;
        }
        if (this.immediateConnectTimeout == null) {
            this.immediateConnectTimeout = config.immediateConnectTimeout;
        }

    }
}