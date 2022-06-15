package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.StorageData;

import java.util.List;

//Чтобы не повторяться

public interface CommonStorage <E extends StorageData> {

    E findById(Long id);

    List<E> findAll();

    E create(E data);

    E update(E data);
}
