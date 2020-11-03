package com.gu.registry.zookeeper.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author FastG
 * @date 2020/11/1 13:41
 */
@Setter
@Getter
@AllArgsConstructor
public class URL {

    private String connectString = "127.0.0.1:2181";
    private Integer timeout = 5 * 1000;
    private Integer sessionExpireMs = 60 * 1000;
    private String username;
    private String password;
    private String path = "/network";
    private ZookeeperInstance instance = new ZookeeperInstance();


    public URL(String connectString, Integer timeout, Integer sessionExpireMs, String username, String password) {
        this.connectString = connectString;
        this.password = password;
        this.username = username;
        this.timeout = timeout;
        this.sessionExpireMs = sessionExpireMs;
    }

    public String getAuthority() {
        if (StringUtils.isEmpty(username)
                && StringUtils.isEmpty(password)) {
            return null;
        }
        return (username == null ? "" : username)
                + ":" + (password == null ? "" : password);
    }


    public static class ZookeeperInstance {

        private String id;

        private String name;

        private Map<String, String> metadata = new HashMap<>();

        @SuppressWarnings("unused")
        private ZookeeperInstance() {
        }

        public ZookeeperInstance(String id, String name, Map<String, String> metadata) {
            this.id = id;
            this.name = name;
            this.metadata = metadata;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, String> getMetadata() {
            return this.metadata;
        }

        public void setMetadata(Map<String, String> metadata) {
            this.metadata = metadata;
        }

        @Override
        public String toString() {
            return "ZookeeperInstance{" + "id='" + this.id + '\'' + ", name='" + this.name
                    + '\'' + ", metadata=" + this.metadata + '}';
        }

    }
}
