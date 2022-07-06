package ru.yandex.practicum.filmorate.storage.memory_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.RatingStorage;
import ru.yandex.practicum.filmorate.storage.memory_impl.AbstractInMemoryStorage;

import java.util.List;
import java.util.Set;

@Component
@Primary
public class InMemoryFilmStorage extends AbstractInMemoryStorage<Film> implements FilmStorage {

    private RatingStorage ratingStorage;
    private GenreStorage genreStorage;

    @Autowired
    public InMemoryFilmStorage(RatingStorage ratingStorage, GenreStorage genreStorage) {
        this.ratingStorage = ratingStorage;
        this.genreStorage = genreStorage;
    }

    @Override
    public Film findById(Long id) {
        Film film = super.findById(id);
        Rating rating = film.getMpa();
        rating = ratingStorage.findById(rating.getId());
        film.setMpa(rating);

        Set<Genre> genres = film.getGenres();
        if (genres != null) {
            for (var el : genres) {
                String name = genreStorage.findById(el.getId()).getName();
                el.setName(name);
            }
            film.setGenres(genres);
        }

        return film;
    }
}
