package ru.yandex.practicum.filmorate.storage.db_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Primary
public class RatingDbStorage implements RatingStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public  RatingDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Rating findById(Long id) {
        String sql = "SELECT * FROM RATINGS WHERE RATING_ID = ?";
        List<Rating> result = jdbcTemplate.query(sql, this::mapToRating, id);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    private Rating mapToRating(ResultSet resultSet, int rowNum) throws SQLException {
        Rating rating = new Rating();
        rating.setId(resultSet.getLong("RATING_ID"));
        rating.setName(resultSet.getString("NAME"));
        return rating;
    }

    @Override
    public List<Rating> findAll() {
        String sql = "SELECT * FROM RATINGS ORDER BY RATING_ID";
        return jdbcTemplate.query(sql, this::mapToRating);
    }

    @Override
    public Rating create(Rating rating) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("RATINGS")
                .usingGeneratedKeyColumns("RATING_ID");

        Map<String, Object> values = new HashMap<>();
        values.put("NAME", rating.getName());

        rating.setId(simpleJdbcInsert.executeAndReturnKey(values).longValue());
        return rating;
    }

    @Override
    public Rating update(Rating rating) {
        String sql = "UPDATE RATINGS SET NAME = ? WHERE RATING_ID = ?";
        jdbcTemplate.update(sql, rating.getName(), rating.getId());
        return rating;
    }
}