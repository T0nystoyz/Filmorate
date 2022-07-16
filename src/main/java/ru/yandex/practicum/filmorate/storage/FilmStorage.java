package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Set;

//ТЗ требует этот интерфейс

public interface FilmStorage extends CommonStorage<Film> {
    public Set<Genre> getGenresByFilm(Film film);

    void createGenresByFilm(Film film);

    public void updateGenresByFilm(Film film);

    public void loadLikes(Film film);

    public void saveLikes(Film film);
}