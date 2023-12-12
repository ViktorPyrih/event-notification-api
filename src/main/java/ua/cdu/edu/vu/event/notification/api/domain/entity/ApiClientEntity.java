package ua.cdu.edu.vu.event.notification.api.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import ua.cdu.edu.vu.event.notification.api.model.Role;

@Data
@Entity
@Table(name = "api_client")
public class ApiClientEntity {

    @Id
    private String clientId;
    @Column(nullable = false)
    private String clientSecret;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    private WebHook webHook;

    @Data
    @Embeddable
    public static class WebHook {
        @Column(nullable = false)
        private String httpMethod;
        @Column(nullable = false)
        private String contentType;
        @Column(nullable = false)
        private String url;
        private String apiKey;
    }
}
