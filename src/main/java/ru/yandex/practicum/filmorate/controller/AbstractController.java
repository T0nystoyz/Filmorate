package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.model.StorageData;
import ru.yandex.practicum.filmorate.service.AbstractService;
import ru.yandex.practicum.filmorate.storage.CommonStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractController <E extends StorageData, T extends CommonStorage<E>,
        S extends AbstractService<E, T>> {
    private final static String MSG_ERR_ID = "Некорректный id ";

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
        validationBeforeCreate(data);
        return service.create(data);
    }

    public E update(E data) {
        validationBeforePut(data);
        return service.update(data);
    }

    abstract protected void validationBeforeCreate(E data);

    protected void validationBeforePut(E data) {
        if (data.getId() == null || data.getId() <= 0) {
            log.warn(MSG_ERR_ID + data.getId());
            throw new InvalidIdException(MSG_ERR_ID);
        }
    }
}
