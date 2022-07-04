package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
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
    @Min(1)
    private Long ratingID;

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
}
