package com.people.pulse.tech.task.config;

import com.people.pulse.tech.task.model.Product;
import java.util.logging.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledCacheCleaner {
    @CacheEvict(value = Product.CACHE_NAME, allEntries = true)
    @Scheduled(fixedRateString = "${caching.spring.products-ttl-ms}")
    public void emptyHotelsCache() {
        Logger.getLogger(ScheduledCacheCleaner.class.getName()).info("emptying Products cache");
    }
}