package ru.yandex.practicum.filmorate.storage.db_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.enums.EventType;
import ru.yandex.practicum.filmorate.model.enums.Operation;
import ru.yandex.practicum.filmorate.storage.EventStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EventDbStorage implements EventStorage {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public EventDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Event createEvent(Event event) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("EVENTS")
                .usingGeneratedKeyColumns("EVENT_ID");

        Map<String, Object> values = new HashMap<>();
        values.put("EVENT_TIMESTAMP", event.getTimestamp());
        values.put("USER_ID", event.getUserId());
        String eventType = event.getEventType().getTitle();
        values.put("EVENT_TYPE", eventType);
        String operation = event.getOperation().getTitle();
        values.put("OPERATION", operation);
        values.put("ENTITY_ID", event.getEntityId());

        event.setEventId(simpleJdbcInsert.executeAndReturnKey(values).longValue());
        return event;
    }

    @Override
    public List<Event> findEventsByUserID(Long id) {
        String sql = "SELECT * FROM EVENTS WHERE USER_ID = ?";
        return jdbcTemplate.query(sql, this::mapToEvent, id);
    }

    private Event mapToEvent(ResultSet resultSet, int rowNum) throws SQLException {
        Event event = new Event();
        event.setEventId(resultSet.getLong("EVENT_ID"));
        event.setTimestamp(resultSet.getLong("EVENT_TIMESTAMP"));
        EventType eventType = EventType.valueOf(resultSet.getString("EVENT_TYPE"));
        event.setEventType(eventType);
        Operation operation = Operation.valueOf(resultSet.getString("OPERATION"));
        event.setOperation(operation);
        event.setUserId(resultSet.getLong("USER_ID"));
        event.setEntityId(resultSet.getLong("ENTITY_ID"));

        return event;
    }
}
