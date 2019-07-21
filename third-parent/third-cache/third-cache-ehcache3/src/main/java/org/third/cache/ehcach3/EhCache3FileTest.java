package org.third.cache.ehcach3;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.xml.XmlConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EhCache3FileTest {
	static Logger logger = LoggerFactory.getLogger(EhCache3FileTest.class);

	public static void main(String[] args) throws InterruptedException {
		// 从配置文件创建配置对象
		Configuration xmlConf = new XmlConfiguration(EhCache3FileTest.class.getResource("ehcache3.xml"));
		// 创建缓存管理器
		CacheManager cacheManager = CacheManagerBuilder.newCacheManager(xmlConf);
		cacheManager.init();
		// 从缓存管理器中获取缓存
		Cache<Long, String> mycache1 = cacheManager.getCache("myCache1", Long.class, String.class);
		// 使用缓存
		mycache1.put(1L, "Hello world!");
		mycache1.get(1L);
		// 清空缓存，关闭缓存管理器
		mycache1.clear();
		cacheManager.close();

	}

}
