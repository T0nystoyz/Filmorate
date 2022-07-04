package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

@Service
public class GenreService extends AbstractService<Genre, GenreStorage>{
    public GenreService(GenreStorage storage) {
        super(storage);
    }

    @Override
    public void validationBeforeCreate(Genre data) {

    }
}
