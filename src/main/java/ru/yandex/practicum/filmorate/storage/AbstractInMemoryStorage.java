package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.StorageData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractInMemoryStorage<E extends StorageData> implements CommonStorage<E> {
    private final Map<Long, E> storage = new HashMap<>();
    private long currentId = 0;

    public List<E> findAll() {
        return new ArrayList<>(storage.values());
    }
    public E create(E data) {
        data.setId(++currentId);
        storage.put(data.getId(), data);
        return  data;
    }

    public E update(E data) {
        storage.put(data.getId(), data);
        return data;
    }
}
