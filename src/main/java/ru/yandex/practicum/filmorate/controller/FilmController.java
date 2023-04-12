package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.Dao.service.FilmStorageDaoImplService;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final FilmStorageDaoImplService filmStorageDaoImplService;
    @Autowired
    public FilmController(FilmStorageDaoImplService filmStorageDaoImplService){
        this.filmStorageDaoImplService = filmStorageDaoImplService;
    }

    @GetMapping(value = "/popular")
    public List<Film> getPopularFilm(@RequestParam(defaultValue = "10") Integer count) {
        return filmStorageDaoImplService.getPopularFilm(count);
    }

    @GetMapping(value = "/{id}")
    public Film getFilmById(@PathVariable("id") Integer id) {
        return filmStorageDaoImplService.get(id);
    }

    @GetMapping
    public List<Film> getFilms(){
        return filmStorageDaoImplService.getFilms();
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) throws ValidationException, CloneNotSupportedException, SQLException {
        return filmStorageDaoImplService.addFilm(film);
    }

    @PutMapping(value = "/{id}/like/{userId}")
    public void filmAddLike(@PathVariable("id") Integer id, @PathVariable("userId")Integer userId)
            throws ValidationException {
        filmStorageDaoImplService.filmAddLike(id, userId);
    }

    @PutMapping
    public Film updateFIlm(@RequestBody Film film) throws ValidationException, SQLException {
        return filmStorageDaoImplService.updateFIlm(film);
    }

    @DeleteMapping(value = "/{id}")
    public Film deleteFilmById(@PathVariable("id") Integer id) {
        return filmStorageDaoImplService.deleteFilmById(id);
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    public void filmDeleteLike(@PathVariable("id") Integer id, @PathVariable("userId")Integer userId)
            throws ValidationException {
        filmStorageDaoImplService.filmDeleteLike(id, userId);
    }
}
