package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidFilmException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController extends AbstractController<Film> {
    private final static String MSG_ERR_DATE = "Дата релиза не раньше 28 декабря 1895 года ";
    private final LocalDate MIN_DATE = LocalDate.of(1895, 12, 28);

    @GetMapping
    @Override
    public List<Film> findAll() {
        return super.findAll();
    }

    @PostMapping
    @Override
    public Film create(@Valid @RequestBody Film film) {
        film = super.create(film);
        log.info("Добавлен фильма {}", film);

        return film;
    }

    @PutMapping
    @Override
    public Film put(@Valid @RequestBody Film film) {
        film = super.put(film);
        log.info("Обновлён фильм {}", film);

        return film;
    }

    private void validateReleaseDate(LocalDate date) {
        if (date.isBefore(MIN_DATE)) {
            log.warn(MSG_ERR_DATE + date);
            throw new InvalidFilmException(MSG_ERR_DATE);
        }
    }

    @Override
    protected void validationBeforeCreate(Film film) {
        validateReleaseDate(film.getReleaseDate());
    }

    @Override
    protected void validationBeforePut(Film film) {
        super.validationBeforePut(film);
        validateReleaseDate(film.getReleaseDate());
    }
}
