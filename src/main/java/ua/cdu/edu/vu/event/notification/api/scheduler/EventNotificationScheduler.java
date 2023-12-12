package ua.cdu.edu.vu.event.notification.api.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ua.cdu.edu.vu.event.notification.api.service.ApiClientService;
import ua.cdu.edu.vu.event.notification.api.service.EventService;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventNotificationScheduler {

    private final EventService eventService;
    private final ApiClientService apiClientService;

    @Scheduled(cron = "${scheduler.event-notifications.cron}")
    public void scheduleEventNotifications() {
        log.debug("Event notification scheduler task started");
        var apiClients = apiClientService.loadAllClients();
        eventService.processUpcomingEvents(apiClients);
        log.debug("Event notification scheduler task finished");
    }
}
