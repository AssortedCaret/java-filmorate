package model;

import java.time.LocalDate;
import lombok.Data;
import lombok.NonNull;

@Data
public class Film {
    private final int id;
    @NonNull
    private final String name;
    private final String description;
    private final LocalDate releaseDate;
    private final double duration;

    public Film(int id, String name, String description, int year, int month, int day, double duration){
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = LocalDate.of(year, month, day);
        this.duration = duration;
    }
}
