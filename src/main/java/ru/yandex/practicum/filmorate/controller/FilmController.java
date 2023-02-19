package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {
    HashMap<Integer, Film> films = new HashMap<Integer, Film>();
    int id = 1;
    private int putIdFilm(Film film){
        film.setId(id);
        id = id+1;
        return id;
    }

    private void validateFilm(@Valid Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))){
            log.debug("Не выполнены условия добавления пользователя. Пользователь не добавлен");
            throw new ValidationException("Не выполнены условия добавления пользователя. /n Убедитесь в " +
                    "правильности ввода данных.");
        }
        String description = film.getDescription();
        char[] c_arr = description.toCharArray();
        if(c_arr.length > 200){
            log.debug("Не выполнены условия добавления пользователя. Пользователь не добавлен");
            throw new ValidationException("Не выполнены условия добавления пользователя. /n Убедитесь в " +
                    "правильности ввода данных.");
        }
    }

    @PostMapping
    public Film addFilm(@RequestBody @Valid Film film) throws ValidationException {
        validateFilm(film);
        putIdFilm(film);
        try {
            films.put(film.getId(), film);
            log.info("Добавлен фильм: '{}'", film);
            return film;
        } catch (Exception e){
            log.debug("Не выполнены условия добавления фильма. Фильм не добавлен");
            throw new ValidationException("Не выполнены условия добавления фильма. /n Убедитесь в правильности ввода" +
                    "данных.");
        }
    }

    @PutMapping
    public Film updateFIlm(@RequestBody @Valid Film film) throws ValidationException {
        validateFilm(film);
        if(film.getId() > films.size()){
            log.debug("Не выполнены условия обновления фильма. Фильм не обновлен");
            throw new ValidationException("Не выполнены условия обновления фильма. /n Убедитесь в правильности ввода" +
                    "данных.");
        }
        films.put(film.getId(), film);
        log.info("Обновлен film '{}' на '{}'", film.getId(), film);
        return film;
    }

    @GetMapping
    public ArrayList<Film> getFilms(){
        log.info("Выведен список films");
        return new ArrayList<>(films.values());
    }
}
