package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class FilmController {
    private final Map<Long, Film> films = new HashMap<>();
    private long currentId = 0;

    @GetMapping("/films")
    public List<Film> findAll() {
        return new ArrayList<>(films.values());
    }

    @PostMapping(value = "/film")
    public Film create(@Valid @RequestBody Film film) {
        currentId++;
        film.setId(currentId);
        films.put(film.getId(), film);

        return film;
    }

    @PutMapping(value = "/film")
    public Film put(@RequestBody Film film) {
        /*if(film.getEmail() == null || film.getEmail().isBlank()) {
            throw new InvalidEmailException("Адрес электронной почты не может быть пустым.");
        }*/
        films.put(film.getId(), film);

        return film;
    }
}
