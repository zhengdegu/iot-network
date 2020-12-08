package com.gu.common.zookeeper;

import lombok.Builder;

import java.io.Serializable;
import java.util.Properties;

/**
 * @author gu
 * @create 2020/12/7 下午3:22
 */
@Builder
public class ZookeeperInstance implements Serializable {

    private Properties nodeMetaData = new Properties();

    public Properties getNodeMetaData() {
        return nodeMetaData;
    }

    public void setNodeMetaData(Properties nodeMetaData) {
        this.nodeMetaData = nodeMetaData;
    }
}
