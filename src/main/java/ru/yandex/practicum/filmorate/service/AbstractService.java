package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.AbstractEntity;
import ru.yandex.practicum.filmorate.storage.CommonStorage;

import java.util.List;

@Slf4j
public abstract class AbstractService <E extends AbstractEntity, T extends CommonStorage<E>> {
    private final static String MSG_ERR_ID = "Некорректный id ";
    private final static String MSG_ERR_NOT_FOUND = "Не найдено по id ";

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
        return storage.create(data);
    }

    public E update(E data) {
        validationBeforeUpdate(data);
        return storage.update(data);
    }

    abstract protected void validationBeforeCreate(E data);

    protected void validationBeforeUpdate(E data) {
        validateId(data.getId());
    }

    protected void validateId(Long id) {
        if (id == null) {
            log.warn(MSG_ERR_ID + id);
            throw new InvalidIdException(MSG_ERR_ID  + id);
        }
        if (id < 0) {
            log.warn(MSG_ERR_NOT_FOUND + id);
            throw new NotFoundException(MSG_ERR_NOT_FOUND + id);
        }
    }

    public E findById(Long id) {
        validateId(id);
        return storage.findById(id);
    }
}
