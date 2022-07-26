package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.enums.EventType;
import ru.yandex.practicum.filmorate.model.enums.Operation;
import ru.yandex.practicum.filmorate.storage.EventStorage;

import java.time.Instant;
import java.util.List;

@Service
public class EventService {
    private final EventStorage eventStorage;

    @Autowired
    public EventService(EventStorage eventStorage) {
        this.eventStorage = eventStorage;
    }

    public List<Event> findEventsByUserId(Long id) {
        return eventStorage.findEventsByUserID(id);
    }

    public Event createEvent(Event event) {
        return eventStorage.createEvent(event);
    }

    private Event createEvent(Long userID,  EventType eventType, Operation operation, Long entityId) {
        Event event = new Event();
        event.setTimestamp(Instant.now().toEpochMilli());
        event.setEventType(eventType);
        event.setOperation(operation);
        event.setUserId(userID);
        event.setEntityId(entityId);

        return eventStorage.createEvent(event);
    }

    public Event createAddLikeEvent(Long userID, Long filmId) {
        return createEvent(userID, EventType.LIKE, Operation.ADD, filmId);
    }

    public Event createRemoveLikeEvent(Long userID, Long filmId) {
        return createEvent(userID, EventType.LIKE, Operation.REMOVE, filmId);
    }

    public Event createReviewEvent(Long userID, Operation operation, Long reviewId) {
        return createEvent(userID, EventType.REVIEW, operation, reviewId);
    }

    public Event createAddFriendEvent(Long userID, Long friendId) {
        return createEvent(userID, EventType.FRIEND, Operation.ADD, friendId);
    }

    public Event createRemoveFriendEvent(Long userID, Long friendId) {
        return createEvent(userID, EventType.FRIEND, Operation.REMOVE, friendId);
    }

}
