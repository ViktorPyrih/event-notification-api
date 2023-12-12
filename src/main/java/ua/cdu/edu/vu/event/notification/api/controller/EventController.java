package ua.cdu.edu.vu.event.notification.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ua.cdu.edu.vu.event.notification.api.domain.ApiClient;
import ua.cdu.edu.vu.event.notification.api.dto.EventRequest;
import ua.cdu.edu.vu.event.notification.api.dto.Event;
import ua.cdu.edu.vu.event.notification.api.domain.entity.EventEntity;
import ua.cdu.edu.vu.event.notification.api.dto.EventResponse;
import ua.cdu.edu.vu.event.notification.api.mapper.EventMapper;
import ua.cdu.edu.vu.event.notification.api.service.EventService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {

    private static final String EVENT_URL = "/api/v1/events/%d";

    private final EventMapper eventMapper;
    private final EventService eventService;

    @PostMapping
    public ResponseEntity<EventResponse> create(@Valid @RequestBody EventRequest eventRequest) {
        EventEntity eventEntity = eventMapper.convertToDomain(eventRequest);
        long eventId = eventService.create(eventEntity);
        String eventResourceUri = EVENT_URL.formatted(eventId);

        return ResponseEntity.created(URI.create(eventResourceUri))
                .body(EventResponse.builder()
                        .id(eventId)
                        .build());
    }

    @PutMapping("/{eventId}")
    public void update(@PathVariable long eventId, @Valid @RequestBody EventRequest eventRequest) {
        EventEntity eventEntity = eventMapper.convertToDomain(eventRequest);
        eventService.updateById(eventId, eventEntity);
    }

    @GetMapping("/{eventId}")
    public Event getById(@PathVariable long eventId) {
        return eventMapper.convertToDto(eventService.getById(eventId));
    }

    @GetMapping
    public List<Event> getAll(@RequestParam String key, @AuthenticationPrincipal ApiClient apiClient) {
        return eventService.getAllByKey(key, apiClient).stream()
                .map(eventMapper::convertToDto)
                .toList();
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> delete(@PathVariable long eventId) {
        eventService.deleteById(eventId);
        return ResponseEntity.noContent().build();
    }
}
