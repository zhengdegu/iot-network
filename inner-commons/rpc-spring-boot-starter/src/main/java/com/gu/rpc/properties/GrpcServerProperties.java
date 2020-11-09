package com.gu.rpc.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.DataSizeUnit;
import org.springframework.boot.convert.DurationUnit;
import org.springframework.util.unit.DataSize;
import org.springframework.util.unit.DataUnit;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author FastG
 * @date 2020/11/6 16:49
 */

@ConfigurationProperties("grpc.server")
public class GrpcServerProperties {
    /**
     * A constant that defines, that the server should listen to any IPv4 and IPv6 address.
     */
    public static final String ANY_IP_ADDRESS = "*";

    /**
     * A constant that defines, that the server should listen to any IPv4 address.
     */
    public static final String ANY_IPv4_ADDRESS = "0.0.0.0";

    /**
     * A constant that defines, that the server should listen to any IPv6 address.
     */
    public static final String ANY_IPv6_ADDRESS = "::";

    private String address = ANY_IP_ADDRESS;

    private int port = 9999;

    private String inProcessName;

    private boolean enableKeepAlive = false;
    @DurationUnit(ChronoUnit.SECONDS)
    private Duration keepAliveTime = Duration.of(60, ChronoUnit.SECONDS);

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration keepAliveTimeout = Duration.of(20, ChronoUnit.SECONDS);

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration permitKeepAliveTime = Duration.of(5, ChronoUnit.MINUTES);

    @DurationUnit(ChronoUnit.SECONDS)
    private boolean permitKeepAliveWithoutCalls = false;

    @DataSizeUnit(DataUnit.BYTES)
    private DataSize maxInboundMessageSize = null;

    @DataSizeUnit(DataUnit.BYTES)
    private DataSize maxInboundMetadataSize = null;
    private boolean healthServiceEnabled = true;

    private boolean reflectionServiceEnabled = true;

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

    public String getInProcessName() {
        return inProcessName;
    }

    public void setInProcessName(String inProcessName) {
        this.inProcessName = inProcessName;
    }

    public boolean isEnableKeepAlive() {
        return enableKeepAlive;
    }

    public void setEnableKeepAlive(boolean enableKeepAlive) {
        this.enableKeepAlive = enableKeepAlive;
    }

    public Duration getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(Duration keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public Duration getKeepAliveTimeout() {
        return keepAliveTimeout;
    }

    public void setKeepAliveTimeout(Duration keepAliveTimeout) {
        this.keepAliveTimeout = keepAliveTimeout;
    }

    public Duration getPermitKeepAliveTime() {
        return permitKeepAliveTime;
    }

    public void setPermitKeepAliveTime(Duration permitKeepAliveTime) {
        this.permitKeepAliveTime = permitKeepAliveTime;
    }

    public boolean isPermitKeepAliveWithoutCalls() {
        return permitKeepAliveWithoutCalls;
    }

    public void setPermitKeepAliveWithoutCalls(boolean permitKeepAliveWithoutCalls) {
        this.permitKeepAliveWithoutCalls = permitKeepAliveWithoutCalls;
    }

    public DataSize getMaxInboundMessageSize() {
        return maxInboundMessageSize;
    }

    public void setMaxInboundMessageSize(DataSize maxInboundMessageSize) {
        this.maxInboundMessageSize = maxInboundMessageSize;
    }

    public DataSize getMaxInboundMetadataSize() {
        return maxInboundMetadataSize;
    }

    public void setMaxInboundMetadataSize(DataSize maxInboundMetadataSize) {
        this.maxInboundMetadataSize = maxInboundMetadataSize;
    }

    public boolean isHealthServiceEnabled() {
        return healthServiceEnabled;
    }

    public void setHealthServiceEnabled(boolean healthServiceEnabled) {
        this.healthServiceEnabled = healthServiceEnabled;
    }

    public boolean isReflectionServiceEnabled() {
        return reflectionServiceEnabled;
    }

    public void setReflectionServiceEnabled(boolean reflectionServiceEnabled) {
        this.reflectionServiceEnabled = reflectionServiceEnabled;
    }


}
