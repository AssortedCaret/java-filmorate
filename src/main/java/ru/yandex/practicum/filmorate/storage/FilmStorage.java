package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

public interface FilmStorage {
    public Film addFilm(Film film) throws ValidationException;
    public Film updateFIlm(Film film) throws ValidationException;
    public ArrayList<Film> getFilms();
}
