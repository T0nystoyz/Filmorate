package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.AbstractEntity;

import java.util.List;

public interface CommonService <E extends AbstractEntity>{
    List<E> findAll();

    E create(E data);

    E update(E data);

    void validationBeforeCreate(E data);

    void validationBeforeUpdate(E data);

    void validateId(Long id);

    E findById(Long id);

    void delete(Long id);
}