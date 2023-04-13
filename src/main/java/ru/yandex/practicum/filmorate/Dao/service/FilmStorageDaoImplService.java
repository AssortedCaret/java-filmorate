package ru.yandex.practicum.filmorate.Dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Dao.FilmStorageDaoImpl;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.SQLException;
import java.util.List;

@Service
public class FilmStorageDaoImplService {

    private final FilmStorageDaoImpl filmStorageDao;
    @Autowired
    public FilmStorageDaoImplService(FilmStorageDaoImpl filmStorageDao){
        this.filmStorageDao = filmStorageDao;
    }

    public List<Film> getPopularFilm(Integer count) {
        return filmStorageDao.getPopularFilm(count);
    }

    public Film get(Integer id) {
        return filmStorageDao.get(id);
    }

    public List<Film> getFilms(){
        return filmStorageDao.getAll();
    }

    public Film addFilm(Film film) throws ValidationException, CloneNotSupportedException, SQLException {
        return filmStorageDao.add(film);
    }

    public void filmAddLike(Integer id, Integer userId) throws ValidationException {
        filmStorageDao.filmAddLike(id, userId);
    }

    public Film updateFIlm(Film film) throws ValidationException, SQLException {
        return filmStorageDao.update(film);
    }

    public Film deleteFilmById(Integer id) {
        return filmStorageDao.delete(id);
    }

    public void filmDeleteLike(Integer id, Integer userId) throws ValidationException {
        filmStorageDao.filmDeleteLike(id, userId);
    }
}
