package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

@Service
@Slf4j
public class UserService extends AbstractService<User, UserStorage> {
    public UserService(UserStorage storage) {
        super(storage);
    }

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
    protected void validationBeforeCreate(User user) {
        if (super.storage.containsEmail(user.getEmail())) {
            String message = ("Пользователь с электронной почтой " +
                    user.getEmail() + " уже зарегистрирован.");
            log.warn(message);
            throw new UserAlreadyExistException(message);
        }
    }

}
