package ua.cdu.edu.vu.event.notification.api.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import ua.cdu.edu.vu.event.notification.api.model.TimeUnit;

@Data
@Entity
@Table(name = "reminder")
public class ReminderEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private Integer unitsBeforeStart;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TimeUnit units;
}
