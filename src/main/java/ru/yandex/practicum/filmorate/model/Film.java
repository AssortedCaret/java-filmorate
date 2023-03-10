package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Film {
    private int id;
    @NotBlank(message = "Название фильма не должно быть пустым")
    private String name;
    @Size(min = 1, max = 200)
    private String description;
    private LocalDate releaseDate;
    @Positive
    private long duration;

    public Film(int id, String name, String description, LocalDate date, long duration){
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = date;
        this.duration = duration;
    }

    private int putIdFilm(){
        id =+ 1;
        return id;
    }
}
