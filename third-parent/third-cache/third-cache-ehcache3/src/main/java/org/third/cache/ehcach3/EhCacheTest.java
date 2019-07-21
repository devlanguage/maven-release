package org.third.cache.ehcach3;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhCacheTest {
	static Logger logger = LoggerFactory.getLogger(EhCacheTest.class);

	public static void main(String[] args) throws InterruptedException {
		// Construct one CacheManager "preConfigured"
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
				.withCache("preConfigured", CacheConfigurationBuilder
						.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(100)).build())
				.build(true);// Initialize the cache
		// Get cache with alias
		Cache<Long, String> preConfiguredCache = cacheManager.getCache("preConfigured", Long.class, String.class);
		preConfiguredCache.put(1000L, "helloword");
		logger.info(preConfiguredCache.get(1000L));

	}

}
