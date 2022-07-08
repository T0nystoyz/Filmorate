package ru.yandex.practicum.filmorate.storage.db_impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;

import java.util.Set;

@Component
public class FilmGenreDbStorage implements FilmGenreStorage {
    @Override
    public Set<Genre> getGenresByFilm(Film film) {
        return null;
    }

    @Override
    public void createGenresByFilm(Film film) {

    }

    @Override
    public void updateGenresByFilm(Film film) {

    }
}
