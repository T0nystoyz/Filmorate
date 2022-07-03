package ru.yandex.practicum.filmorate.storage.memory_impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.memory_impl.AbstractInMemoryStorage;

@Component
public class InMemoryFilmStorage extends AbstractInMemoryStorage<Film> implements FilmStorage {
}
