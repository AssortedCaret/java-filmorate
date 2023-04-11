package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.Dao.service.GenreStorageDaoImplService;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;

@RestController
@RequestMapping("/genres")
public class GenreController {
    private final GenreStorageDaoImplService genreStorageDaoImplService;

    public GenreController(GenreStorageDaoImplService genreStorageDaoImplService) {
        this.genreStorageDaoImplService = genreStorageDaoImplService;
    }

    @GetMapping
    public Collection<Genre> getAllGenre() {
        return genreStorageDaoImplService.getAllGenre();
    }

    @GetMapping("/{id}")
    public Genre get(@PathVariable int id) {
        return genreStorageDaoImplService.get(id);
    }
}
