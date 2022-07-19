package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

//ТЗ требует этот интерфейс

public interface FilmStorage extends CommonStorage<Film> {

    void createGenresByFilm(Film film);

    void updateGenresByFilm(Film film);

    void loadLikes(Film film);

    void saveLikes(Film film);
}