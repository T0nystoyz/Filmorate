package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//import ru.yandex.practicum.filmorate.exception.InvalidUserException;
import ru.yandex.practicum.filmorate.exception.UserAlreadyExistException;
import ru.yandex.practicum.filmorate.model.Film;
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
    public UserController(UserService service) {
        super(service);
    }

    @GetMapping("/{id}")
    @Override
    public User findById(@PathVariable  Long id) {
        return super.findById(id);
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

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable("id") Long id1, @PathVariable("friendId") Long id2) {
        service.addFriend(id1, id2);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void removeFriend(@PathVariable("id") Long id1, @PathVariable("friendId") Long id2) {
        service.removeFriend(id1, id2);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable  Long id) {
        return service.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("id") Long id1, @PathVariable("otherId") long id2) {
        return service.getCommonFriends(id1, id2);
    }

}
