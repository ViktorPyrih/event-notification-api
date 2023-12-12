package ua.cdu.edu.vu.event.notification.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.ZonedDateTime;
import java.util.List;

public record EventRequest(@NotEmpty @Size(max = 64) String name,
                           String notes,
                           @NotEmpty @Size(max = 64) String key,
                           @NotNull @Future(message = "cannot be in the past") ZonedDateTime dateTime,
                           List<@Valid Reminder> reminders) {
}
