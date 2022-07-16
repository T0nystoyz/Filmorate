package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.RatingService;

@RestController
@RequestMapping("/mpa")
public class RatingController extends AbstractController<Rating, RatingService> {

    @Autowired
    public RatingController(RatingService service) {
        super(service);
    }
}