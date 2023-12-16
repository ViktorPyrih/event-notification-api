package ua.cdu.edu.vu.event.notification.api.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestControllerAdvice
public class ApiAdvice {

    private static final String NAME_MESSAGE_SEPARATOR = ": ";
    private static final String ALL_ERRORS_SEPARATOR = ", ";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ProblemDetail problemDetail = e.getBody();
        problemDetail.setDetail(getDetail(e));
        return problemDetail;
    }

    private String getDetail(MethodArgumentNotValidException e) {
        return Stream.concat(globalErrors(e), fieldErrors(e))
                .collect(Collectors.joining(ALL_ERRORS_SEPARATOR));
    }

    private Stream<String> globalErrors(MethodArgumentNotValidException e) {
        return e.getGlobalErrors().stream()
                .map(error -> String.join(NAME_MESSAGE_SEPARATOR, error.getObjectName(), error.getDefaultMessage()));
    }

    private Stream<String> fieldErrors(MethodArgumentNotValidException e) {
        return e.getFieldErrors().stream()
                .map(error -> String.join(NAME_MESSAGE_SEPARATOR, error.getField(), error.getDefaultMessage()));
    }
}
