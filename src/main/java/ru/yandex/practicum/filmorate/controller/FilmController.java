package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController extends AbstractController<Film, FilmStorage, FilmService> {

    @Autowired
    public FilmController(FilmService service, FilmStorage storage) {
        super(service, storage);
    }

    @GetMapping("/{id}")
    @Override
    public Film findById(@PathVariable  Long id) {
        return super.findById(id);
    }

    @GetMapping
    @Override
    public List<Film> findAll() {
        return super.findAll();
    }

    @PostMapping
    @Override
    public Film create(@Valid @RequestBody Film film) {
        return super.create(film);
    }

    @PutMapping
    @Override
    public Film update(@Valid @RequestBody Film film) {
        return super.update(film);
    }
}
