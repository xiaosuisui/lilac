package io.github.isliqian.sys.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CacheUtils {
    private static CacheManager cacheManager = (CacheManager)MicAppContext.getBean("cacheManager");
    private static final String SYS_CACHE = "sysCache";

    public CacheUtils() {
    }

    public static Object get(String key) {
        return get("sysCache", key);
    }

    public static void put(String key, Object value) {
        put("sysCache", key, value);
    }

    public static void remove(String key) {
        remove("sysCache", key);
    }

    public static Object get(String cacheName, String key) {
        Element element = getCache(cacheName).get(key);
        return element == null ? null : element.getObjectValue();
    }

    public static void put(String cacheName, String key, Object value) {
        Element element = new Element(key, value);
        getCache(cacheName).put(element);
    }

    public static void remove(String cacheName, String key) {
        getCache(cacheName).remove(key);
    }

    private static Cache getCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            cacheManager.addCache(cacheName);
            cache = cacheManager.getCache(cacheName);
            cache.getCacheConfiguration().setEternal(true);
        }

        return cache;
    }

    public static CacheManager getCacheManager() {
        return cacheManager;
    }
}
