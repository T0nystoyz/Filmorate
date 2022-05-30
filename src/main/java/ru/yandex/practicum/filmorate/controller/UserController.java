package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidUserException;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final static String MSG_ERR_LOGIN = "Логин не должен содержать пробелы ";
    private final static String MSG_ERR_ID = "Некорректный id ";
    private final Map<Long, User> users = new HashMap<>();
    private final Set<String> emails = new HashSet<>();
    private long currentId = 0;

    @GetMapping
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (user.getLogin().contains(" ")) {
            log.warn(MSG_ERR_LOGIN + user.getLogin());
            throw new InvalidUserException(MSG_ERR_LOGIN);
        }
        if (emails.contains(user.getEmail())) {
            String message = ("Пользователь с электронной почтой " +
                    user.getEmail() + " уже зарегистрирован.");
            log.warn(message);
            throw new UserAlreadyExistException(message);
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        currentId++;
        user.setId(currentId);
        log.info("Добавление пользователя {}", user);
        emails.add(user.getEmail());
        users.put(user.getId(), user);

        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {
        if(user.getId() == null || user.getId() <= 0) {
            log.warn(MSG_ERR_ID + user.getId());
            throw new InvalidUserException(MSG_ERR_ID);
        }

        if (user.getLogin().contains(" ")) {
            log.warn(MSG_ERR_LOGIN + user.getLogin());
            throw new InvalidUserException(MSG_ERR_LOGIN);
        }

        log.info("Обновление пользователя {}", user);
        emails.add(user.getEmail());
        users.put(user.getId(), user);

        return user;
    }

}
