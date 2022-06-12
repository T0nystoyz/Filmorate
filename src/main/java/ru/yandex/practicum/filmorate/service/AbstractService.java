package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.model.StorageData;
import ru.yandex.practicum.filmorate.storage.CommonStorage;

import java.util.List;

public class AbstractService <E extends StorageData, T extends CommonStorage> {
    private final T storage;

    @Autowired
    public AbstractService(T storage) {
        this.storage = storage;
    }

    public List<E> findAll() {
        return storage.findAll();
    }

    public E create(E data) {
        return (E) storage.create(data);
    }

    public E update(E data) {
        return (E) storage.update(data);
    }
}
