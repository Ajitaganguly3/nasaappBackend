package com.nasaapp.wishlist.caching;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CacheEventLogger implements CacheEventListener<Object, Object> {

	private Logger logger = LoggerFactory.getLogger(CacheEventListener.class);

	@Override
	public void onEvent(CacheEvent<? extends Object, ? extends Object> cacheEvent) {
		logger.info("Cache added with [key = {}, Old Value = {}, New Value = {}", cacheEvent.getKey(),
				cacheEvent.getOldValue(), cacheEvent.getNewValue());
	}

}
