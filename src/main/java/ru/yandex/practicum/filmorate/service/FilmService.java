package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.comparator.NewComparator;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage inMemoryFilmStorage;
    Comparator<Film> comparator = new NewComparator();
    Set<Film> popularFilm = new TreeSet<>(comparator);

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
    }

    public Film addLike(Integer filmId, Integer userId) throws ValidationException {
        HashMap<Integer, Film> films;
        films = inMemoryFilmStorage.getMap();
        Film film = films.get(filmId);
        log.info("Метод отработал положительно в FilmService, addLike()");
        return film;
    }

    public Film deleteLike(Integer filmId, Integer userId) throws ValidationException {
        HashMap<Integer, Film> films;
        films = inMemoryFilmStorage.getMap();
        if (filmId < 0 || userId < 0) {
            log.error("Отсутствует указанный UserId в UserService, addFriend()");
            throw new NotFoundException("Film id < 0");
        }
        Film film = films.get(filmId);
        film.deleteLike(userId);
        inMemoryFilmStorage.update(film);
        log.info("Метод отработал положительно в FilmService, deleteLike()");
        return film;
    }

    public List<Film> returnPopularFilm(Integer count) {
        popularFilm.addAll(inMemoryFilmStorage.getMap().values());
        if (!(count == null))
            return popularFilm.stream().limit(count).collect(Collectors.toList());
        else
            return popularFilm.stream().collect(Collectors.toList());
    }
}
