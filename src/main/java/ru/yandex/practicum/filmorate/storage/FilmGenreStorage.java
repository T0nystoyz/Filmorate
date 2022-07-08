package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Set;

public interface FilmGenreStorage {

    Set<Genre> getGenresByFilm(Film film);

    void createGenresByFilm(Film film);

    void updateGenresByFilm(Film film);
}
