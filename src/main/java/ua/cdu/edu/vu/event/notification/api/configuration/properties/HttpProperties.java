package ua.cdu.edu.vu.event.notification.api.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;

@Data
@Configuration
@ConfigurationProperties(prefix = "http")
public class HttpProperties {

    public static final String DEFAULT = "default";

    private Map<String, ClientProperties> clients;

    public ClientProperties getDefault() {
        return clients.get(DEFAULT);
    }

    @Data
    public static class ClientProperties {

        private RetryProperties retry;

        @Data
        public static class RetryProperties {
            private int attempts;
            private Duration minBackoff;
        }

    }
}
