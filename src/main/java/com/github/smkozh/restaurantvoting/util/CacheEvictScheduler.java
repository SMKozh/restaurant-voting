package com.github.smkozh.restaurantvoting.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheEvictScheduler {
    @Scheduled(cron = "@midnight")
    @CacheEvict(value = "restaurants", allEntries = true)
    public void clearCache() {
        log.info("Evict restaurants cache at midnight");
    }


}
