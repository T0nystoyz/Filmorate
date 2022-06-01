package ru.yandex.practicum.filmorate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Тесты из ТЗ требуют код ошибки 500
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidIdException extends RuntimeException {
    public InvalidIdException(String message) {
        super(message);
    }
}
