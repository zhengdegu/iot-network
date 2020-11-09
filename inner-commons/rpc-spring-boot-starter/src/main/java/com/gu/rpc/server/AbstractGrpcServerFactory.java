package com.gu.rpc.server;


import com.google.common.annotations.VisibleForTesting;
import com.gu.rpc.ControlRPCGrpc;
import com.gu.rpc.properties.GrpcServerProperties;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.health.v1.HealthCheckResponse;
import io.grpc.services.HealthStatusManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.unit.DataSize;

import static java.util.Objects.requireNonNull;

@Slf4j
public abstract class AbstractGrpcServerFactory<T extends ServerBuilder<T>> implements GrpcServerFactory {

    protected final GrpcServerProperties properties;
    @Autowired
    @VisibleForTesting
    HealthStatusManager healthStatusManager;
    private ControlRPCGrpc.ControlRPCImplBase service;


    public AbstractGrpcServerFactory(final GrpcServerProperties properties) {
        this.properties = requireNonNull(properties, "properties");

    }

    @Override
    public Server createServer() {
        final T builder = newServerBuilder();
        configure(builder);
        return builder.build();
    }


    protected abstract T newServerBuilder();


    protected void configure(final T builder) {
        configureServices(builder);
        configureKeepAlive(builder);
        configureLimits(builder);

    }


    protected void configureServices(final T builder) {

        if (this.properties.isHealthServiceEnabled()) {
            builder.addService(this.healthStatusManager.getHealthService());

        }

        if (service != null) {
            builder.addService(service);
            this.healthStatusManager.setStatus(service.getClass().getName(), HealthCheckResponse.ServingStatus.SERVING);
        }
    }


    protected void configureKeepAlive(final T builder) {
        if (this.properties.isEnableKeepAlive()) {
            throw new IllegalStateException("KeepAlive is enabled but this implementation does not support keepAlive!");
        }
    }

    protected void configureLimits(final T builder) {
        final DataSize maxInboundMessageSize = this.properties.getMaxInboundMessageSize();
        if (maxInboundMessageSize != null) {
            builder.maxInboundMessageSize((int) maxInboundMessageSize.toBytes());
        }
        final DataSize maxInboundMetadataSize = this.properties.getMaxInboundMetadataSize();
        if (maxInboundMetadataSize != null) {
            builder.maxInboundMetadataSize((int) maxInboundMetadataSize.toBytes());
        }
    }

    @Override
    public String getAddress() {
        return this.properties.getAddress();
    }

    @Override
    public int getPort() {
        return this.properties.getPort();
    }

    @Override
    public void addService(ControlRPCGrpc.ControlRPCImplBase service) {
        this.service = service;
    }

    @Override
    public void destroy() {
        this.healthStatusManager.clearStatus(service.getClass().getName());
    }

}
