package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

@Service
public class FilmService extends AbstractService<Film, FilmStorage>{
    public FilmService(FilmStorage storage) {
        super(storage);
    }
}
