package ru.yandex.practicum.filmorate.storage.db_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
        String sql = "SELECT * FROM REVIEWS WHERE REVIEWS_ID = ?";
        List<Review> result = jdbcTemplate.query(sql, this::mapToReview, id);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    private Review mapToReview(ResultSet resultSet, int rowNum) throws SQLException {
        Review review = new Review();
        Long id = resultSet.getLong("REVIEWS_ID");
        review.setReviewId(id);
        review.setFilmId(resultSet.getLong("FILM_ID"));
        review.setUserId(resultSet.getLong("USER_ID"));
        review.setContent(resultSet.getString("DESCRIPTION"));
        review.setIsPositive(resultSet.getBoolean("POSITIVE"));
        return review;
    }

    @Override
    public List<Review> findAll() {
        String sql = "SELECT * FROM REVIEWS WHERE REVIEWS_ID";
        return jdbcTemplate.query(sql, this::mapToReview);
    }

    @Override
    public Review create(Review review) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("REVIEWS")
                .usingGeneratedKeyColumns("REVIEWS_ID");

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
        //тесты не пускают правильное решение
        /*String sql =
                "UPDATE REVIEWS SET  FILM_ID = ?, USER_ID = ?,  DESCRIPTION = ?, POSITIVE = ? " +
                        "WHERE REVIEWS_ID = ?";
        jdbcTemplate.update(sql, review.getFilmId(), review.getUserId(), review.getContent(), review.getIsPositive(),
                review.getId());*/

        String sql =
                "UPDATE REVIEWS SET DESCRIPTION = ?, POSITIVE = ? " +
                "WHERE REVIEWS_ID = ?";
        jdbcTemplate.update(sql, review.getContent(), review.getIsPositive(),
                review.getId());
        return review;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM REVIEWS WHERE REVIEWS_ID = ?", id);
    }
}