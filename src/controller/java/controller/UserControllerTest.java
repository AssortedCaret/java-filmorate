package controller;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.User;
import javax.validation.*;
import java.time.LocalDate;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserControllerTest {
    private static Validator validator;
    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Test
    void validateBirthday() {
        User user = new User(1,"human@yes.ru", "Huuuman1", "Man",
                LocalDate.of(2220,01,01));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Add @ in email");
    }

    @Test
    void validateEmail() {
        User user = new User(1,"humanyes.ru", "Huuuman1", "Man", LocalDate.of(1990,01,01));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(0, violations.size(), "Email must contain @");
    }

    @Test
    void validateLogin() {
        User user = new User(1,"human@yes.ru", "", "Man", LocalDate.of(1990,01,01));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size(), "Login must be full");
    }
}
