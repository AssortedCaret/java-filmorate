package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage{

    private final HashMap<Integer, Film> films = new HashMap<>();
    private Integer id = 1;
    @Override
    public Film addFilm(@Valid Film film) throws ValidationException {
        validateFilm(film);
        putIdFilm(film);
        try {
            films.put(film.getId(), film);
            log.info("Добавлен фильм: '{}'", film);
            return film;
        } catch (Exception e){
            log.error("Не выполнены условия добавления фильма. Фильм не добавлен");
            throw new ValidationException("Не выполнены условия добавления фильма. /n Убедитесь в правильности ввода" +
                    "данных.");
        }
    }

    @Override
    public Film updateFIlm(@Valid Film film) throws ValidationException{
        validateFilm(film);
        if(film.getId() > films.size()){
            log.error("Не выполнены условия обновления фильма. Фильм не обновлен");
            throw new ValidationException("Не выполнены условия обновления фильма. /n Убедитесь в правильности ввода" +
                    "данных.");
        }
        films.put(film.getId(), film);
        log.info("Обновлен film '{}' на '{}'", film.getId(), film);
        return film;
    }

    @Override
    public ArrayList<Film> getFilms() {
        log.info("Выведен список films");
        return new ArrayList<>(films.values());
    }

    public Film getFilm(Integer id){
        ArrayList<Film> film = getFilms();
        if(film.size() < id){
            throw new NotFoundException("Такого id не существует");
        }
        return film.get(Math.toIntExact(id - 1));
    }
    public HashMap getMap(){
        return films;
    }

    private Integer putIdFilm(Film film){
        film.setId(id);
        id = id+1;
        return id;
    }

    private void validateFilm(@Valid Film film) throws ValidationException {
        if (film.getReleaseDate().isBefore(LocalDate.of(1895,12,28))){
            log.error("Не выполнены условия добавления пользователя. Пользователь не добавлен");
            throw new ValidationException("Не выполнены условия добавления пользователя. /n Убедитесь в " +
                    "правильности ввода данных.");
        }
        String description = film.getDescription();
        char[] c_arr = description.toCharArray();
        if(c_arr.length > 200){
            log.error("Не выполнены условия добавления пользователя. Пользователь не добавлен");
            throw new ValidationException("Не выполнены условия добавления пользователя. /n Убедитесь в " +
                    "правильности ввода данных.");
        }
        if(film.getName() == ""){
            log.error("Не выполнены условия добавления пользователя. Пользователь не добавлен");
            throw new ValidationException("Не выполнены условия добавления пользователя. /n Убедитесь в " +
                    "правильности ввода данных.");
        }
        if(film.getDuration() <= 0){
            log.error("Не выполнены условия добавления пользователя. Пользователь не добавлен");
            throw new ValidationException("Не выполнены условия добавления пользователя. /n Убедитесь в " +
                    "правильности ввода данных.");
        }
    }
}
