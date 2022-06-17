package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.model.AbstractEntity;
import ru.yandex.practicum.filmorate.service.AbstractService;
import ru.yandex.practicum.filmorate.storage.CommonStorage;

import java.util.List;

public abstract class AbstractController <E extends AbstractEntity, T extends
        CommonStorage<E>, S extends AbstractService<E, T>> {
    protected final S service;

    @Autowired
    public AbstractController(S service) {
        this.service = service;
    }

    public E findById(Long id) {
        return service.findById(id);
    }

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
