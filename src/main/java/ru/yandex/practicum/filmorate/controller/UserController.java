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
public class UserController extends AbstractController<User> {
    private final Set<String> emails = new HashSet<>();

    @GetMapping
    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    @PostMapping
    @Override
    public User create(@Valid @RequestBody User user) {
        if (emails.contains(user.getEmail())) {
            String message = ("Пользователь с электронной почтой " +
                    user.getEmail() + " уже зарегистрирован.");
            log.warn(message);
            throw new UserAlreadyExistException(message);
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        user = super.create(user);
        emails.add(user.getEmail());
        log.info("Добавлен пользователь {}", user);

        return user;
    }

    @PutMapping
    @Override
    public User put(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        super.put(user);
        emails.add(user.getEmail());
        log.info("Обновлён пользователь {}", user);

        return user;
    }
}
