package com.gu.common.zookeeper.client.impl;

import com.gu.common.zookeeper.client.ZookeeperClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author gu
 * @create 2020/12/7 下午2:36
 */

@Slf4j
public class CuratorZookeeperClient implements ZookeeperClient {

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    @Autowired
    private CuratorFramework curatorFramework;

    @Override
    public void create(String path, boolean ephemeral) {
        try {
            if (ephemeral) {
                curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(path);
            } else {
                curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath(path);
            }

        } catch (KeeperException.NodeExistsException e) {
            log.warn("ZNode " + path + " already exists, since we will only try to recreate a node on a session expiration" +
                    ", this duplication might be caused by a delete delay from the zk server, which means the old expired session" +
                    " may still holds this ZNode and the server just hasn't got time to do the deletion. In this case, " +
                    "we can just try to delete and create again.", e);
            this.delete(path);
            this.create(path, ephemeral);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public void create(String path, byte[] content, boolean ephemeral) {
        try {
            if (ephemeral) {
                curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath(path, content);
            } else {
                curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath(path, content);
            }

        } catch (KeeperException.NodeExistsException e) {
            log.warn("ZNode " + path + " already exists, since we will only try to recreate a node on a session expiration" +
                    ", this duplication might be caused by a delete delay from the zk server, which means the old expired session" +
                    " may still holds this ZNode and the server just hasn't got time to do the deletion. In this case, " +
                    "we can just try to delete and create again.", e);
            this.delete(path);
            this.create(path, ephemeral);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(String path) {
        try {
            curatorFramework.delete().forPath(path);
        } catch (KeeperException.NoNodeException e) {

        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public byte[] getContent(String path) {
        try {
            byte[] dataBytes = curatorFramework.getData().forPath(path);
            return (dataBytes == null || dataBytes.length == 0) ? null : dataBytes;
        } catch (KeeperException.NoNodeException e) {
            // ignore NoNode Exception.
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<String> getChildren(String path) {
        try {
            return curatorFramework.getChildren().forPath(path);
        } catch (KeeperException.NoNodeException e) {
            return null;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    @Override
    public boolean checkExists(String path) {
        try {
            return curatorFramework.checkExists().forPath(path) != null;
        } catch (KeeperException.NoNodeException e) {
            return false;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
