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
        User user = super.findById(id);
        User friend = super.findById(friendId);
        if (user == null || friend == null) {
            String message = ("Пользователь не найден");
            log.warn(message);
            throw  new NotFoundException(message);
        }
        user.addFriend(friendId);
        //friend.addFriend(id);
        super.update(user);
        //super.update(friend);
    }

    public void removeFriend(Long id, Long friendId) {
        User user = super.findById(id);
        User friend = super.findById(friendId);
        if (user == null || friend == null) {
            String message = ("Пользователь не найден");
            log.warn(message);
            throw  new NotFoundException(message);
        }
        user.removeFriend(friendId);
        //friend.removeFriend(id);
        super.update(user);
       // super.update(friend);
    }

    public List<User> getFriends(Long id) {
        User user = super.findById(id);
        if (user == null) {
            String message = ("Пользователь не найден");
            log.warn(message);
            throw new NotFoundException(message);
        }
        List<Long> friendsId = user.getFiends();
        List<User> friends = new ArrayList<>();
        for (var friendId : friendsId) {
            friends.add(super.findById(friendId));
        }

        return friends;
    }

    public List<User> getCommonFriends(Long id1, long id2) {
        User user1 = super.findById(id1);
        User user2 = super.findById(id2);
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
            friends.add(super.findById(friendId));
        }

        return friends;
    }




}
