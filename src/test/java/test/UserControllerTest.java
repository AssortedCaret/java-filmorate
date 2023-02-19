package test;

import ru.yandex.practicum.filmorate.model.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
public class UserControllerTest {
    @Test
    public void shouldBeFullUser(){
        ArrayList<User> users = new ArrayList<>();
        User user = new User(1,"human@yes.ru", "Huuuman1", "Man", LocalDate.of(1990,01,01));
        Assertions.assertNotNull(user.getId());
        Assertions.assertNotNull(user.getName());
        Assertions.assertEquals(user.getBirthday(), LocalDate.of(1990, 01, 01));
        Assertions.assertEquals(user.getLogin(), "Huuuman1");
        Assertions.assertEquals(user.getEmail(), "human@yes.ru");
    }
}
