package com.gu.common.zookeeper.client;

import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.zookeeper.Watcher;

/**
 * @author gu
 * @create 2020/12/4 下午4:50
 */
public interface ZookeeperClient {


    String createPerNode(String path) throws Exception;

    String createPerNode(String path, String content) throws Exception;

    String createPerSeqNode(String path) throws Exception;

    String createPerSeqNode(String path, String content) throws Exception;

    String createEphNode(String path) throws Exception;

    String createEphNode(String path, String content) throws Exception;

    String createEphSeqNode(String path) throws Exception;

    String createEphSeqNode(String path, String content) throws Exception;

}
