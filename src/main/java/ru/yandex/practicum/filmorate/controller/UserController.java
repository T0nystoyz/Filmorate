package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.*;

//Переписал, чтобы меньше кода была наследниках
@RestController
@RequestMapping("/users")
public class UserController extends AbstractController<User, UserService> {

    @Autowired
    public UserController(UserService service) {
        super(service);
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
