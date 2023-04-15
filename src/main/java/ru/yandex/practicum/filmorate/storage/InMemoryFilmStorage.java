package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final HashMap<Integer, Film> films = new HashMap<>();
    private Integer id = 1;

    @Override
    public Film add(@Valid Film film) throws ValidationException, CloneNotSupportedException {
        validateFilm(film);
        putIdFilm(film);
        if (films.containsKey(film.getId())) {
            log.error("Данный '{}' уже добавлен", film);
            throw new CloneNotSupportedException("Данный FilmId уже добавлен");
        }
        try {
            films.put(film.getId(), film);
            log.info("Добавлен фильм: '{}'", film);
            return film;
        } catch (Exception e) {
            log.error("Не выполнены условия добавления фильма. Фильм не добавлен");
            throw new ValidationException("Не выполнены условия добавления фильма. /n Убедитесь в правильности ввода" +
                    "данных.");
        }
    }

    @Override
    public Film update(@Valid Film film) throws ValidationException {
        validateFilm(film);
        if (film.getId() > films.size()) {
            log.error("Не выполнены условия обновления фильма. Фильм не обновлен");
            throw new ValidationException("Не выполнены условия обновления фильма. /n Убедитесь в правильности ввода" +
                    "данных.");
        }
        films.put(film.getId(), film);
        log.info("Обновлен film '{}' на '{}'", film.getId(), film);
        return film;
    }

    @Override
    public Film delete(Integer id) {
        Film film = films.get(id);
        films.remove(film);
        return film;
    }

    @Override
    public ArrayList<Film> getAll() {
        log.info("Выведен список films");
        return new ArrayList<>(films.values());
    }

    @Override
    public Film get(Integer id) {
        if (films.size() < id) {
            throw new NotFoundException("Такого id не существует");
        }
        return films.get(id);
    }

    @Override
    public HashMap<Integer, Film> getMap() {
        return films;
    }

    private Integer putIdFilm(Film film) {
        film.setId(id);
        id = id + 1;
        return id;
    }

    private void validateFilm(@Valid Film film) throws ValidationException {
        String description = film.getDescription();
        char[] carr = description.toCharArray();
        if (carr.length > 200) {
            log.error("Не выполнены условия добавления пользователя. Пользователь не добавлен");
            throw new ValidationException("Не выполнены условия добавления пользователя. /n Убедитесь в " +
                    "правильности ввода данных.");
        }
        if (film.getName() == "") {
            log.error("Не выполнены условия добавления пользователя. Пользователь не добавлен");
            throw new ValidationException("Не выполнены условия добавления пользователя. /n Убедитесь в " +
                    "правильности ввода данных.");
        }
        if (film.getDuration() <= 0) {
            log.error("Не выполнены условия добавления пользователя. Пользователь не добавлен");
            throw new ValidationException("Не выполнены условия добавления пользователя. /n Убедитесь в " +
                    "правильности ввода данных.");
        }
    }
}
