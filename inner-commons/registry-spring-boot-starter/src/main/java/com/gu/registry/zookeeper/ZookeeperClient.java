package com.gu.registry.zookeeper;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author FastG
 * @date 2020/11/1 13:16
 */
public interface ZookeeperClient {

    /**
     * 创建节点
     * @param path 路径
     * @param ephemeral 永久还是临时
     */
    void create(String path, boolean ephemeral);

    /**
     * 删除接地那
     * @param path
     */
    void delete(String path);

    /**
     * 得到孩子节点
     * @param path 路径
     * @return
     */
    List<String> getChildren(String path);

    /**
     * 添加孩子节点监听
     * @param path
     * @param listener
     * @return
     */
    List<String> addChildListener(String path, ChildListener listener);

    /**
     * @param path:    directory. All of child of path will be listened.
     * @param listener
     */
    void addDataListener(String path, DataListener listener);

    /**
     * @param path:    directory. All of child of path will be listened.
     * @param listener
     * @param executor another thread
     */
    void addDataListener(String path, DataListener listener, Executor executor);

    void removeDataListener(String path, DataListener listener);

    void removeChildListener(String path, ChildListener listener);

    void addStateListener(StateListener listener);

    void removeStateListener(StateListener listener);

    boolean isConnected();

    void close();

    void create(String path, String content, boolean ephemeral);

    String getContent(String path);

    boolean checkExist(String path);
}