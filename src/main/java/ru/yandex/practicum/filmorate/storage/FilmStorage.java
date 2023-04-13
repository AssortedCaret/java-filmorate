package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;

public interface FilmStorage extends Storage<Film>{

    public Film add(Film film) throws ValidationException, CloneNotSupportedException;

    public Film update(Film film) throws ValidationException;

    public Film delete(Integer id);

    public ArrayList<Film> getAll();

    public Film get(Integer id);

    public HashMap<Integer, Film> getMap();
}
