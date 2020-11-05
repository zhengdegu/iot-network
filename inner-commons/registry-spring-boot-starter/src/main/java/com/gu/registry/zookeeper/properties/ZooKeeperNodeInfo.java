package com.gu.registry.zookeeper.properties;

import cn.hutool.json.JSONUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author FastG
 * @date 2020/11/3 17:55
 */
@ConfigurationProperties(prefix = "spring.network")
public class ZooKeeperNodeInfo implements JsonSerializer {

    /**
     * 是否开启外网
     */
    private boolean open = false;

    private String id;

    private String name;

    private String version;

    private RpcNodeInfo rpcNodeInfo = new RpcNodeInfo();

    @Override
    public String serialize() {
        return JSONUtil.toJsonStr(this);
    }

    @Override
    public void deserialize(String pack) {
        ZooKeeperNodeInfo zooKeeperNodeInfo = JSONUtil.toBean(pack, this.getClass());
        this.open = zooKeeperNodeInfo.isOpen();
        this.id = zooKeeperNodeInfo.getId();
        this.name = zooKeeperNodeInfo.getName();
        this.version = zooKeeperNodeInfo.getName();
        this.rpcNodeInfo = zooKeeperNodeInfo.getRpcNodeInfo();
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public RpcNodeInfo getRpcNodeInfo() {
        return rpcNodeInfo;
    }

    public void setRpcNodeInfo(RpcNodeInfo rpcNodeInfo) {
        this.rpcNodeInfo = rpcNodeInfo;
    }

}
