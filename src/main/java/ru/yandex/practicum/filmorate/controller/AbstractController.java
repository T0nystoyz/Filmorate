package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.model.StorageData;
import ru.yandex.practicum.filmorate.service.AbstractService;
import ru.yandex.practicum.filmorate.storage.CommonStorage;

import java.util.List;

public abstract class AbstractController <E extends StorageData, T extends
        CommonStorage<E>, S extends AbstractService<E, T>> {
    private final S service;
    private final T storage;

    @Autowired
    public AbstractController(S service, T storage) {
        this.service = service;
        this.storage = storage;
    }

    @Autowired
    public List<E> findAll() {
        return service.findAll();
    }

    public E create(E data) {
        return service.create(data);
    }

    public E update(E data) {
        return service.update(data);
    }

}
