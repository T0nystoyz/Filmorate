package ru.yandex.practicum.filmorate.storage.db_impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

//@Component
//@Primary
@Slf4j
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Film findById(Long id) {
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT * FROM film WHERE film_id = ?", id);
        if (!filmRows.next()) {
            log.info("Фильм с идентификатором {} не найден.", id);
            return null;
        }

        Film film = new Film();
        film.setId(id);
        film.setName(filmRows.getString("NAME"));
        film.setDescription(filmRows.getString("DESCRIPTION"));
        film.setReleaseDate(filmRows.getDate("RELEASE_DATE").toLocalDate());
        film.setDuration(filmRows.getInt("DURATION"));
        Rating rating = new Rating();
        rating.setId(filmRows.getLong("RATING_ID"));
        film.setMpa(rating);
        //Todo ID Жанров
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
