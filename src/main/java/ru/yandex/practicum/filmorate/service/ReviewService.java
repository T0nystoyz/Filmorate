package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.storage.ReviewStorage;

@Service
public class ReviewService extends AbstractService<Review, ReviewStorage>{

    @Autowired
    public ReviewService(ReviewStorage storage) {
        super(storage);
    }

    @Override
    public void validationBeforeCreate(Review review) {
        super.validateId(review.getFilmId());
        super.validateId(review.getUserId());
    }
}