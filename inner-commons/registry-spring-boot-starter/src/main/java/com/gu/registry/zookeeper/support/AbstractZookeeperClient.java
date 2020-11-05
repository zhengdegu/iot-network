package com.gu.registry.zookeeper.support;

import com.gu.common.utils.ConcurrentHashSet;
import com.gu.registry.zookeeper.ChildListener;
import com.gu.registry.zookeeper.DataListener;
import com.gu.registry.zookeeper.StateListener;
import com.gu.registry.zookeeper.ZookeeperClient;
import com.gu.registry.zookeeper.properties.URL;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;

/**
 * @author FastG
 * @date 2020/11/1 13:35
 */
@Slf4j
public abstract class AbstractZookeeperClient<TargetDataListener, TargetChildListener> implements ZookeeperClient {

    private final Set<StateListener> stateListeners = new CopyOnWriteArraySet<StateListener>();
    private final ConcurrentMap<String, ConcurrentMap<ChildListener, TargetChildListener>> childListeners = new ConcurrentHashMap<String, ConcurrentMap<ChildListener, TargetChildListener>>();
    private final ConcurrentMap<String, ConcurrentMap<DataListener, TargetDataListener>> listeners = new ConcurrentHashMap<String, ConcurrentMap<DataListener, TargetDataListener>>();
    private final Set<String> persistentExistNodePath = new ConcurrentHashSet<>();
    private volatile boolean closed = false;
    private final URL url;

    public AbstractZookeeperClient(URL url) {
        this.url = url;
    }

    @Override
    public void delete(String path) {
        //never mind if ephemeral
        persistentExistNodePath.remove(path);
        deletePath(path);
    }


    @Override
    public void create(String path, boolean ephemeral) {
        if (!ephemeral) {
            if (persistentExistNodePath.contains(path)) {
                return;
            }
            if (checkExists(path)) {
                persistentExistNodePath.add(path);
                return;
            }
        }
        int i = path.lastIndexOf('/');
        if (i > 0) {
            create(path.substring(0, i), false);
        }
        if (ephemeral) {
            createEphemeral(path);
        } else {
            createPersistent(path);
            persistentExistNodePath.add(path);
        }
    }

    @Override
    public void addStateListener(StateListener listener) {
        stateListeners.add(listener);
    }

    @Override
    public void removeStateListener(StateListener listener) {
        stateListeners.remove(listener);
    }

    public Set<StateListener> getSessionListeners() {
        return stateListeners;
    }

    @Override
    public List<String> addChildListener(String path, final ChildListener listener) {
        ConcurrentMap<ChildListener, TargetChildListener> listeners = childListeners.computeIfAbsent(path, k -> new ConcurrentHashMap<>());
        TargetChildListener targetListener = listeners.computeIfAbsent(listener, k -> createTargetChildListener(path, k));
        return addTargetChildListener(path, targetListener);
    }

    @Override
    public void addDataListener(String path, DataListener listener) {
        this.addDataListener(path, listener, null);
    }

    @Override
    public void addDataListener(String path, DataListener listener, Executor executor) {
        ConcurrentMap<DataListener, TargetDataListener> dataListenerMap = listeners.computeIfAbsent(path, k -> new ConcurrentHashMap<>());
        TargetDataListener targetListener = dataListenerMap.computeIfAbsent(listener, k -> createTargetDataListener(path, k));
        addTargetDataListener(path, targetListener, executor);
    }

    @Override
    public void removeDataListener(String path, DataListener listener) {
        ConcurrentMap<DataListener, TargetDataListener> dataListenerMap = listeners.get(path);
        if (dataListenerMap != null) {
            TargetDataListener targetListener = dataListenerMap.remove(listener);
            if (targetListener != null) {
                removeTargetDataListener(path, targetListener);
            }
        }
    }

    @Override
    public void removeChildListener(String path, ChildListener listener) {
        ConcurrentMap<ChildListener, TargetChildListener> listeners = childListeners.get(path);
        if (listeners != null) {
            TargetChildListener targetListener = listeners.remove(listener);
            if (targetListener != null) {
                removeTargetChildListener(path, targetListener);
            }
        }
    }

    protected void stateChanged(int state) {
        for (StateListener sessionListener : getSessionListeners()) {
            sessionListener.stateChanged(state);
        }
    }

    @Override
    public void close() {
        if (closed) {
            return;
        }
        closed = true;
        try {
            doClose();
        } catch (Throwable t) {
            log.warn(t.getMessage(), t);
        }
    }

    @Override
    public void create(String path, String content, boolean ephemeral) {
        if (checkExists(path)) {
            delete(path);
        }
        int i = path.lastIndexOf('/');
        if (i > 0) {
            create(path.substring(0, i), false);
        }
        if (ephemeral) {
            createEphemeral(path, content);
        } else {
            createPersistent(path, content);
        }
    }

    @Override
    public void create(String path, Object content, boolean ephemeral) {
        if (checkExists(path)) {
            delete(path);
        }
        int i = path.lastIndexOf('/');
        if (i > 0) {
            create(path.substring(0, i), false);
        }
        if (ephemeral) {
            createEphemeral(path, content);
        } else {
            createPersistent(path, content);
        }
    }
    @Override
    public String getContentString(String path) {
        if (!checkExists(path)) {
            return null;
        }
        return doGetContentString(path);
    }

    @Override
    public Object getContentObject(String path) {
        if (!checkExists(path)) {
            return null;
        }
        return doGetContentObject(path);
    }

    @Override
    public boolean checkExist(String path) {
        return this.checkExists(path);
    }

    protected abstract void doClose();

    protected abstract void createPersistent(String path);

    protected abstract void createEphemeral(String path);

    protected abstract void createPersistent(String path, String data);

    protected abstract void createPersistent(String path, Object data);

    protected abstract void createEphemeral(String path, String data);

    protected abstract void createEphemeral(String path, Object data);

    protected abstract boolean checkExists(String path);

    protected abstract TargetChildListener createTargetChildListener(String path, ChildListener listener);

    protected abstract List<String> addTargetChildListener(String path, TargetChildListener listener);
    @Deprecated
    protected abstract TargetDataListener createTargetDataListener(String path, DataListener listener);
    @Deprecated
    protected abstract void addTargetDataListener(String path, TargetDataListener listener);
    @Deprecated
    protected abstract void addTargetDataListener(String path, TargetDataListener listener, Executor executor);

    protected abstract void removeTargetDataListener(String path, TargetDataListener listener);

    protected abstract void removeTargetChildListener(String path, TargetChildListener listener);

    protected abstract String doGetContentString(String path);

    protected abstract Object doGetContentObject(String path);
    /**
     * we invoke the zookeeper client to delete the node
     *
     * @param path the node path
     */
    protected abstract void deletePath(String path);
}
