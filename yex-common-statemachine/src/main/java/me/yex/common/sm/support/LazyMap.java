package me.yex.common.sm.support;

import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentMap;

public abstract class LazyMap<K, V> {

    private ConcurrentMap<K, V> map = Maps.newConcurrentMap();

    public V get(K key) {
        V obj = this.map.get(key);
        if (obj != null) {
            return obj;
        } else {
            obj = this.load(key);
            V exists = obj == null ? null : this.map.putIfAbsent(key, obj);
            return exists == null ? obj : exists;
        }
    }

    public void put(K key, V value) {
        this.map.putIfAbsent(key, value);
    }

    protected abstract V load(K key);
}
