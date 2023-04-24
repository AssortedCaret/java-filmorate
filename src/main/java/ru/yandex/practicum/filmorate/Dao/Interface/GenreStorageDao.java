package ru.yandex.practicum.filmorate.Dao.Interface;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorageDao {
    public List<Genre> getAllGenre();

    public Genre getGenreId(int id);

    public List<Genre> getGenreFilmId(int id);
}
