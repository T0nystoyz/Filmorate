package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;

import java.util.List;

@Service
@Slf4j
public class ReviewService extends AbstractService<Review, ReviewStorage> {
    private final UserService userService;

    @Autowired
    public ReviewService(ReviewStorage storage, UserService userService) {
        super(storage);
        this.userService = userService;
    }

    @Override
    public List<Review> findAll() {
        List<Review> reviews = super.findAll();
        reviews.forEach(x->storage.loadGrades(x));
        return reviews;
    }

    @Override
    public Review findById(Long id) {
        Review review = super.findById(id);
        storage.loadGrades(review);
        return review;
    }

    @Override
    public void validationBeforeCreate(Review review) {
        super.validateId(review.getFilmId());
        super.validateId(review.getUserId());
    }

    public void addLike(Long id, Long userId) {
        addGrade(id, userId, true);
    }

    public void addDislike(Long id, Long userId) {
        addGrade(id, userId, false);
    }

    public void delLike(Long id, Long userId) {
        delGrade(id, userId);
    }

    public void delDislike(Long id, Long userId) {
        delGrade(id, userId);
    }

    private void addGrade(Long id, Long userId, boolean positive) {
        Review review = this.findById(id);
        User user = userService.findById(userId);
        validateForGrade(review, user);
        //grade.addLike(userId);
        storage.saveGrades(review);
    }

    private void delGrade(Long id, Long userId) {
        Review review = this.findById(id);
        User user = userService.findById(userId);
        validateForGrade(review, user);
        //film.removeLike(userId);
        storage.saveGrades(review);
    }

    private void validateForGrade(Review review, User user) {
        if (review == null) {
            String message = "Отзыв не найден";
            log.warn(message);
            throw new NotFoundException(message);
        }
        if (user == null) {
            String message = ("Пользователь не найден");
            log.warn(message);
            throw new NotFoundException(message);
        }
    }
}