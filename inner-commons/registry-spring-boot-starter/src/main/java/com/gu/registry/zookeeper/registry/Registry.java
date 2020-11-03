package com.gu.registry.zookeeper.registry;

import com.gu.registry.zookeeper.properties.URL;

/**
 * @author FastG
 * @date 2020/11/2 11:01
 */
public interface Registry {


    /**
     * 注册服务
     * @param url
     */
    void register(URL url);

    void unregister(URL url);
}
