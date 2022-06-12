package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.model.StorageData;
import ru.yandex.practicum.filmorate.storage.CommonStorage;

import java.util.List;

@Slf4j
public abstract class AbstractService <E extends StorageData, T extends CommonStorage> {
    private final static String MSG_ERR_ID = "Некорректный id ";

    protected final T storage;

    @Autowired
    public AbstractService(T storage) {
        this.storage = storage;
    }

    public List<E> findAll() {
        return storage.findAll();
    }

    public E create(E data) {
        validationBeforeCreate(data);
        return (E) storage.create(data);
    }

    public E update(E data) {
        validationBeforeUpdate(data);
        return (E) storage.update(data);
    }

    abstract protected void validationBeforeCreate(E data);

    protected void validationBeforeUpdate(E data) {
        if (data.getId() == null || data.getId() <= 0) {
            log.warn(MSG_ERR_ID + data.getId());
            throw new InvalidIdException(MSG_ERR_ID);
        }
    }
}
