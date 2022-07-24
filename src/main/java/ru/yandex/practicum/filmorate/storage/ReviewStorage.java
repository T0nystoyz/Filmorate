package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Review;

public interface ReviewStorage extends CommonStorage<Review> {
    void loadGrades(Review review);

    void saveGrades(Review review);
}