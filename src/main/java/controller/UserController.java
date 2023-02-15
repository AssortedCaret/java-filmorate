package controller;

import exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;

@Controller
@Slf4j
public class UserController {
    ArrayList<User> users = new ArrayList<>();
    @PostMapping("/add-user")
    public void addUser(@RequestBody User user) throws ValidationException {
        if(user.getName() == null) {
            user.setName(user.getLogin());
            if (user.getEmail().length() > 0 && user.getEmail().contains("@") && !(user.getLogin().contains(" ")) &&
                    user.getLogin().length() > 0 && user.getBirthday().isBefore(LocalDate.now())) {
                users.add(user);
                log.info("Добавлен user: '{}'", user);
            } else
                log.debug("Не выполнены условия добавления пользователя. Пользователь не добавлен");
                throw new ValidationException("Не выполнены условия добавления пользователя. /n Убедитесь в " +
                        "правильности ввода данных.");
        }
    }

    @PostMapping("/update-user")
    public void updateFIlm(int id, User newUser) throws ValidationException {
        if(newUser.getName() == null) {
            newUser.setName(newUser.getLogin());
            if (newUser.getEmail().length() > 0 && newUser.getEmail().contains("@") && !(newUser.getLogin().contains(" ")) &&
                    newUser.getLogin().length() > 0 && newUser.getBirthday().isBefore(LocalDate.now())) {
                for (User user : users) {
                    if (id == user.getId()) {
                        user = newUser;
                        log.info("Обновлен user '{}' на '{}'", user, newUser);
                    }
                }
            } else
                log.debug("Не выполнены условия обновления пользователя. Пользователь не обновлен");
                throw new ValidationException("Не выполнены условия обновления пользователя. /n Убедитесь в " +
                    "правильности ввода данных.");
        }
    }

    @GetMapping("/get-users")
    public ArrayList getFilms(){
        log.info("Выведен список users");
        return users;
    }
}
