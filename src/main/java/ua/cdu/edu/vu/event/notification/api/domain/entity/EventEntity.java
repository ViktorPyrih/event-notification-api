package ua.cdu.edu.vu.event.notification.api.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "event")
@EntityListeners(AuditingEntityListener.class)
public class EventEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, length = 30)
    private String name;
    private String notes;
    @Column(nullable = false, length = 36)
    private String key;
    @Column(nullable = false)
    private ZonedDateTime dateTime;
    @CreatedBy
    @Column(nullable = false, length = 30)
    private String createdBy;
    private boolean notified;

    @JoinColumn(name = "event_id")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReminderEntity> reminders;
}
