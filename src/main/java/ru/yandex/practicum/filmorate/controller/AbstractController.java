package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.AbstractEntity;
import ru.yandex.practicum.filmorate.service.CommonService;

import javax.validation.Valid;
import java.util.List;

public abstract class AbstractController <E extends AbstractEntity, S extends CommonService<E>> {
    protected final S service;

    @Autowired
    public AbstractController(S service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public E findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @GetMapping
    public List<E> findAll() {
        return service.findAll();
    }

    @PostMapping
    public E create(@Valid @RequestBody E data) {
        return service.create(data);
    }

    @PutMapping
    public E update(@Valid @RequestBody E data) {
        return service.update(data);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}