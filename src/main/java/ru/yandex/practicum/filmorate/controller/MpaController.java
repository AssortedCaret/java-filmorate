package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.Dao.service.MpaStorageDaoImplService;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
public class MpaController {
    private final MpaStorageDaoImplService mpaStorageDaoImplService;

    MpaController(MpaStorageDaoImplService mpaStorageDaoImplService) {
        this.mpaStorageDaoImplService = mpaStorageDaoImplService;
    }

    @GetMapping
    public List<Mpa> getMpa() {
        log.info("Получены все mpa");
        return mpaStorageDaoImplService.getMpa();
    }

    @GetMapping("/{id}")
    public Mpa getMpaById(@PathVariable int id) throws ValidationException {
        if (id > 5) {
            log.error("Не выполнены условия обновления фильма. Фильм не обновлен");
            throw new NotFoundException("Такого id MPA не существует");
        }
        log.info("Получены mpa {}", id);
        return mpaStorageDaoImplService.getMpaId(id);
    }
}
