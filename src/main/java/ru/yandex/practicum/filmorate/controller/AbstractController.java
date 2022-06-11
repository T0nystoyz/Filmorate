package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.model.StorageData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class AbstractController <T extends StorageData> {
    private final static String MSG_ERR_ID = "Некорректный id ";
    private final Map<Long, T> storage = new HashMap<>();
    private long currentId = 0;

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }
    public T create(T data) {
        validationBeforeCreate(data);
        data.setId(++currentId);
        storage.put(data.getId(), data);

        return  data;
    }

    public T update(T data) {
        validationBeforePut(data);
        storage.put(data.getId(), data);
        return data;
    }

    abstract protected void validationBeforeCreate(T data);

    protected void validationBeforePut(T data) {
        if (data.getId() == null || data.getId() <= 0) {
            log.warn(MSG_ERR_ID + data.getId());
            throw new InvalidIdException(MSG_ERR_ID);
        }
    }
}
