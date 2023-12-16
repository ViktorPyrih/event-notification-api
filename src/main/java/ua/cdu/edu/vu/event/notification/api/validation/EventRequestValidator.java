package ua.cdu.edu.vu.event.notification.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ua.cdu.edu.vu.event.notification.api.dto.EventRequest;
import ua.cdu.edu.vu.event.notification.api.dto.Reminder;

import java.time.Clock;
import java.time.ZonedDateTime;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class EventRequestValidator implements ConstraintValidator<ValidEventRequest, EventRequest> {

    private final Clock clock;

    @Override
    public boolean isValid(EventRequest value, ConstraintValidatorContext context) {
        if (isNull(value) || isNull(value.dateTime()) || CollectionUtils.isEmpty(value.reminders())) {
            return true;
        }

        return value.reminders().stream().allMatch(reminder -> isValid(value.dateTime(), reminder));
    }

    private boolean isValid(ZonedDateTime dateTime, Reminder reminder) {
        return ZonedDateTime.now(clock).isBefore(dateTime.minus(reminder.duration()));
    }
}
