package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FilmService extends AbstractService<Film, FilmStorage> {
    private final static String MSG_ERR_DATE = "Дата релиза не раньше 28 декабря 1895 года ";
    private final LocalDate MIN_DATE = LocalDate.of(1895, 12, 28);

    public FilmService(FilmStorage storage) {
        super(storage);
    }

    @Override
    public Film create(Film film) {
        film = super.create(film);
        log.info("Добавлен фильма {}", film);
        return film;
    }

    @Override
    public Film update(Film film) {
        film = super.update(film);
        log.info("Обновлён фильм {}", film);
        return film;
    }

    //Шаблонный метод
    @Override
    protected void validationBeforeCreate(Film film) {
        validateReleaseDate(film.getReleaseDate());
    }

    //Шаблонный метод
    @Override
    protected void validationBeforeUpdate(Film film) {
        super.validationBeforeUpdate(film);
        validateReleaseDate(film.getReleaseDate());
    }

    private void validateReleaseDate(LocalDate date) {
        if (date.isBefore(MIN_DATE)) {
            log.warn(MSG_ERR_DATE + date);
            throw new InvalidFilmException(MSG_ERR_DATE);
        }
    }

    public void addLike(Long id, Long userId) {
        super.validateId(id);
        super.validateId(userId);
    }

    public void removeLike(Long id, Long userId) {
        super.validateId(id);
        super.validateId(userId);
    }

    public List<Film> findPopularMovies(int count) {
        return new ArrayList<>();
    }
}
