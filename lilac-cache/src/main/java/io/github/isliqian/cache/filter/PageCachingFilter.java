package io.github.isliqian.cache.filter;
import io.github.isliqian.cache.CacheUtils;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;


public class PageCachingFilter extends SimplePageCachingFilter {
    public PageCachingFilter() {
    }

    protected CacheManager getCacheManager() {
        return CacheUtils.getCacheManager();
    }
}
