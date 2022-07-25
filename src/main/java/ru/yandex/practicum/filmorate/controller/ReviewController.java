package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController extends AbstractController<Review, ReviewService> {

    @Autowired
    public ReviewController(ReviewService service) {
        super(service);
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        service.addLike(id, userId);
    }

    @PutMapping("/{id}/dislike/{userId}")
    public void addDislike(@PathVariable Long id, @PathVariable Long userId) {
        service.addDislike(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void delLike(@PathVariable Long id, @PathVariable Long userId) {
        service.delLike(id, userId);
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    public void delDislike(@PathVariable Long id, @PathVariable Long userId) {
        service.delDislike(id, userId);
    }

    @GetMapping(params = {"filmId"})
    public List<Review> findAllByFilm(@RequestParam(required = false) Long filmId,
                                      @RequestParam(defaultValue = "10") Integer count) {
        return service.findAllByFilm(filmId, count);
    }
}
