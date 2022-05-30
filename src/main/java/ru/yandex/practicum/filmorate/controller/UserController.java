package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.InvalidUserException;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UserController {
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
            throw new InvalidUserException("Логин не должен содержать пробелы");
        }
        if (emails.contains(user.getEmail())) {
            throw new UserAlreadyExistException("Пользователь с электронной почтой " +
                    user.getEmail() + " уже зарегистрирован.");
        }

        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        currentId++;
        user.setId(currentId);
        emails.add(user.getEmail());
        users.put(user.getId(), user);

        return user;
    }

    @PutMapping
    public User put(@Valid @RequestBody User user) {
        if(user.getId() == null || user.getId() <= 0) {
            throw new InvalidUserException("Некорректный id");
        }
        emails.add(user.getEmail());
        users.put(user.getId(), user);

        return user;
    }

}
