package ru.yandex.practicum.filmorate.Dao.Interface;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface FilmStorageDao {
    public Film add(Film film) throws ValidationException, CloneNotSupportedException, SQLException;
    public Film update(Film film) throws ValidationException, SQLException;
    public Film delete(Integer id);
    public List<Film> getAll();
    public Film get(int id);
    public List <Film> getPopularFilm(Integer quantity);
    public Film filmAddLike(Integer filmId, Integer userId);
    public Film filmDeleteLike(Integer filmId, Integer userId);
}
