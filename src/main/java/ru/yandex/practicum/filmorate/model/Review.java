package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class Review extends AbstractEntity {
    @NotBlank
    @Size(max = 500)
    private String content;
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    @NonNull
    private Boolean isPositive;
    private Long userId;
    private Long filmId;
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private int useful;
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final Map<Long, Boolean> grades = new HashMap<>();

    public Long getReviewId() {
        return super.getId();
    }

    public void setReviewId(Long reviewId) {
        super.setId(reviewId);
    }

    public boolean getIsPositive() {
        return isPositive;
    }

    public void setIsPositive(boolean positive) {
        isPositive = positive;
    }

    public int getUseful() {
        //todo
        return 0;
    }

    public void setUseful(int useful) {
        //todo

    }
}
