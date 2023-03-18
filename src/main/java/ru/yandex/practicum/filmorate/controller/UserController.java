package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exception.ErrorResponse;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final UserStorage inMemoryUserStorage;
    private final UserService userService;

    @Autowired
    UserController(InMemoryUserStorage inMemoryUserStorage, UserService userService){
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.userService = userService;
    }

    @GetMapping
    public ArrayList<User> getUsers(){
        return inMemoryUserStorage.getAll();
    }

    @GetMapping(value = "/{id}/friends")
    public List<User> userFindAllFriends(@PathVariable("id") Integer id) throws NotFoundException {
        return userService.returnsFriend(id);
    }

    @GetMapping(value = "/{id}/friends/common/{otherId}")
    public List<User> returnSameFriend(@PathVariable("id") Integer id, @PathVariable("otherId")Integer otherId)
            throws ClassNotFoundException{
        return userService.returnSameFriend(id, otherId);
    }

    @GetMapping(value = "/{id}")
    public User userFindById(@PathVariable ("id") Integer id) throws NotFoundException {
        return userService.getUser(id);
    }

    @PostMapping
    public User addUser(@RequestBody User user) throws ValidationException, CloneNotSupportedException {
        return inMemoryUserStorage.add(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) throws ValidationException, NotFoundException, ErrorResponse {
        return inMemoryUserStorage.update(user);
    }

    @PutMapping(value = "/{id}/friends/{friendId}")
    public User userAddFriend(@PathVariable("id") Integer id, @PathVariable("friendId") Integer friendId)
            throws NotFoundException {
        log.info("Метод отработал положительно в UserController, addFriend()");
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping(value = "/{id}")
    public User deleteFilmById(@PathVariable("id") Integer id) {
        return inMemoryUserStorage.delete(id);
    }

    @DeleteMapping(value = "/{id}/friends/{friendId}")
    public void userDeleteFriend(@PathVariable("id") Integer id, @PathVariable("friendId")Integer friendId)
            throws NotFoundException {
        userService.deleteFriend(id, friendId);
    }
}
