package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.InvalidFilmException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class FilmService extends AbstractService<Film, FilmStorage> {
    private final static String MSG_ERR_DATE = "Дата релиза не раньше 28 декабря 1895 года ";
    private final static String MSG_ERR_MPA = "Не заполнен рейтинг MPA";
    private final static String MSG_ERR_GENRES = "Не заполнен список жанров";

    private final LocalDate MIN_DATE = LocalDate.of(1895, 12, 28);
    private final UserService userService;

    @Autowired
    public FilmService(FilmStorage storage, UserService userService) {
        super(storage);
        this.userService = userService;
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
    public void validationBeforeCreate(Film film) {
        validateReleaseDate(film.getReleaseDate());
        validateMpa(film.getMpa());
    }

    //Шаблонный метод
    @Override
    public void validationBeforeUpdate(Film film) {
        super.validationBeforeUpdate(film);
        validateReleaseDate(film.getReleaseDate());
        validateMpa(film.getMpa());
    }

    private void validateReleaseDate(LocalDate date) {
        if (date.isBefore(MIN_DATE)) {
            log.warn(MSG_ERR_DATE + date);
            throw new InvalidFilmException(MSG_ERR_DATE);
        }
    }

    private void validateMpa(Rating mpa) {
        if (mpa == null) {
            log.warn(MSG_ERR_MPA);
            throw new InvalidFilmException(MSG_ERR_MPA);
        }
    }


    private void validateGenres (List<Genre> genres) {
        if (genres == null) {
            log.warn(MSG_ERR_GENRES);
            throw new InvalidFilmException(MSG_ERR_GENRES);
        }
    }

    private void validateLike(Film film, User user) {
        if (film == null) {
            String message = ("Фильм не найден");
            log.warn(message);
            throw new NotFoundException(message);
        }
        if (user == null) {
            String message = ("Пользователь не найден");
            log.warn(message);
            throw new NotFoundException(message);
        }
    }

    public void addLike(Long id, Long userId) {
        Film film = super.findById(id);
        User user = userService.findById(userId);
        validateLike(film, user);
        film.addLike(userId);
        super.update(film);
    }

    public void removeLike(Long id, Long userId) {
        Film film = super.findById(id);
        User user = userService.findById(userId);
        validateLike(film, user);
        film.removeLike(userId);
        super.update(film);
    }

    public List<Film> findPopularMovies(int count) {
        List<Film> films = super.findAll();
        films.sort(Comparator.comparing(Film::getLikesCount).reversed());
        if(count > films.size()) {
            count = films.size();
        }

        return new ArrayList<>(films.subList(0, count));
    }
}
