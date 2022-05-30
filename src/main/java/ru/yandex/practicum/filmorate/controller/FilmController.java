package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidFilmException;
import ru.yandex.practicum.filmorate.exception.InvalidUserException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    private final LocalDate MIN_DATE = LocalDate.of(1895, 12, 28);
    private final Map<Long, Film> films = new HashMap<>();
    private long currentId = 0;

    @GetMapping
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        if (film.getReleaseDate().isBefore(MIN_DATE)) {
            throw new InvalidFilmException("Дата релиза не раньше 28 декабря 1895 года");
        }
        currentId++;
        film.setId(currentId);
        films.put(film.getId(), film);

        return film;
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) {
        if(film.getId() == null || film.getId() <= 0) {
            throw new InvalidUserException("Некорректный id");
        }

        if (film.getReleaseDate().isBefore(MIN_DATE)) {
            throw new InvalidFilmException("Дата релиза не раньше 28 декабря 1895 года");
        }
        films.put(film.getId(), film);

        return film;
    }
}
