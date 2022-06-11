package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.StorageData;

import java.util.List;

public interface CommonStorage <T extends StorageData> {

    public List<T> findAll();

    public T create(T data);

    public T update(T data);

}
