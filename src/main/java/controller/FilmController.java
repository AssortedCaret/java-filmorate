package controller;

import exception.ValidationException;
import lombok.extern.slf4j.Slf4j;

import model.Film;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.time.LocalDate;
import java.util.ArrayList;

@Controller
@Slf4j
public class FilmController {
    ArrayList<Film> films = new ArrayList<>();
    private final LocalDate earlyRealise = LocalDate.parse("28.12.1895");
    @PostMapping("/add-film")
    public void addFilm(@RequestBody Film film) throws ValidationException {
        if(film.getName().length() > 0 && film.getDescription().length() <= 200
        && film.getReleaseDate().isAfter(earlyRealise) && film.getDuration() > 0) {
            films.add(film);
            log.info("Добавлен фильм: '{}'", film);
        } else {
            log.debug("Не выполнены условия добавления фильма. Фильм не добавлен");
            throw new ValidationException("Не выполнены условия добавления фильма. /n Убедитесь в правильности ввода" +
                    "данных.");

        }
    }

    @PostMapping("/update-film")
    public void updateFIlm(int id, Film newFilm) throws ValidationException {
        if(newFilm.getName().length() > 0 && newFilm.getDescription().length() <= 200
                && newFilm.getReleaseDate().isAfter(earlyRealise) && newFilm.getDuration() > 0) {
            for (Film film : films) {
                if (id == film.getId())
                    film = newFilm;
            }
            log.info("Обновлен фильм: '{}'", newFilm);
        } else
            log.debug("Не выполнены условия обновления фильма. Фильм не обновлен");
            throw new ValidationException("Не выполнены условия обновления фильма. /n Убедитесь в правильности ввода" +
                    "данных.");
    }

    @GetMapping("/get-films")
    public ArrayList getFilms(){
        log.info("Выведен список фильмов");
        return films;
    }
}
