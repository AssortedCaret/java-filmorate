package test;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class FIlmControllerTest {

    private static Validator validator;
    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.usingContext().getValidator();
    }

    @Test
    void validateName() {
        Film film = new Film(1,"", "Blue people",
                LocalDate.of(2020, 01, 01), 220);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(2, violations.size(), "Add @ in email");
    }

    @Test
    void validateDuration() {
        Film film = new Film(1,"Avatar", "Blue people",
                LocalDate.of(2020, 01, 01), -220);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        assertEquals(1, violations.size(), "Add @ in email");
    }
}
