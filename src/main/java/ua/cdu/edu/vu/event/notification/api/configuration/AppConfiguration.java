package ua.cdu.edu.vu.event.notification.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.reactive.function.client.WebClient;
import ua.cdu.edu.vu.event.notification.api.util.AuthorizationContextAuditorAware;

import java.time.Clock;

@Configuration
public class AppConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuthorizationContextAuditorAware();
    }

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
