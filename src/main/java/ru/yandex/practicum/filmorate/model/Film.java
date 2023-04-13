package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private Integer id;
    @NotBlank(message = "Название фильма не должно быть пустым")
    private String name;
    @Size(min = 1, max = 200)
    private String description;
    private LocalDate releaseDate;
    @Positive
    private long duration;
    private Set<Integer> likes = new HashSet<>();

    public Film(Integer id, String name, String description, LocalDate date, long duration, Set<Integer> likes){
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = date;
        this.duration = duration;
        this.likes = getLikes();
    }

    public Set<Integer> getLikes() {
        return likes;
    }

    public void setLikes(Integer like) {
        likes.add(like);
    }

    public void deleteLike(Integer id){
        likes.remove(id);
    }
    private Integer putIdFilm(){
        id =+ 1;
        return id;
    }
}
