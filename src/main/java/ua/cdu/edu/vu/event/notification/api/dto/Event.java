package ua.cdu.edu.vu.event.notification.api.dto;

import java.time.ZonedDateTime;
import java.util.List;

public record Event(long id,
                    String name,
                    String notes,
                    String key,
                    ZonedDateTime dateTime,
                    List<Reminder> reminders) {
}
