package com.gu.network.session;

import lombok.extern.slf4j.Slf4j;

/**
 * @author FastG
 * @date 2020/10/30 17:17
 */
@Slf4j
public class DefaultConnectionManager  extends AbstractMemoryConnectionManager{
    @Override
    void retrySendMessage(String drcId, Object message) {

    }
}
