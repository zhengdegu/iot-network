package com.gu.registry.zookeeper;

import com.gu.registry.zookeeper.properties.URL;

public interface NotifyListener {


    default void addServiceListener(URL url) {
    }

}