package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService extends AbstractService<User, UserStorage> {
    public UserService(UserStorage storage) {
        super(storage);
    }

    @Override
    public User create(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        user = super.create(user);
        log.info("Добавлен пользователь {}", user);

        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users = super.findAll();
        users.forEach(storage::loadFriends);
        return users;
    }

    @Override
    public User findById(Long id) {
        User user = super.findById(id);
        storage.loadFriends(user);
        return user;
    }

    //Шаблонный метод
    @Override
    public void validationBeforeCreate(User user) {
        if (super.storage.containsEmail(user.getEmail())) {
            String message = ("Пользователь с электронной почтой " +
                    user.getEmail() + " уже зарегистрирован.");
            log.warn(message);
            throw new UserAlreadyExistException(message);
        }
    }

    public void addFriend(Long id, Long friendId) {
        User user = this.findById(id);
        User friend = this.findById(friendId);
        if (user == null || friend == null) {
            String message = ("Пользователь не найден");
            log.warn(message);
            throw  new NotFoundException(message);
        }
        if (user.containsFriend(friendId)) {
            log.warn("Друг существует");
            return;
        }
        user.addFriend(friendId);

        if (storage.containsFriendship(friendId, id, false)) {
            //friendId уже добавил ранее в друзья
            storage.updateFriendship(friendId, id, true, friendId, id);
        } else if (!storage.containsFriendship(id, friendId, null)){
            //Односторонняя связь, не было дружбы
            storage.insertFriendship(id, friendId);
        }
    }

    public void removeFriend(Long id, Long friendId) {
        User user = this.findById(id);
        User friend = this.findById(friendId);
        if (user == null || friend == null) {
            String message = ("Пользователь не найден");
            log.warn(message);
            throw  new NotFoundException(message);
        }
        if (!user.containsFriend(friendId)) {
            log.warn("Друг не существует");
            return;
        }
        user.removeFriend(friendId);

        if (storage.containsFriendship(id, friendId, false)) {
            //Односторонняя связь. friendId не одобрял
            storage.removeFriendship(id, friendId);
        } else if (storage.containsFriendship(id, friendId, true)) {
            //Совместная связь
            storage.updateFriendship(friendId, id, false, id, friendId);
        } else if (storage.containsFriendship(friendId, id, true)) {
            //Совместная связь. friendId первый добавил
            storage.updateFriendship(friendId, id, false, friendId, id);
        }
    }

    public List<User> getFriends(Long id) {
        User user = this.findById(id);
        if (user == null) {
            String message = ("Пользователь не найден");
            log.warn(message);
            throw new NotFoundException(message);
        }
        List<Long> friendsId = user.getFiends();
        List<User> friends = new ArrayList<>();
        for (var friendId : friendsId) {
            friends.add(this.findById(friendId));
        }

        return friends;
    }

    public List<User> getCommonFriends(Long id1, long id2) {
        User user1 = this.findById(id1);
        User user2 = this.findById(id2);
        if (user1 == null || user2 == null) {
            String message = ("Пользователь не найден");
            log.warn(message);
            throw  new NotFoundException(message);
        }
        List<Long> friendsId1 = user1.getFiends();
        List<Long> friendsId2 = user2.getFiends();
        friendsId1.retainAll(friendsId2);

        List<User> friends = new ArrayList<>();
        for (var friendId : friendsId1) {
            friends.add(this.findById(friendId));
        }

        return friends;
    }

    public List<Long> getUsersFilms(Long userId) {
        return storage.getUsersFilms(userId);
    }
}