package ru.yandex.practicum.filmorate.Dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Dao.Interface.GenreStorageDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Component
public class GenreStorageDaoImpl implements GenreStorageDao {
    private final Logger log = LoggerFactory.getLogger(FilmStorageDaoImpl.class);

    private final JdbcTemplate jdbcTemplate;

    public GenreStorageDaoImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getAllGenre() {
        /**
         * 3-й способ
         */
        String sql = "select * from genres";
        List<Genre> genres = jdbcTemplate.query(sql, (rs, rowNum) -> makeGenre(rs));
        log.info("Получены все жанры");
        if(genres.isEmpty())
            log.error("Список жанров пуст");
        return genres;
    }

    @Override
    public Genre getGenreId(int id) {
        if (id > 6) {
            log.error("Передан неверный genreId.");
            throw new NotFoundException("Такого id не существует.");
        }
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("select * from genres where id = ?", id);
        Genre genre;
        if (genreRows.next()) {
             genre = new Genre(
                    genreRows.getInt("id"),
                    genreRows.getString("name"));
            if (genre.getId() == id) {
                log.info("Найден жанр: {} {}", genre.getId(), genre.getName());
                return genre;
            }
        } else {
            log.info("Жанр с идентификатором {} не найден.", id);
            return null;
        }
        return null;
    }

    @Override
    public List<Genre> getGenreFilmId(int id) {
        String str = "select * from genres where id in (select genre_id from film_genre where film_id = ?)";
        List<Genre> genres = jdbcTemplate.query(str, (rs, rowNum) -> makeGenre(rs), id);
        if (genres.isEmpty()) {
            return Collections.emptyList();
        }
        return genres;
    }

    private Genre makeGenre(ResultSet res) throws SQLException {
        Integer id = res.getInt("id");
        String name = res.getString("name");
        return new Genre(id, name);
    }
}
