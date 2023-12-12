package ua.cdu.edu.vu.event.notification.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.cdu.edu.vu.event.notification.api.service.CacheService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/cache")
public class CacheController {

    private final CacheService cacheService;

    @DeleteMapping
    public ResponseEntity<Void> evictCache() {
        cacheService.evictCache();
        return ResponseEntity.noContent().build();
    }
}
