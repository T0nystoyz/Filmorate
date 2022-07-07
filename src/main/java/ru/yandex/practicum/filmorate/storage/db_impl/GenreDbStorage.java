package ru.yandex.practicum.filmorate.storage.db_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@Primary
public class GenreDbStorage implements GenreStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenreDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre findById(Long id) {
        String sql = "SELECT * FROM GENRES WHERE GENRE_ID = ?";
        List<Genre> result = jdbcTemplate.query(sql, this::mapToGenre, id);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    private Genre mapToGenre(ResultSet resultSet, int rowNum) throws SQLException {
        Genre genre = new Genre();
        genre.setId(resultSet.getLong("GENRE_ID"));
        genre.setName(resultSet.getString("NAME"));
        return genre;
    }

    @Override
    public List<Genre> findAll() {
        String sql = "SELECT * FROM GENRES";
        return jdbcTemplate.query(sql, this::mapToGenre);
    }

    @Override
    public Genre create(Genre genre) {
        String sql = "INSERT INTO GENRES (NAME) VALUES(?)";
        jdbcTemplate.update(sql, genre.getName());
        return genre;
    }

    @Override
    public Genre update(Genre genre) {
        String sql = "UPDATE GENRES SET NAME = ? WHERE GENRE_ID = ?";
        jdbcTemplate.update(sql, genre.getName(), genre.getId());
        return genre;
    }
}
