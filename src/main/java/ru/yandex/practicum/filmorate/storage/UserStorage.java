package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

//ТЗ требует этот интерфейс

public interface UserStorage extends CommonStorage<User> {
    boolean containsEmail(String email);

    default void addFriend(Long id, Long friendId) {
        /*Заглушка
         * Для хранилища в памяти не нужно отдельно добавлять друзей
         * Этот метод нужно реализовать для хранилища в БД
         * */
    }

    default void removeFriend(Long id, Long friendId) {
        /*Заглушка
         * Для хранилища в памяти не нужно отдельно удалять друзей
         * Этот метод нужно реализовать для хранилища в БД
         * */
    }
}