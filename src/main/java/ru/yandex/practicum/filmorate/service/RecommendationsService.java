package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationsService {
    @Autowired
    private FilmService filmService;
    @Autowired
    private UserService userService;

    public Set<Film> getRecommendedFilms(Long userId) {
        Map<Long, List<Long>> filmsOfUsers = new HashMap<>();

        //получение списка пользователей
        List<User> users = userService.findAll();

        //получение всех фильмов лайкнутых пользователем
        for (User user : users) {
            filmsOfUsers.put(user.getId(), userService.getUsersFilms(user.getId()));
        }

        long maxMatches = 0;
        Set<Long> similarity = new HashSet<>(); //хранит id пользователей с наибольшими совпадениями по лайкам
        for (Long id : filmsOfUsers.keySet()) {
            if (id == userId) continue;

            long numberOfMatches = filmsOfUsers.get(id).stream()
                    .filter(filmId -> filmsOfUsers.get(userId).contains(filmId)).count();

            if (numberOfMatches == maxMatches & numberOfMatches != 0) {
                similarity.add(id);
            }

            if (numberOfMatches > maxMatches) {
                maxMatches = numberOfMatches;
                similarity = new HashSet<>();
                similarity.add(id);
            }
        }

        if (maxMatches == 0) return new HashSet<>();
        else return similarity.stream().flatMap(idUser -> userService.getUsersFilms(idUser).stream())
                .filter(filmId -> !filmsOfUsers.get(userId).contains(filmId))
                .map(filmId -> filmService.findById(filmId))
                .collect(Collectors.toSet());
    }
}
