package com.gu.common.zookeeper.client;

import java.util.List;

/**
 * zookeeper client基本的操作逻辑
 *
 * @author gu
 * @create 2020/12/4 下午4:50
 */
public interface ZookeeperClient {
    /**
     * 创建节点
     *
     * @param path      路径
     * @param ephemeral 是否临时
     */
    void create(String path, boolean ephemeral);

    /**
     * 创建节点 数据
     *
     * @param path      路径
     * @param content   数据
     * @param ephemeral 是否临时
     */
    void create(String path, byte[] content, boolean ephemeral);

    /**
     * 删除
     *
     * @param path 路径
     */
    void delete(String path);

    /**
     * 获取指定节点数据
     *
     * @param path 路径
     * @return 数据
     */
    byte[]  getContent(String path);

    /**
     * 获取孩子节点
     *
     * @param path 路径
     * @return 孩子节点集合
     */
    List<String> getChildren(String path);

    /**
     * 检查节点是否存在
     * @param path 节点
     * @return true
     */
    boolean checkExists(String path);
}
