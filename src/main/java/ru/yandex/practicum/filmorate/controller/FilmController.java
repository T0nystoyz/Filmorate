package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class FilmController {
    private final static String MSG_ERR_DATE = "Дата релиза не раньше 28 декабря 1895 года ";
    private final static String MSG_ERR_ID = "Некорректный id ";
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
            log.warn(MSG_ERR_DATE + film.getReleaseDate());
            throw new InvalidFilmException(MSG_ERR_DATE);
        }
        currentId++;
        film.setId(currentId);
        log.info("Добавление фильма {}", film);
        films.put(film.getId(), film);

        return film;
    }

    @PutMapping
    public Film put(@Valid @RequestBody Film film) {
        if (film.getId() == null || film.getId() <= 0) {
            log.warn(MSG_ERR_ID + film.getId());
            throw new InvalidUserException(MSG_ERR_ID);
        }

        if (film.getReleaseDate().isBefore(MIN_DATE)) {
            log.warn(MSG_ERR_DATE + film.getReleaseDate());
            throw new InvalidFilmException(MSG_ERR_DATE);
        }
        log.info("Обновление фильма {}", film);
        films.put(film.getId(), film);

        return film;
    }
}
