package ru.yandex.practicum.filmorate.Dao.Interface;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

public interface UserStorageDao {
    User addUser(User user) throws ValidationException;

    Optional<User> updateUser(User user) throws ValidationException;

    List<User> getUsers();

    User getUserId(Integer id);

    Collection<User> getFriends(Integer id);

    Set<User> getCommonFriends(Integer userId, Integer otherId);

    Integer addFriend(Integer userId, Integer friendId);

    void deleteFriend(Integer userId, Integer friendId);
}