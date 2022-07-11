package ru.yandex.practicum.filmorate.storage.memory_impl;

import ru.yandex.practicum.filmorate.model.AbstractEntity;
import ru.yandex.practicum.filmorate.storage.CommonStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractInMemoryStorage<E extends AbstractEntity> implements CommonStorage<E> {
    private final Map<Long, E> storage = new HashMap<>();
    private long currentId = 0;

    @Override
    public E findById(Long id) {
        return storage.get(id);
    }

    @Override
    public List<E> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public E create(E data) {
        data.setId(++currentId);
        storage.put(data.getId(), data);
        return  data;
    }

    @Override
    public E update(E data) {
        storage.put(data.getId(), data);
        return data;
    }
}