package ru.yandex.practicum.filmorate.storage.db_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Primary
public class ReviewDbStorage implements ReviewStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ReviewDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Review findById(Long id) {
        String sql = "SELECT * FROM REVIEWS WHERE REVIEW_ID = ?";
        List<Review> result = jdbcTemplate.query(sql, this::mapToReview, id);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    private Review mapToReview(ResultSet resultSet, int rowNum) throws SQLException {
        Review review = new Review();
        Long id = resultSet.getLong("REVIEW_ID");
        review.setReviewId(id);
        review.setFilmId(resultSet.getLong("FILM_ID"));
        review.setUserId(resultSet.getLong("USER_ID"));
        review.setContent(resultSet.getString("DESCRIPTION"));
        review.setIsPositive(resultSet.getBoolean("POSITIVE"));
        return review;
    }

    @Override
    public List<Review> findAll() {
        String sql = "SELECT * FROM REVIEWS";
        return jdbcTemplate.query(sql, this::mapToReview);
    }

    @Override
    public List<Review> findAllByFilm(Long filmId) {
        String sql = "SELECT * FROM REVIEWS WHERE FILM_ID = ?";
        return jdbcTemplate.query(sql, this::mapToReview, filmId);
    }

    @Override
    public Review create(Review review) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("REVIEWS")
                .usingGeneratedKeyColumns("REVIEW_ID");

        Map<String, Object> values = new HashMap<>();
        values.put("FILM_ID", review.getFilmId());
        values.put("USER_ID", review.getUserId());
        values.put("DESCRIPTION", review.getContent());
        values.put("POSITIVE", review.getIsPositive());

        Long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
        review.setReviewId(id);
        return review;
    }


    @Override
    public Review update(Review review) {
        String sql =
                "UPDATE REVIEWS SET DESCRIPTION = ?, POSITIVE = ? " +
                "WHERE REVIEW_ID = ?";
        jdbcTemplate.update(sql, review.getContent(), review.getIsPositive(),
                review.getId());
        return review;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM REVIEWS WHERE REVIEW_ID = ?", id);
    }

    @Override
    public void loadGrades(Review review) {
        String sql = "SELECT * FROM GRADES WHERE  REVIEW_ID = ?";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, review.getId());
        while (sqlRowSet.next()) {
            review.addGrade(sqlRowSet.getLong("USER_ID"), sqlRowSet.getBoolean("POSITIVE"));
        }
    }

    @Override
    public void saveGrades(Review review) {
        jdbcTemplate.update("DELETE FROM GRADES WHERE REVIEW_ID = ?", review.getId());

        String sql = "INSERT INTO GRADES (REVIEW_ID, USER_ID, POSITIVE) VALUES(?, ?, ?)";
        Map<Long, Boolean>  grades = review.getGrades();
        for (var grade : grades.entrySet()) {
            jdbcTemplate.update(sql, review.getId(), grade.getKey(), grade.getValue());
        }
    }
}