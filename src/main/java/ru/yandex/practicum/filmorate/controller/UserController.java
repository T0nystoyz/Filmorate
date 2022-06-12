package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//import ru.yandex.practicum.filmorate.exception.InvalidUserException;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController extends AbstractController<User, UserStorage, UserService> {
    private final Set<String> emails = new HashSet<>();

    @Autowired
    public UserController(UserService service, UserStorage storage) {
        super(service, storage);
    }

    @GetMapping
    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    @PostMapping
    @Override
    public User create(@Valid @RequestBody User user) {
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
    public User update(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }

        super.update(user);
        emails.add(user.getEmail());
        log.info("Обновлён пользователь {}", user);

        return user;
    }

    @Override
    protected void validationBeforeCreate(User user) {
        if (emails.contains(user.getEmail())) {
            String message = ("Пользователь с электронной почтой " +
                    user.getEmail() + " уже зарегистрирован.");
            log.warn(message);
            throw new UserAlreadyExistException(message);
        }

    }

}
