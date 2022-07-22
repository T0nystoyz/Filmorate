package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.storage.DirectorStorage;

@Service
public class DirectorService extends AbstractService<Director, DirectorStorage> {

    @Autowired
    public DirectorService(DirectorStorage storage) {
        super(storage);
    }

    @Override
    public void validationBeforeCreate(Director director) {
    }

    public void deleteDirector(Long directorId) {
        super.storage.deleteDirector(directorId);
    }

}
