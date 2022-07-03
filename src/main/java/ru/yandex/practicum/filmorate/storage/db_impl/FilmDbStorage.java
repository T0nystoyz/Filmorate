package ru.yandex.practicum.filmorate.storage.db_impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@Component("filmDbStorage")
public class FilmDbStorage implements FilmStorage {
    @Override
    public Film findById(Long id) {
        return null;
    }

    @Override
    public List<Film> findAll() {
        return null;
    }

    @Override
    public Film create(Film film) {
        return null;
    }

    @Override
    public Film update(Film film) {
        return null;
    }
}
