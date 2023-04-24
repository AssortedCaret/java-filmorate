package ru.yandex.practicum.filmorate.Dao.Interface;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaStorageDao {
    public Mpa addMpa(Mpa mpa);

    public List<Mpa> getAllMpa();

    public Mpa getMpaId(int id);
}
