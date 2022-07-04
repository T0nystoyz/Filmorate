package ru.yandex.practicum.filmorate.storage.db_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@Component
@Primary
public class FilmDbStorage implements FilmStorage {
    private final static String GET_FILM_BY_ID = "SELECT * FROM film WHERE film_id = ?";
   private final JdbcTemplate jdbcTemplate;

   @Autowired
   public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Film findById(Long id) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(GET_FILM_BY_ID, id);
        Film film = new Film();
        film.setId(id);
        return film;
    }

    @Override
    public List<Film> findAll() {
        return null;
    }

    @Override
    public Film create(Film film) {
        return null;
    }

    @Override
    public Film update(Film film) {
        return null;
    }
}
