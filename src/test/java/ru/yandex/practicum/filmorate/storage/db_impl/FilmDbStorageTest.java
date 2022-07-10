package ru.yandex.practicum.filmorate.storage.db_impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class FilmDbStorageTest {
    private final FilmDbStorage filmStorage;

    @Test
    void findById() {
        Film expFilm = getExpFilm1();
        filmStorage.create(expFilm);
        Film actFilm = filmStorage.findById(expFilm.getId());
        assertEquals(expFilm.getId(), actFilm.getId());
        assertEquals(expFilm.getName(), actFilm.getName());
    }

    @Test
    void findAll() {
        Film expFilm1 = getExpFilm1();
        filmStorage.create(expFilm1);
        Film expFilm2 = getExpFilm2();
        filmStorage.create(expFilm2);
        List<Film> expFilms = List.of(expFilm1, expFilm2);

        List<Film> actFilms = filmStorage.findAll();
        assertEquals(expFilms, actFilms);
        assertEquals(2, actFilms.size());
    }

    @Test
    void create() {
        Film expFilm = getExpFilm1();
        filmStorage.create(expFilm);
        Film actFilm = filmStorage.findById(expFilm.getId());
        assertEquals(expFilm.getId(),actFilm.getId());
        assertEquals(expFilm.getName(),actFilm.getName());
        assertEquals(expFilm.getDescription(),actFilm.getDescription());
        assertEquals(expFilm.getReleaseDate(),actFilm.getReleaseDate());
        assertEquals(expFilm.getDuration(),actFilm.getDuration());
        assertEquals(expFilm.getMpa(),actFilm.getMpa());
        assertEquals(expFilm.getGenres(),actFilm.getGenres());
    }

    @Test
    void update() {
        Film expFilm = getExpFilm1();
        filmStorage.create(expFilm);
        expFilm.setName("Super Film");

        filmStorage.update(expFilm);
        Film actFilm = filmStorage.findById(expFilm.getId());

        assertEquals(expFilm.getId(), actFilm.getId());
        assertEquals(expFilm.getName(), actFilm.getName());
    }

    private Film getExpFilm1() {
        Film film = new Film();
        film.setId(1L);
        film.setName("Film1");
        film.setDescription("DESCRIPTION1");
        film.setReleaseDate(LocalDate.of(2020, 3, 3));
        film.setDuration(100);

        Rating rating = new Rating();
        rating.setId(1L);
        film.setMpa(rating);

        Genre genre1 = new Genre();
        genre1.setId(1L);
        Genre genre2 = new Genre();
        genre2.setId(2L);
        film.setGenres(Set.of(genre1, genre2));
        return film;
    }

    private Film getExpFilm2() {
        Film film = new Film();
        film.setId(2L);
        film.setName("Film2");
        film.setDescription("DESCRIPTION2");
        film.setReleaseDate(LocalDate.of(2010, 1, 3));
        film.setDuration(90);
        Rating rating = new Rating();
        rating.setId(2L);
        film.setMpa(rating);
        return film;
    }
}