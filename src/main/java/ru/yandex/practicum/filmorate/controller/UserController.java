package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.Dao.service.UserStorageDaoImplService;
import ru.yandex.practicum.filmorate.exception.ErrorResponse;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserStorageDaoImplService userStorageDaoImplService;

    @Autowired
    UserController(UserStorageDaoImplService userStorageDaoImplService) {
        this.userStorageDaoImplService = userStorageDaoImplService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userStorageDaoImplService.getUsers();
    }

    @GetMapping(value = "/{id}/friends")
    public Collection<User> userFindAllFriends(@Valid @PathVariable("id") Integer id) throws NotFoundException {
        return userStorageDaoImplService.userFindAllFriends(id);
    }

    @GetMapping(value = "/{id}/friends/common/{otherId}")
    public Set<User> returnSameFriend(@Valid @PathVariable("id") Integer id, @PathVariable("otherId")Integer otherId)
            throws ClassNotFoundException {
        return userStorageDaoImplService.returnSameFriend(id, otherId);
    }

    @GetMapping(value = "/{id}")
    public User userFindById(@Valid @PathVariable ("id") Integer id) throws NotFoundException {
        return userStorageDaoImplService.userFindById(id);
    }

    @PostMapping
    public User addUser(@Valid @RequestBody User user) throws ValidationException, CloneNotSupportedException {
        return userStorageDaoImplService.addUser(user);
    }

    @PutMapping
    public Optional<User> updateUser(@Valid @RequestBody User user) throws ValidationException, NotFoundException {
        return userStorageDaoImplService.updateUser(user);
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public Integer userAddFriend(@Valid @PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId)
            throws NotFoundException {
        log.info("Метод отработал положительно в UserController, addFriend()");
        return userStorageDaoImplService.userAddFriend(id, friendId);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public void deleteFriend(@Valid @PathVariable("id") Integer id, @PathVariable("friendId")Integer friendId)
            throws NotFoundException {
        userStorageDaoImplService.deleteFriend(id, friendId);
    }
}
