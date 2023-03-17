package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage{

    private HashMap<Integer, User> users = new HashMap<>();
    private int id = 1;

    @Override
    public User addUser(User user) throws ValidationException {
        validateUser(user);
        putIdUser(user);
        try {
            users.put(user.getId(), user);
            log.info("Добавлен user: '{}'", user);
            return user;
        } catch (Exception e) {
            log.error("Не выполнены условия добавления пользователя. Пользователь не добавлен");
            throw new ValidationException("Не выполнены условия добавления пользователя. /n Убедитесь в " +
                    "правильности ввода данных.");
        }
    }

    @Override
    public User updateUser(User user) throws ValidationException, NotFoundException {
        validateUser(user);
        if(user.getId() > users.size()){
            log.error("Не выполнены условия добавления пользователя. Пользователь не добавлен");
            throw new NotFoundException("Отсутствует указанный UserId");
        }
            users.put(user.getId(), user);
        log.info("Обновлен user '{}' на '{}'", user.getId(), user);
        return user;
    }

    @Override
    public ArrayList<User> getUsers() {
        log.info("Выведен список users");
        return new ArrayList<>(users.values());
    }

    public HashMap<Integer, User> getMap(){
        return users;
    }

    private int putIdUser(User user){
        user.setId(id);
        id = id+1;
        return id;
    }

    private void validateUser(@Valid User user) throws ValidationException {
        if (!(user.getEmail().length() > 0 && user.getEmail().contains("@") && !(user.getLogin().contains(" ")) &&
                user.getLogin().length() > 0 && user.getBirthday().isBefore(LocalDate.now()))){
            log.error("Не выполнены условия добавления пользователя. Пользователь не добавлен");
            throw new ValidationException("Не выполнены условия добавления пользователя.");
        }
        if(user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
