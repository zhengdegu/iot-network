package com.gu.registry.zookeeper.curator;

import com.gu.registry.zookeeper.ChildListener;
import com.gu.registry.zookeeper.properties.URL;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.zookeeper.WatchedEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

public class CuratorZookeeperClientTest {
    CuratorFramework client = null;
    private TestingServer zkServer;
    private CuratorZookeeperClient curatorClient;

    @BeforeEach
    public void setUp() throws Exception {
        zkServer = new TestingServer(2181, true);
        curatorClient = new CuratorZookeeperClient(new URL("127.0.0.1:2181",3000,50000,null,null));
        client = CuratorFrameworkFactory.newClient(zkServer.getConnectString(), new ExponentialBackoffRetry(1000, 3));
        client.start();
    }
    @Test
    public void testChildrenListener() throws InterruptedException {
        String path = "/com/gu/providers";
        curatorClient.create(path, false);
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        curatorClient.addTargetChildListener(path, new CuratorZookeeperClient.CuratorWatcherImpl() {

            @Override
            public void process(WatchedEvent watchedEvent) throws Exception {
                int intValue = watchedEvent.getType().getIntValue();
                countDownLatch.countDown();
            }
        });
        curatorClient.createPersistent(path + "/provider1");
        countDownLatch.await();
    }

    @Test
    public void testAddTargetDataListener() throws Exception {
        String listenerPath = "/com/gu/configuration";
        String path = listenerPath + "/data";
        String value = "vav";

        curatorClient.create(path + "/d.json", value, true);
        String valueFromCache = curatorClient.getContent(path + "/d.json");
        Assertions.assertEquals(value, valueFromCache);
        final AtomicInteger atomicInteger = new AtomicInteger(0);
        curatorClient.addTargetDataListener(listenerPath, new CuratorZookeeperClient.CuratorWatcherImpl() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                System.out.println("===" + event);
                atomicInteger.incrementAndGet();
            }
        });

        valueFromCache = curatorClient.getContent(path + "/d.json");
        Assertions.assertNotNull(valueFromCache);
        curatorClient.getClient().setData().forPath(path + "/d.json", "sdsdf".getBytes());
        curatorClient.getClient().setData().forPath(path + "/d.json", "dfsasf".getBytes());
        curatorClient.delete(path + "/d.json");
        curatorClient.delete(path);
        valueFromCache = curatorClient.getContent(path + "/d.json");
        Assertions.assertNull(valueFromCache);
        Thread.sleep(2000L);
        Assertions.assertTrue(9L >= atomicInteger.get());
        Assertions.assertTrue(2L <= atomicInteger.get());
    }

    @Test
    public void testCreateContent4Persistent() {
        String path = "/curatorTest4CrContent/content.data";
        String content = "createContentTest";
        curatorClient.delete(path);
        assertThat(curatorClient.checkExists(path), is(false));
        assertNull(curatorClient.getContent(path));

        curatorClient.create(path, content, false);
        assertThat(curatorClient.checkExists(path), is(true));
        assertEquals(curatorClient.getContent(path), content);
    }


    @Test
    public void testConnectedStatus() {
        curatorClient.createEphemeral("/testPath");
        boolean connected = curatorClient.isConnected();
        assertThat(connected, is(true));
    }
        @Test
    public void testCreateExistingPath() {
        curatorClient.create("/pathOne", false);
        curatorClient.create("/pathOne", false);
    }


    @Test
    public void testRemoveChildrenListener() {
        ChildListener childListener = mock(ChildListener.class);
        curatorClient.addChildListener("/children", childListener);
        curatorClient.removeChildListener("/children", childListener);
    }


    @Test
    public void testChildrenPath() {
        String path = "/com/gu/providers";
        curatorClient.create(path, false);
        curatorClient.create(path + "/provider1", false);
        curatorClient.create(path + "/provider2", false);

        List<String> children = curatorClient.getChildren(path);
        assertThat(children.size(), is(2));
    }

    @Test
    public void testCheckExists() {
        String path = "/com/gu/providers";
        curatorClient.create(path, false);
        assertThat(curatorClient.checkExists(path), is(true));
        assertThat(curatorClient.checkExists(path + "/exits"), is(false));
    }
}
