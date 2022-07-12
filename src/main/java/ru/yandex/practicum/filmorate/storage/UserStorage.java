package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

//ТЗ требует этот интерфейс

public interface UserStorage extends CommonStorage<User> {
    boolean containsEmail(String email);

    boolean containsLink(Long filterId1, Long filterId2, Boolean filterConfirmed);

    void updateLink(Long id1, Long id2, boolean confirmed,  Long filterId1, Long filterId2);

    void insertLink(Long id, Long friendId);

    void removeLink(Long filterId1, Long filterId2);
}