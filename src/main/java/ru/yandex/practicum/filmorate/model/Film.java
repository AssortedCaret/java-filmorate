package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.*;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    @Id
    private Integer id;
    @NotBlank(message = "Название фильма не должно быть пустым")
    private String name;
    @Size(min = 1, max = 200)
    private String description;
    private LocalDate releaseDate;
    @Positive
    private long duration;
    private List<Genre> genres;
    private Mpa mpa;
    private final Set<Integer> likes = new HashSet<>();

    public Film(Integer id, String name, LocalDate date,
                String description, long duration,
                List<Genre> genres, Mpa mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = date;
        this.duration = duration;
        this.genres = genres;
        this.mpa = mpa;
    }

    public void deleteLike(Integer id) {
        likes.remove(id);
    }
}
