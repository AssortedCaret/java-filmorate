package ru.yandex.practicum.filmorate.Dao.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.Dao.MpaStorageDaoImpl;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Service
public class MpaStorageDaoImplService {
    private final MpaStorageDaoImpl mpaStorageDao;

    MpaStorageDaoImplService(MpaStorageDaoImpl mpaStorageDao) {
        this.mpaStorageDao = mpaStorageDao;
    }

    public List<Mpa> getMpa() {
        return mpaStorageDao.getAllMpa();
    }

    public Mpa getMpaId(int id) throws ValidationException {
        if (id > 5) {
            throw new NotFoundException("Такого id MPA не существует");
        }
        return mpaStorageDao.getMpaId(id);
    }
}
