package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse extends Throwable {
    String error;
    String description;
    public ErrorResponse(String error, String description) {
        this.error = error;
        this.description = description;
    }
    public String getError() {
        return error;
    }
    public String getDescription() {
        return description;
    }
}
