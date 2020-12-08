package com.gu.common.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;

/**
 * @author gu
 * @create 2020/12/7 下午3:53
 */
@Slf4j
public abstract class CuratorConnectionStateListener implements ConnectionStateListener {


    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
        this.stateChanged(curatorFramework, connectionState);
    }

    /**
     *
     * @param curatorFramework
     * @param connectionState
     */
    protected abstract void stateChangedOverride(CuratorFramework curatorFramework, ConnectionState connectionState);
}
