package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.StorageData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractInMemoryStorage<T extends StorageData> implements CommonStorage<T> {
    private final Map<Long, T> storage = new HashMap<>();
    private long currentId = 0;

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }
    public T create(T data) {
        data.setId(++currentId);
        storage.put(data.getId(), data);
        return  data;
    }

    public T update(T data) {
        storage.put(data.getId(), data);
        return data;
    }
}
