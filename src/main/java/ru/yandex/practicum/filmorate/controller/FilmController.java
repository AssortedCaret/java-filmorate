package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final FilmStorage inMemoryFilmStorage;
    private final FilmService filmService;
    @Autowired
    public FilmController(InMemoryFilmStorage inMemoryFilmStorage, FilmService filmService){
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.filmService = filmService;
    }

    @GetMapping(value = "/popular")
    public List<Film> returnSameFriend(@RequestParam(required = false, defaultValue = "10") Integer count) {
        return filmService.returnPopularFilm(count);
    }

    @GetMapping(value = "/{id}")
    public Film getFilmById(@PathVariable("id") Integer id) {
        return inMemoryFilmStorage.get(id);
    }

    @GetMapping
    public ArrayList<Film> getFilms(){
        return inMemoryFilmStorage.getAll();
    }

    @PostMapping
    public Film addFilm(@RequestBody Film film) throws ValidationException, CloneNotSupportedException {
        return inMemoryFilmStorage.add(film);
    }

    @PutMapping(value = "/{id}/like/{userId}")
    public Film filmAddLike(@PathVariable("id") Integer id, @PathVariable("userId")Integer userId)
            throws ValidationException {
        return filmService.addLike(id, userId);
    }

    @PutMapping
    public Film updateFIlm(@RequestBody Film film) throws ValidationException {
        return inMemoryFilmStorage.update(film);
    }

    @DeleteMapping(value = "/{id}")
    public Film deleteFilmById(@PathVariable("id") Integer id) {
        return inMemoryFilmStorage.delete(id);
    }

    @DeleteMapping(value = "/{id}/like/{userId}")
    public Film filmDeleteLike(@PathVariable("id") Integer id, @PathVariable("userId")Integer userId)
            throws ValidationException {
        return filmService.deleteLike(id, userId);
    }



}
