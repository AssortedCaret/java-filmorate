package test;

import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.boot.test.context.SpringBootTest;
//import org.testng.annotations.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

@SpringBootTest
public class FIlmControllerTest {
   // @Test
    public void shouldBeFullFilm(){
        Film film = new Film(1,"Avatar", "Blue people",
                LocalDate.of(2020, 01, 01), 220);
        Assertions.assertNotNull(film.getId());
        Assertions.assertNotNull(film.getName());
        Assertions.assertEquals(film.getReleaseDate(), LocalDate.of(2020, 01, 01));
        Assertions.assertEquals(film.getDuration(), 2.20);
    }
}
