package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

public class RatingService  extends AbstractService<Rating, RatingStorage>{
    public RatingService(RatingStorage storage) {
        super(storage);
    }

    @Override
    public void validationBeforeCreate(Rating data) {

    }
}
