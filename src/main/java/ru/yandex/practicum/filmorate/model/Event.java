package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.filmorate.model.enums.EventType;
import ru.yandex.practicum.filmorate.model.enums.Operation;

@Getter
@Setter
@ToString
public class Event extends AbstractEntity {
    private Long timestamp;
    private Long userId;
    private EventType eventType;
    private Operation operation;
    private Long entityId;

    public Long getEventId() {
        return super.getId();
    }

    public void setEventId(Long eventId) {
        super.setId(eventId);
    }
}
