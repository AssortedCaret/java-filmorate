package test;

import model.Film;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;

public class FIlmControllerTest {
    @Test
    public void shouldBeFullFilm(){
        Film film = new Film(1, "Avatar", "Blue people", 2020, 01, 01, 2.20);
        Assertions.assertNotNull(film.getId());
        Assertions.assertNotNull(film.getName());
        Assertions.assertEquals(film.getReleaseDate(), LocalDate.of(2020, 01, 01));
        Assertions.assertEquals(film.getDuration(), 2.20);
    }
}
