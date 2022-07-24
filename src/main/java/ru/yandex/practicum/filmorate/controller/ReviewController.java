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
        //service.addLike(id, userId);
        System.out.println("like");
    }

    @PutMapping("/{id}/dislike/{userId}")
    public void addDislike(@PathVariable Long id, @PathVariable Long userId) {
        //service.addLike(id, userId);
        System.out.println("Dislike");
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void delLike(@PathVariable Long id, @PathVariable Long userId) {
        //service.addLike(id, userId);
        System.out.println("del like");
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    public void delDislike(@PathVariable Long id, @PathVariable Long userId) {
        //service.addLike(id, userId);
        System.out.println("del Dislike");
    }

    @GetMapping(params = { "filmId", "count" })
    public List<Review> findAllByFilm(@RequestParam(required = false) Long filmId,
                                      @RequestParam(defaultValue = "10") Integer count) {
        return new ArrayList<>();
    }
}
