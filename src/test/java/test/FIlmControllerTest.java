package test;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
public class FIlmControllerTest {
    @Test
    public void shouldBeFullFilm(){
        Film film = new Film(1,"Avatar", "Blue people",
                LocalDate.of(2020,05,10),  220);
        Assertions.assertNotNull(film.getId());
        Assertions.assertNotNull(film.getName());
        Assertions.assertEquals(film.getReleaseDate(), LocalDate.of(2020, 05, 10));
        Assertions.assertEquals(film.getDuration(), 2.20);
    }
}
