package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.AbstractEntity;

import java.util.List;

//Чтобы не повторяться

public interface CommonStorage <E extends AbstractEntity> {
    E findById(Long id);

    List<E> findAll();

    E create(E data);

    E update(E data);

    default void delete(Long id) {
        throw new UnsupportedOperationException("Операция удаление не поддерживается.");
    };
}