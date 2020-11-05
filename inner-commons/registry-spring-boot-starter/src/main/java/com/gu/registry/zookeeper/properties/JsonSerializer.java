package com.gu.registry.zookeeper.properties;

/**
 * @author FastG
 * @date 2020/11/3 17:37
 */
public interface JsonSerializer {

    /**
     * 序列化
     * 将obj对象中的相关数据取出，串行化为json字符串
     * @return
     */
    String serialize();

    /**
     * 反序列化
     * 将json字符串里的信息抽取，反馈到对象中
     * @param pack
     */
    void deserialize(String pack);
}
