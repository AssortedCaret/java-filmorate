package ru.yandex.practicum.filmorate.Dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.Dao.Interface.FilmStorageDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import javax.validation.Valid;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Component
@Primary
public class FilmStorageDaoImpl implements FilmStorageDao {

    private final Logger log = LoggerFactory.getLogger(FilmStorageDaoImpl.class);
    private Integer id = 0;
    ArrayList<Integer> listIdFilms = new ArrayList<>();
    private final GenreStorageDaoImpl genreStorage;
    private final JdbcTemplate jdbcTemplate;
    private final MpaStorageDaoImpl mpaStorageDao;

    public FilmStorageDaoImpl(JdbcTemplate jdbcTemplate, GenreStorageDaoImpl genreStorage, MpaStorageDaoImpl mpa) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreStorage = genreStorage;
        this.mpaStorageDao = mpa;
    }

    /** add и update попробовал разные способы, рабочие не закомментил **/
    @Override
    public Film add(@Valid Film film) throws ValidationException, CloneNotSupportedException, SQLException {
        validateFilm(film);
        String request = "insert into film (film_id, name, release_date, description, duration, id) values (?, ?, ?, ?, ?, ?)";
        int filmId = getNewFilm();
        listIdFilms.add(filmId);
        film.setId(filmId);
        Mpa mpa = new Mpa(film.getMpa().getId(), film.getMpa().getName());
        jdbcTemplate.update(request,
                filmId,
                film.getName(),
                Date.valueOf(film.getReleaseDate()),
                film.getDescription(),
                film.getDuration(),
                mpa.getId());
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                String requestD = "insert into film_genre (film_id, genre_id) values (?,?)";
                jdbcTemplate.update(requestD, filmId, genre.getId());
            }
        }
        log.info("Фильм {} добавлен в таблицу", film);
        return get(film.getId());
    }

    @Override
    public Film update(@Valid Film film) throws ValidationException, SQLException {
        if (listIdFilms.size() < film.getId()){
            log.error("Передан неверный id. Фильм не обновлен");
            throw new NotFoundException("Не выполнены условия обновления фильма. /n Убедитесь в правильности ввода" +
                    "данных.");
        }
        String request = "update film set name = ?, release_date = ?, description = ?, duration= ?, id = ? WHERE film_id = ?";
        Mpa mpa = new Mpa(film.getMpa().getId(), film.getMpa().getName());
        jdbcTemplate.update(request,
                film.getName(),
                Date.valueOf(film.getReleaseDate()),
                film.getDescription(),
                film.getDuration(),
                mpa.getId(),
                film.getId());
        String requestD = "delete from film_genre where film_id = ?";
        jdbcTemplate.update(requestD, film.getId());
        if (film.getGenres() != null) {
            for (Genre genre : film.getGenres()) {
                String requestG = "insert into film_genre (film_id, genre_id) values (?,?)";
                try {
                    jdbcTemplate.update(requestG, film.getId(), genre.getId());
                } catch (DuplicateKeyException r){
                    log.error("Данный жанр уже добавлен");
                }
            }
        }
        log.info("Фильм {} обновлен в таблице", film);
        return get(film.getId());
    }

    @Override
    public Film delete(Integer id) {
        String request = "delete from film where film_id=?";
        List<Film> films = getAll();
        Film searchingFilm = null;
        for (Film film : films) {
            if (film.getId() == id)
               searchingFilm = film;
        }
        jdbcTemplate.update(request,
                searchingFilm.getId());
        log.info("Фильм {} удален из таблицы", id);
        return searchingFilm;
    }

    @Override
    public List<Film> getAll() {
        String sql = "select * from film";
        List<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
        for (Film film : films) {
            if (film.getGenres() != null) {
                for (Genre genre : film.getGenres()) {
                    String requestG = "insert into film_genre (film_id, genre_id) values (?,?)";
                    jdbcTemplate.update(requestG, film.getId(), genre.getId());
                }
            }
            if (film.getGenres().size() == 0) {
//                film.setGenres(0);
                log.error("Список фильмов пуст");
            }
        }
        log.info("Получены все фильмы");
        return films;

    }

    @Override
    public Film get(int id) {
        if (listIdFilms.size() < id){
            log.error("Передан неверный id. Фильм не обновлен");
            throw new NotFoundException("Не выполнены условия обновления фильма. /n Убедитесь в правильности ввода" +
                    "данных.");
        }
        String filmRows = "select * from film where film_id = ?";
        List<Film> films = jdbcTemplate.query(filmRows, (rs, rowNum) -> makeFilm(rs), id);
        if (films.isEmpty()) {
            return null;
        }
        return films.get(0);
    }

    @Override
    public Film filmAddLike(Integer filmId, Integer userId) {
        if (filmId <= 0 || userId <= 0){
            log.error("Передан неверный id. Лайк не добавлен");
            throw new NotFoundException("Не выполнены условия добавления лайка. /n Убедитесь в правильности ввода" +
                    "данных.");
        }
        Film film = get(filmId);
        String sqlFindFilm = "select * from film_like where film_id = ? and user_id = ?";
        SqlRowSet checkFilm = jdbcTemplate.queryForRowSet(sqlFindFilm, filmId, userId);
        if (!checkFilm.next()) {
            String sqlAddLikes = "insert into film_like (user_id, film_id) " +
                    "values (?, ?)";
            jdbcTemplate.update(sqlAddLikes, userId, filmId);
        }
        film.getLikes().add(userId);
        return film;
    }

    @Override
    public Film filmDeleteLike(Integer filmId, Integer userId) {
        if (filmId <= 0 || userId <= 0){
            log.error("Передан неверный id. Лайк не добавлен");
            throw new NotFoundException("Не выполнены условия добавления лайка. /n Убедитесь в правильности ввода" +
                    "данных.");
        }
        Film film = get(filmId);
        String deleteLikes = "delete from film_like where film_id = ? and user_id = ? ";
        jdbcTemplate.update(deleteLikes, filmId, userId);
        film.getLikes().remove(filmId);
        return film;
    }

    @Override
    public List<Film> getPopularFilm(Integer count) {
        String sql = "select f.*, " +
                "count(fl.film_id) " +
                "from film as f " +
                "left join film_like as fl on f.film_id = fl.film_id " +
                "group by f.film_id " +
                "order by count(fl.film_id) desc " +
                "limit ?";
        List<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs), count);
        if (films.isEmpty()) {
            log.info("Список популярных фильмов пуст");
            return Collections.emptyList();
        }
        log.info("Получен список популярных фильмов: {}", count);
        List<Film> checkFilm = new ArrayList<>();
        if (count == 1){
            checkFilm.add(films.get(0));
            return checkFilm;
        } else
            return films;
    }

    private Integer getNewFilm() throws SQLException {
        String sql = "select film_id from film order by id desc limit 1";
        Optional<Integer> filmId = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilmId(rs)).stream().findFirst();
        return filmId.map(aInteger -> aInteger + 1).orElse(1);
    }

    private Integer makeFilmId(ResultSet rs) throws SQLException {
        return rs.getInt("film_id");
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        Integer id = rs.getInt("film_id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        Date releaseDate = rs.getDate("release_date");
        Long duration = rs.getLong("duration");
        Mpa mpa = mpaStorageDao.getMpaId(rs.getInt("id"));
        List<Genre> genres = genreStorage.getGenreFilmId(id);
        Film film = new Film(id, name, releaseDate.toLocalDate(), description, duration, genres, mpa);
        return film;
    }

    private void validateFilm(@Valid Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))){
            log.error("Не выполнены условия добавления пользователя. Пользователь не добавлен");
            throw new ValidationException("Не выполнены условия добавления пользователя. /n Убедитесь в " +
                    "правильности ввода данных.");
        }
        String description = film.getDescription();
        char[] c_arr = description.toCharArray();
        if (c_arr.length > 200){
            log.error("Не выполнены условия добавления пользователя. Пользователь не добавлен");
            throw new ValidationException("Не выполнены условия добавления пользователя. /n Убедитесь в " +
                    "правильности ввода данных.");
        }
        if (film.getName() == ""){
            log.error("Не выполнены условия добавления пользователя. Пользователь не добавлен");
            throw new ValidationException("Не выполнены условия добавления пользователя. /n Убедитесь в " +
                    "правильности ввода данных.");
        }
        if (film.getDuration() <= 0){
            log.error("Не выполнены условия добавления пользователя. Пользователь не добавлен");
            throw new ValidationException("Не выполнены условия добавления пользователя. /n Убедитесь в " +
                    "правильности ввода данных.");
        }
    }
}
