package ua.cdu.edu.vu.event.notification.api.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import ua.cdu.edu.vu.event.notification.api.configuration.properties.HttpProperties;
import ua.cdu.edu.vu.event.notification.api.domain.ApiClient;
import ua.cdu.edu.vu.event.notification.api.domain.Notification;
import ua.cdu.edu.vu.event.notification.api.exception.SendNotificationException;
import ua.cdu.edu.vu.event.notification.api.mapper.NotificationMapper;

import static java.util.Objects.nonNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestNotificationProducer implements NotificationProducer {

    private static final String X_API_KEY = "x-api-key";
    private static final String ERROR_SENDING_NOTIFICATION_MESSAGE = "Error sending notification for event with id: %d and client: %s";

    private final WebClient webClient;
    private final NotificationMapper notificationMapper;
    private final HttpProperties httpProperties;
    private final StringEncryptor encryptor;

    @Override
    public void sendNotification(Notification notification, ApiClient apiClient) throws SendNotificationException {
        var notificationPayload = notificationMapper.convertToDto(notification);
        var webHook = apiClient.webHook();
        var retryProperties = httpProperties.getDefault().getRetry();

        try {
            webClient.method(HttpMethod.valueOf(webHook.httpMethod()))
                    .uri(webHook.url())
                    .headers(httpHeaders -> enrichHeaders(httpHeaders, webHook))
                    .contentType(MediaType.parseMediaType(webHook.contentType()))
                    .bodyValue(notificationPayload)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.empty())
                    .toBodilessEntity()
                    .doOnSuccess(response -> handleResponse(response, apiClient))
                    .retryWhen(Retry.backoff(retryProperties.getAttempts(), retryProperties.getMinBackoff()))
                    .doOnError(e -> log.error("Request to API endpoint for client: {} failed", apiClient.clientId(), e))
                    .block();
        } catch (RuntimeException e) {
            String message = ERROR_SENDING_NOTIFICATION_MESSAGE.formatted(notification.eventId(), apiClient.clientId());
            throw new SendNotificationException(message, e);
        }
    }

    private void enrichHeaders(HttpHeaders httpHeaders, ApiClient.WebHook webHook) {
        if (nonNull(webHook.apiKey())) {
            httpHeaders.set(X_API_KEY, encryptor.decrypt(webHook.apiKey()));
        }
    }

    private void handleResponse(ResponseEntity<Void> response, ApiClient apiClient) {
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Notification sent successfully for client: {}, status: {}", apiClient.clientId(), response.getStatusCode());
        } else {
            log.error("Request to API endpoint for client: {} failed with status code: {}. No retries will be made",
                    apiClient.clientId(), response.getStatusCode());
        }
    }
}
