package ua.cdu.edu.vu.event.notification.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.cdu.edu.vu.event.notification.api.domain.ApiClient;
import ua.cdu.edu.vu.event.notification.api.domain.Notification;
import ua.cdu.edu.vu.event.notification.api.domain.entity.EventEntity;
import ua.cdu.edu.vu.event.notification.api.exception.NotFoundException;
import ua.cdu.edu.vu.event.notification.api.mapper.EventMapper;
import ua.cdu.edu.vu.event.notification.api.producer.NotificationProducer;
import ua.cdu.edu.vu.event.notification.api.repository.EventRepository;

import java.time.Clock;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import static ua.cdu.edu.vu.event.notification.api.util.DateTimeUtils.truncateToNextMinute;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final NotificationProducer notificationProducer;
    private final Clock clock;

    public long create(EventEntity event) {
        event.setDateTime(event.getDateTime().truncatedTo(ChronoUnit.MINUTES));
        return eventRepository.save(event).getId();
    }

    public void updateById(long id, EventEntity event) {
        EventEntity storedEvent = getById(id);
        event.setId(id);
        event.setCreatedBy(storedEvent.getCreatedBy());
        create(event);
    }

    @Transactional(readOnly = true)
    public EventEntity getById(long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event with id: %d not found".formatted(id)));
    }

    @Transactional(readOnly = true)
    public List<EventEntity> getAllByKey(String key, ApiClient apiClient) {
        return eventRepository.findAllByKeyAndCreatedBy(key, apiClient.clientId());
    }

    public void requireEventExistsById(long id) {
        getById(id);
    }

    public void deleteById(long id) {
        requireEventExistsById(id);
        eventRepository.deleteById(id);
    }

    public void processUpcomingEvents(Map<String, ApiClient> apiClients) {
        ZonedDateTime currentDateTime = truncateToNextMinute(ZonedDateTime.now(clock));
        List<EventEntity> upcomingEvents = eventRepository.findAllUpcomingEvents(currentDateTime);

        processUpcomingEvents(upcomingEvents, apiClients);
    }

    public void processLostEvents(Map<String, ApiClient> apiClients) {
        ZonedDateTime currentDateTime = truncateToNextMinute(ZonedDateTime.now(clock));
        var lostEvents = eventRepository.findAllLostEvents(currentDateTime);
        processUpcomingEvents(lostEvents, apiClients);
    }

    private void processUpcomingEvents(List<EventEntity> upcomingEvents, Map<String, ApiClient> apiClients) {
        upcomingEvents.parallelStream()
                .forEach(event -> processUpcomingEvent(event, apiClients.get(event.getCreatedBy())));
    }

    private void processUpcomingEvent(EventEntity event, ApiClient apiClient) {
        Notification notification = eventMapper.convertToNotification(event);
        notificationProducer.sendNotification(notification, apiClient);
        eventRepository.markNotifiedById(event.getId());
    }

}
