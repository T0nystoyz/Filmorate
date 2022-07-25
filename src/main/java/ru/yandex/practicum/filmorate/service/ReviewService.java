package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;

import javax.validation.ValidationException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        reviews.forEach(storage::loadGrades);
        reviews.sort(Comparator.comparing(Review::getUseful).reversed());
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
        if (review.getIsPositive() == null) {
            throw new ValidationException("review isPositive не заполнено!");
        }
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
        review.addGrade(userId, positive);
        storage.saveGrades(review);
    }

    private void delGrade(Long id, Long userId) {
        Review review = this.findById(id);
        User user = userService.findById(userId);
        validateForGrade(review, user);
        review.delGrade(userId);
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

    public List<Review> findAllByFilm(Long filmId, Integer count) {
        List<Review> reviews;
        if (filmId == null) {
            reviews = storage.findAll();
        } else {
            reviews = storage.findAllByFilm(filmId);
        }
        reviews.forEach(storage::loadGrades);

        return reviews.stream()
                .sorted(Comparator.comparing(Review::getUseful).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
}