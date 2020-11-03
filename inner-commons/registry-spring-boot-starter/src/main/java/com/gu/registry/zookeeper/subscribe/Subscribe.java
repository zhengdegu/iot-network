package com.gu.registry.zookeeper.subscribe;

import com.gu.registry.zookeeper.NotifyListener;
import com.gu.registry.zookeeper.properties.URL;

/**
 * @author FastG
 * @date 2020/11/2 11:28
 */
public interface Subscribe {

    void subscribe(URL url);

    void unsubscribe(URL url);
}
