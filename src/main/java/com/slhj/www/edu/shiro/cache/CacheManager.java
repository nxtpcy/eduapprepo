package com.slhj.www.edu.shiro.cache;

import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.ehcache.EhCacheManager;

public class CacheManager<K, V> implements Cache<K, V> {

	private EhCacheManager cacheManager;
	private Cache<K, V> cache = null;

	public Cache<K, V> getCache() {
		try {
			if (this.cache == null) {
				this.cache = this.cacheManager.getCache("shiro_cache");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return this.cache;
	}

	public EhCacheManager getCacheManager() {
		return this.cacheManager;
	}

	public void setCacheManager(EhCacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public void setCache(Cache<K, V> cache) {
		this.cache = cache;
	}

	public void clear() throws CacheException {
		// TODO Auto-generated method stub
		getCache().clear();
	}

	public V get(K arg0) throws CacheException {
		// TODO Auto-generated method stub
		return (V) getCache().get(arg0);
	}

	public Set<K> keys() {
		// TODO Auto-generated method stub
		return getCache().keys();
	}

	public V put(K arg0, V arg1) throws CacheException {
		// TODO Auto-generated method stub
		return (V) getCache().put(arg0, arg1);
	}

	public V remove(K arg0) throws CacheException {
		// TODO Auto-generated method stub
		return (V) getCache().remove(arg0);
	}

	public int size() {
		// TODO Auto-generated method stub
		return getCache().size();
	}

	public Collection<V> values() {
		// TODO Auto-generated method stub
		return getCache().values();
	}

}
