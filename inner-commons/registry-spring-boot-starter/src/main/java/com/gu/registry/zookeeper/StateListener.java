package com.gu.registry.zookeeper;

public interface StateListener {

    int SESSION_LOST = 0;

    int CONNECTED = 1;

    int RECONNECTED = 2;

    int SUSPENDED = 3;

    int NEW_SESSION_CREATED = 4;

    void stateChanged(int connected);

}
