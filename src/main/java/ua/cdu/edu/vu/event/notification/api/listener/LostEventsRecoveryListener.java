package ua.cdu.edu.vu.event.notification.api.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ua.cdu.edu.vu.event.notification.api.service.ApiClientService;
import ua.cdu.edu.vu.event.notification.api.service.EventService;

@Slf4j
@Component
@RequiredArgsConstructor
public class LostEventsRecoveryListener {

    private final EventService eventService;
    private final ApiClientService apiClientService;

    @EventListener(ApplicationReadyEvent.class)
    public void recoverLostEvents() {
        var apiClients = apiClientService.loadAllClients();
        try {
            eventService.processLostEvents(apiClients);
        } catch (RuntimeException e) {
            log.error("Lost events recovery failed", e);
        }
    }
}
