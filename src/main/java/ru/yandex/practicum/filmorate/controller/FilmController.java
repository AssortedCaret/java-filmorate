package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.Dao.service.FilmImplService;
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
    private final FilmImplService filmImplService;

    @Autowired
    public FilmController(FilmImplService filmImplService) {
        this.filmImplService = filmImplService;
    }

    @GetMapping(value = "/popular")
    public List<Film> getPopularFilm(@RequestParam(defaultValue = "10") Integer count) {
        return filmImplService.getPopularFilm(count);
    }

    @GetMapping(value = "/{id}")
    public Film getFilmById(@PathVariable("id") Integer id) {
        return filmImplService.get(id);
    }

    @GetMapping
    public List<Film> getFilms() {
        return filmImplService.getFilms();
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) throws ValidationException, CloneNotSupportedException, SQLException {
        return filmImplService.addFilm(film);
    }

    @PutMapping(value = "/{id}/like/{userId}")
    public void filmAddLike(@PathVariable("id") Integer id, @PathVariable("userId")Integer userId)
            throws ValidationException {
        filmImplService.filmAddLike(id, userId);
    }

    @PutMapping
    public Film updateFIlm(@RequestBody Film film) throws ValidationException, SQLException {
        return filmImplService.updateFIlm(film);
    }

    @DeleteMapping(value = "/{id}")
    public Film deleteFilmById(@PathVariable("id") Integer id) {
        return filmImplService.deleteFilmById(id);
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    public void filmDeleteLike(@PathVariable("id") Integer id, @PathVariable("userId")Integer userId)
            throws ValidationException {
        filmImplService.filmDeleteLike(id, userId);
    }
}
