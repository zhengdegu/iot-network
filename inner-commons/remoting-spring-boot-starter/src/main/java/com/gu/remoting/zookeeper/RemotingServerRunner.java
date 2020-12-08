package com.gu.remoting.zookeeper;

import com.gu.common.properties.RegistryProperties;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author gu
 * @create 2020/12/8 下午3:42
 */
public class RemotingServerRunner implements CommandLineRunner, DisposableBean {


    @Autowired
    private CuratorFramework curatorFramework;

    @Autowired
    private RegistryProperties registryProperties;

    private final AtomicBoolean initDone = new AtomicBoolean(false);

    @Override
    public void destroy() throws Exception {
        TreeCache treeCache = TreeCache.newBuilder(curatorFramework, "/" + registryProperties.getClusterName()).build();
        treeCache.start();
        treeCache.getListenable().addListener((c, event) -> {
            switch (event.getType()) {
                case INITIALIZED:

                    break;
                case NODE_REMOVED:

                    break;
                case NODE_ADDED:

                    break;
                case NODE_UPDATED:

                    break;
                default:
                    break;
            }
        }, Executors.newCachedThreadPool());

        while (!initDone.get()) {
            Thread.yield();
        }
    }

    @Override
    public void run(String... args) throws Exception {

    }

    private String extractNodeId(String path) {
        String[] s = path.split("/");
        return s[s.length - 1];
    }
}
