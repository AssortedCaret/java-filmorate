package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.Dao.FilmStorageDaoImpl;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDaoTest {

    private final FilmStorageDaoImpl filmStorage;

    @Test
    public void testFindFilmById() throws ValidationException, SQLException, CloneNotSupportedException {
        List<Genre> genres = new ArrayList<>();
        Film film = new Film(1, "Film", LocalDate.of(2000, 01, 01),
                "new Film test", 100L, genres, new Mpa(1, "G"));
        filmStorage.add(film);
        assertEquals(film, filmStorage.get(1));
        filmStorage.delete(1);
    }

    @Test
    public void testUpdateFilm() throws ValidationException, SQLException, CloneNotSupportedException {
        List<Genre> genres = new ArrayList<>();
        Film film = new Film(1, "Film", LocalDate.of(2000, 01, 01),
                "new Film test", 100L, genres, new Mpa(1, "G"));
        Film newFilm = new Film(1, "NewFilm", LocalDate.of(2000, 01, 01),
                "new Film test", 100L, genres, new Mpa(1, "G"));
        filmStorage.add(film);
        filmStorage.update(newFilm);
        assertEquals(newFilm, filmStorage.get(1));
        filmStorage.delete(1);
    }

    @Test
    public void testGetFilms() throws ValidationException, SQLException, CloneNotSupportedException {
        Film film = new Film(1, "Film", LocalDate.of(2000, 01, 01),
                "new Film test", 100L, null, new Mpa(1, "12"));
        filmStorage.add(film);
        List<Film> films = filmStorage.getAll();
        assertEquals(1, films.size());
        filmStorage.delete(1);
    }

    @Test
    public void testDeleteFilms() throws ValidationException, SQLException, CloneNotSupportedException {
        Film film = new Film(1, "Film", LocalDate.of(2000, 01, 01),
                "new Film test", 100L, null, new Mpa(1, "12"));
        filmStorage.add(film);
        filmStorage.delete(1);
        List<Film> films = filmStorage.getAll();
        assertEquals(0, films.size());
    }
}
