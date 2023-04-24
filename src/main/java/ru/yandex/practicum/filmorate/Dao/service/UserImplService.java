package ru.yandex.practicum.filmorate.Dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Dao.UserStorageDaoImpl;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserImplService {
    private final UserStorageDaoImpl userStorageDao;

    @Autowired
    UserImplService(UserStorageDaoImpl userStorageDao) {
        this.userStorageDao = userStorageDao;
    }

    public List<User> getUsers() {
        return userStorageDao.getUsers();
    }

    public Collection<User> userFindAllFriends(Integer id) throws NotFoundException {
        return userStorageDao.getFriends(id);
    }

    public Set<User> returnSameFriend(Integer id, Integer otherId)
            throws ClassNotFoundException {
        return userStorageDao.getCommonFriends(id, otherId);
    }

    public User userFindById(Integer id) throws NotFoundException {
        return userStorageDao.getUserId(id);
    }

    public User addUser(User user) throws ValidationException {
        return userStorageDao.addUser(user);
    }

    public Optional<User> updateUser(User user) throws ValidationException, NotFoundException {
        return userStorageDao.updateUser(user);
    }

    public Integer userAddFriend(Integer id, Integer friendId) throws NotFoundException {
        return userStorageDao.addFriend(id, friendId);
    }

    public void deleteFriend(Integer id, Integer friendId) throws NotFoundException {
        userStorageDao.deleteFriend(id, friendId);
    }
}
