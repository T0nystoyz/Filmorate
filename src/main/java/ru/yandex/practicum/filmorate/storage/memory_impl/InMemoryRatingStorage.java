package ru.yandex.practicum.filmorate.storage.memory_impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

@Component
public class InMemoryRatingStorage extends AbstractInMemoryStorage<Rating> implements RatingStorage {
}
