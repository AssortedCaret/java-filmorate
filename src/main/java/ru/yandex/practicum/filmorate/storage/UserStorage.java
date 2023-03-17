package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.ErrorResponse;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;

public interface UserStorage {
    public User addUser(User user) throws ValidationException;
    public User updateUser(User user) throws ValidationException, ErrorResponse, ClassNotFoundException;
    public ArrayList<User> getUsers();
}
