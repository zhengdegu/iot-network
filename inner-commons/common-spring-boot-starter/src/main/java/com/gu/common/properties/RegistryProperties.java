package com.gu.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * @author gu
 * @create 2020/12/7 下午5:09
 */
@ConfigurationProperties(prefix = "spring.registry")
public class RegistryProperties {
    private String clusterName="coordinate_server_ls";
    private String nodeId="111";
    public String getNodeId() {
        return nodeId;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

}
