package ru.yandex.practicum.filmorate.Dao.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Dao.GenreStorageDaoImpl;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@Service
public class GenreStorageDaoImplService {
    private final GenreStorageDaoImpl genreStorageDao;

    public GenreStorageDaoImplService(GenreStorageDaoImpl genreStorageDao) {
        this.genreStorageDao = genreStorageDao;
    }

    public Collection<Genre> getAllGenre() {
        return genreStorageDao.getAllGenre();
    }

    public Genre get(int id) {
        return genreStorageDao.getGenreId(id);
    }
}
