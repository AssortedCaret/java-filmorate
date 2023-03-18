package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.ErrorResponse;
import ru.yandex.practicum.filmorate.exception.ValidationException;

import java.util.ArrayList;

public interface Storage<T> {
    T add(T data) throws ValidationException, CloneNotSupportedException;

    T update(T data) throws ValidationException, ErrorResponse;

    T get(Integer id);

    T delete(Integer id);

    ArrayList<T> getAll();
}
