package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    HashMap<Integer, User> users = new HashMap<Integer, User>();
    int id = 1;
    private int putIdUser(User user){
        user.setId(id);
        id = id+1;
        return id;
    }

    private void validateUser(@Valid User user) throws ValidationException {
        if (!(user.getEmail().length() > 0 && user.getEmail().contains("@") && !(user.getLogin().contains(" ")) &&
                user.getLogin().length() > 0 && user.getBirthday().isBefore(LocalDate.now()))){
            log.debug("Не выполнены условия добавления пользователя. Пользователь не добавлен");
            throw new ValidationException("Не выполнены условия добавления пользователя. /n Убедитесь в " +
                    "правильности ввода данных.");
        }
        if(user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    @PostMapping
    public User addUser(@RequestBody @Valid  User user) throws ValidationException {
        validateUser(user);
        putIdUser(user);
            try {
                users.put(user.getId(), user);
                log.info("Добавлен user: '{}'", user);
                return user;
            } catch (Exception e) {
                log.debug("Не выполнены условия добавления пользователя. Пользователь не добавлен");
                throw new ValidationException("Не выполнены условия добавления пользователя. /n Убедитесь в " +
                        "правильности ввода данных.");
            }
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid  User user) throws ValidationException {
        validateUser(user);
        if(user.getId() > users.size()){
            log.debug("Не выполнены условия добавления пользователя. Пользователь не добавлен");
            throw new ValidationException("Не выполнены условия добавления пользователя. /n Убедитесь в " +
                    "правильности ввода данных.");
        }
        users.put(user.getId(), user);
        log.info("Обновлен user '{}' на '{}'", user.getId(), user);
        return user;
    }

    @GetMapping
    public ArrayList<User> getUsers(){
        log.info("Выведен список users");
        return new ArrayList<>(users.values());
    }
}