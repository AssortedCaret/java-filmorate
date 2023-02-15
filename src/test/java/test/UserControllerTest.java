package test;

import model.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

public class UserControllerTest {
    @Test
    public void shouldBeFullUser(){
        User user = new User(1, "human@yes.ru", "Huuuman1", "Man", 1990, 01, 01);
        Assertions.assertNotNull(user.getId());
        Assertions.assertNotNull(user.getName());
        Assertions.assertEquals(user.getBirthday(), LocalDate.of(1990, 01, 01));
        Assertions.assertEquals(user.getLogin(), "Huuuman1");
        Assertions.assertEquals(user.getEmail(), "human@yes.ru");
    }
}
