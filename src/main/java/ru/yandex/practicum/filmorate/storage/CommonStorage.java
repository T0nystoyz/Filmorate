package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.StorageData;

import java.util.List;

public interface CommonStorage <E extends StorageData> {

    public List<E> findAll();

    public E create(E data);

    public E update(E data);

}
