package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.EventService;
import ru.yandex.practicum.filmorate.service.RecommendationsService;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;
import java.util.Set;

//Переписал, чтобы меньше кода была наследниках
@RestController
@RequestMapping("/users")
public class UserController extends AbstractController<User, UserService> {

    RecommendationsService recommendationsService;
    EventService eventService;

    @Autowired
    public UserController(UserService service, RecommendationsService recommendationsService,
                          EventService eventService) {
        super(service);
        this.recommendationsService = recommendationsService;
        this.eventService = eventService;
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

    //возвращает рекомендации по фильмам для просмотра, где id - это пользователь
    @GetMapping("/{id}/recommendations")
    public Set<Film> getRecommendedFilms(@PathVariable("id") Long userId) {
        return recommendationsService.getRecommendedFilms(userId);
    }

    @GetMapping("/{id}/feed")
    public List<Event> getFeed(@PathVariable  Long id) {
        return eventService.findEventsByUserId(id);
    }
}