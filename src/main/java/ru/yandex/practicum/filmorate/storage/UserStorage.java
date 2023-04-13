package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.ErrorResponse;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashMap;

public interface UserStorage extends Storage<User>{
    public User add(User user) throws ValidationException, CloneNotSupportedException;
    public User update(User user) throws ValidationException, ErrorResponse, NotFoundException;
    public User delete(Integer id);
    public User get(Integer id);
    public ArrayList<User> getAll();
    public HashMap<Integer, User> getMap();
}
