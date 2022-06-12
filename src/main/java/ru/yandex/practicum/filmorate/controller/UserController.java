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
public class UserController extends AbstractController<User, UserStorage, UserService> {

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
        return super.create(user);
    }

    @PutMapping
    @Override
    public User update(@Valid @RequestBody User user) {
        return super.update(user);
    }
}
