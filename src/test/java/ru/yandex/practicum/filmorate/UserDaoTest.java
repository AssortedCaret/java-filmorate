package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.Dao.UserStorageDaoImpl;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDaoTest {
    private final UserStorageDaoImpl userStorage;
    User user = new User(1, "user@user.ru", "Userattor", "User",
            LocalDate.of(1990, 04, 04));
    User newUser = new User(1, "user2@user.ru", "NewUserattor", "NewUser",
            LocalDate.of(1990, 04, 04));
    @Test
    public void testFindUserById() throws ValidationException {
        assertEquals(newUser, userStorage.getUserId(1));
    }

    @Test
    public void testUpdateUser() throws ValidationException {
        userStorage.updateUser(newUser);
        assertEquals(newUser, userStorage.getUserId(1));
    }

    @Test
    public void testGetUsers() throws ValidationException {
        User user = new User(1, "user@user.ru", "Userattor", "User",
                LocalDate.of(1990, 04, 04));
        userStorage.addUser(user);
        List<User> users = userStorage.getUsers();
        assertEquals(1, users.size());
    }

}
