package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Film extends AbstractEntity {
    @NotBlank
    private String name;
    @NotBlank
    @Size(max = 200)
    private String description;
    private LocalDate releaseDate;
    @Min(1)
    private int duration;
    private Rating mpa;
    private Set<Genre> genres;

    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final Set<Long> likes = new HashSet<>();

    public void addLike(Long userId) {
        likes.add(userId);
    }

    public void removeLike(Long userId) {
        likes.remove(userId);
    }

    public int getLikesCount() {
        return likes.size();
    }

    public Set<Long> getLikes() {
        return new HashSet<>(likes);
    }
}