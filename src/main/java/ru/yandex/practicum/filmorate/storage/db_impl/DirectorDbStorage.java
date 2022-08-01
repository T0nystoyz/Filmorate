package ru.yandex.practicum.filmorate.storage.db_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.DirectorStorage;

import java.sql.PreparedStatement;
import java.util.*;

@Component
public class DirectorDbStorage implements DirectorStorage {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Director findById(Long id) {
        String sql = "SELECT * FROM directors WHERE director_id = ?";
        List<Director> directors =  jdbcTemplate.query(sql, (rs, rowNum) ->
                        new Director(rs.getLong("director_id"), rs.getString("name"))
                , id);

        if (directors.isEmpty()) {
            return null;
        }
        return directors.get(0);
    }

    @Override
    public List<Director> findAll() {
        String sql = "SELECT * FROM directors";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Director(rs.getLong("director_id"), rs.getString("name")));
    }

    @Override
    public Director create(Director director) {
        String sqlQuery = "INSERT INTO directors(name) VALUES (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"director_id"});
            stmt.setString(1, director.getName());
            return stmt;
        }, keyHolder);
        Long directorId = keyHolder.getKey().longValue();

        return findById(directorId);
    }

    @Override
    public Director update(Director director) {
        String sql = "UPDATE directors SET name = ? WHERE director_id = ?";
        int result = jdbcTemplate.update(sql, director.getName(), director.getId());
        if (result == 0) return null;
        else return findById(director.getId());
    }

    @Override
    public void delete(Long directorId) {
        String sqlQuery = "DELETE from directors WHERE director_id = ?";
        jdbcTemplate.update(sqlQuery, directorId);
    }

    @Override
    public Set<Director> getDirectorsByFilm(Film film) {
        String sql = "SELECT dir.director_id, dir.name " +
                "FROM films_directors AS fd " +
                "LEFT OUTER JOIN directors AS dir ON fd.director_id = dir.director_id " +
                "WHERE fd.film_id = ?";
        return new HashSet<>(jdbcTemplate.query(sql, (rs, rowNum) ->
                new Director(rs.getLong("director_id"), rs.getString("name")), film.getId()));
    }
}
