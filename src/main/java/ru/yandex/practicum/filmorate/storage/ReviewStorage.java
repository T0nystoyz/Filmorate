package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

public interface ReviewStorage extends CommonStorage<Review> {
    void loadGrades(Review review);

    void saveGrades(Review review);

    public List<Review> findAllByFilm(Long filmId);
}