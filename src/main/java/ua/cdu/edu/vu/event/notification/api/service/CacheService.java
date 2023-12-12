package ua.cdu.edu.vu.event.notification.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheService {

    private final CacheManager cacheManager;

    public void evictCache() {
        log.debug("Received request to clear cache");
        cacheManager.getCacheNames()
                .forEach(cache -> requireNonNull(cacheManager.getCache(cache)).clear());
        log.debug("Cache was cleared");
    }
}
