package ru.yandex.practicum.filmorate.comparator;

import ru.yandex.practicum.filmorate.model.Film;
import java.util.Comparator;

public class NewComparator implements Comparator<Film> {
    @Override
    public int compare(Film film1, Film film2) {
        if (film1.getLikes().size() < film2.getLikes().size()) {
            return 1;
        } else if (film1.getLikes().size() == film2.getLikes().size()) {
            return 0;
        }
        return -1;
    }
}
