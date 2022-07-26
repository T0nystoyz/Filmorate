package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

//ТЗ требует этот интерфейс

public interface UserStorage extends CommonStorage<User> {
    boolean containsEmail(String email);

    void loadFriends(User user);

    boolean containsFriendship(Long filterId1, Long filterId2, Boolean filterConfirmed);

    void updateFriendship(Long id1, Long id2, boolean confirmed,  Long filterId1, Long filterId2);

    void insertFriendship(Long id, Long friendId);

    void removeFriendship(Long filterId1, Long filterId2);

    List<Long> getUsersFilms(Long userId);

    Event createEvent(Event event);

    List<Event> getFeed(Long id);
}