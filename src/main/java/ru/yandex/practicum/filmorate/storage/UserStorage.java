package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

//ТЗ требует этот интерфейс

public interface UserStorage extends CommonStorage<User> {
    boolean containsEmail(String email);
}