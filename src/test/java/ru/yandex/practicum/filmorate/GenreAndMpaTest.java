package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.Dao.GenreStorageDaoImpl;
import ru.yandex.practicum.filmorate.Dao.MpaStorageDaoImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GenreAndMpaTest {
    private final GenreStorageDaoImpl genreStorageDaoImpl;
    private final MpaStorageDaoImpl mpaStorageDaoImpl;

    @Test
    public void testGetGenre() {
        assertEquals(6, genreStorageDaoImpl.getAllGenre().size());
    }

    @Test
    public void testGetGenreId() {
        assertEquals(1, genreStorageDaoImpl.getGenreId(1).getId());
    }

    @Test
    public void testGetMpa() {
        assertEquals(5, mpaStorageDaoImpl.getAllMpa().size());
    }

    @Test
    public void testGetMpaId() {
        assertEquals(1, genreStorageDaoImpl.getGenreId(1).getId());
    }
}
