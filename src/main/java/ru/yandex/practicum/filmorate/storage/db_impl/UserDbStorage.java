package ru.yandex.practicum.filmorate.storage.db_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Primary
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User findById(Long id) {
        String sql = "SELECT * FROM USERS WHERE USER_ID = ?";
        List<User> result = jdbcTemplate.query(sql, this::mapToUser, id);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    private User mapToUser(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("USER_ID"));
        user.setEmail(resultSet.getString("EMAIL"));
        user.setLogin(resultSet.getString("LOGIN"));
        user.setName(resultSet.getString("NAME"));
        user.setBirthday(resultSet.getDate("BIRTHDAY").toLocalDate());
        loadFriends(user);
        return user;
    }

    public void loadFriends(User user) {
        String sql =
                    "(SELECT USER_ID2 ID FROM FRIENDSHIP  WHERE USER_ID1 = ?) " +
                    "UNION " +
                    "(SELECT USER_ID1 ID FROM FRIENDSHIP  WHERE USER_ID2 = ? AND  CONFIRMED = true)";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql, user.getId(), user.getId());
        while (sqlRowSet.next()) {
            user.addFriend(sqlRowSet.getLong("id"));
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM USERS ORDER BY USER_ID";
        return jdbcTemplate.query(sql, this::mapToUser);
    }

    @Override
    public User create(User user) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("USERS")
                .usingGeneratedKeyColumns("USER_ID");

        Map<String, Object> values = new HashMap<>();
        values.put("EMAIL", user.getEmail());
        values.put("LOGIN", user.getLogin());
        values.put("NAME", user.getName());
        values.put("BIRTHDAY", user.getBirthday());

        user.setId(simpleJdbcInsert.executeAndReturnKey(values).longValue());
        return user;
    }

    @Override
    public User update(User user) {
        String sql =
                    "UPDATE USERS SET LOGIN = ?, EMAIL = ?, NAME = ?, BIRTHDAY = ?" +
                    "WHERE USER_ID = ?";
        jdbcTemplate.update(sql, user.getLogin(), user.getEmail(), user.getName(), user.getBirthday(), user.getId());
        return user;
    }

    @Override
    public boolean containsEmail(String email) {
        String sql = "SELECT * FROM USERS WHERE EMAIL = ?";
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet(sql, email);
        return filmRows.next();
    }

    @Override
    public boolean containsFriendship(Long filterId1, Long filterId2, Boolean filterConfirmed) {
        String sql = "SELECT * FROM FRIENDSHIP WHERE USER_ID1 = ? AND USER_ID2 = ? AND  CONFIRMED = ?";
        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, filterId1, filterId2, filterConfirmed);
        return rows.next();
    }

    @Override
    public void updateFriendship(Long id1, Long id2, boolean confirmed,  Long filterId1, Long filterId2) {
        String sql =
                    "UPDATE FRIENDSHIP SET USER_ID1 = ?, USER_ID2 = ?, CONFIRMED = ? " +
                    "WHERE USER_ID1 = ? AND USER_ID2 = ?";
        jdbcTemplate.update(sql, id1, id2, confirmed, filterId1, filterId2);
    }

    @Override
    public void insertFriendship(Long id, Long friendId) {
        String sql = "INSERT INTO FRIENDSHIP (USER_ID1, USER_ID2, CONFIRMED) VALUES(?, ?, ?)";
        jdbcTemplate.update(sql, id, friendId, false);
    }

    @Override
    public void removeFriendship(Long filterId1, Long filterId2) {
        String sql = "DELETE FROM FRIENDSHIP WHERE USER_ID1 = ? AND USER_ID2 = ?";
        jdbcTemplate.update(sql, filterId1, filterId2);
    }
}