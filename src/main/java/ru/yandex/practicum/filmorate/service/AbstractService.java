package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exception.InvalidIdException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.AbstractEntity;
import ru.yandex.practicum.filmorate.storage.CommonStorage;

import java.util.List;

@Slf4j
public abstract class AbstractService <E extends AbstractEntity, T extends CommonStorage<E>>
        implements CommonService<E> {
    private final static String MSG_ERR_ID = "Некорректный id ";
    private final static String MSG_ERR_NOT_FOUND = "Не найдено по id ";

    protected final T storage;

    @Autowired
    public AbstractService(T storage) {
        this.storage = storage;
    }

    @Override
    public List<E> findAll() {
        return storage.findAll();
    }

    @Override
    public E create(E data) {
        validationBeforeCreate(data);
        return storage.create(data);
    }

    @Override
    public E update(E data) {
        validationBeforeUpdate(data);
        return storage.update(data);
    }

    @Override
    abstract public void validationBeforeCreate(E data);

    @Override
    public void validationBeforeUpdate(E data) {
        validateId(data.getId());
    }

    @Override
    public void validateId(Long id) {
        if (id == null) {
            log.warn(MSG_ERR_ID + id);
            throw new InvalidIdException(MSG_ERR_ID  + id);
        }
        if (id < 0) {
            log.warn(MSG_ERR_NOT_FOUND + id);
            throw new NotFoundException(MSG_ERR_NOT_FOUND + id);
        }
    }

    @Override
    public E findById(Long id) {
        validateId(id);
        E data = storage.findById(id);
        if (data == null) {
            log.warn(MSG_ERR_NOT_FOUND + id);
            throw new NotFoundException(MSG_ERR_NOT_FOUND + id);
        }

        return data;
    }
}
