package ua.cdu.edu.vu.event.notification.api.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiAdvice {

    private static final String FIELD_NAME_FIELD_MESSAGE_SEPARATOR = ": ";
    private static final String FIELD_ERRORS_SEPARATOR = ", ";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ProblemDetail problemDetail = e.getBody();
        problemDetail.setDetail(getDetail(e));
        return problemDetail;
    }

    private String getDetail(MethodArgumentNotValidException e) {
        return e.getFieldErrors().stream()
                .map(error -> String.join(FIELD_NAME_FIELD_MESSAGE_SEPARATOR, error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(FIELD_ERRORS_SEPARATOR));
    }
}
