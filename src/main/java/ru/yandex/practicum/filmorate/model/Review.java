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
    private Boolean isPositive;
    private Long userId;
    private Long filmId;
    //@Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    //private int useful;
    @Getter(AccessLevel.NONE) @Setter(AccessLevel.NONE)
    private final Map<Long, Boolean> grades = new HashMap<>();

    public Long getReviewId() {
        return super.getId();
    }

    public void setReviewId(Long reviewId) {
        super.setId(reviewId);
    }

    public Boolean getIsPositive() {
        return isPositive;
    }

    public void setIsPositive(Boolean positive) {
        isPositive = positive;
    }

    public int getUseful() {
        int useful = 0;
        for (var positive : grades.values()) {
            if (positive) {
                useful++;
            } else {
                useful--;
            }
        }
        return useful;
    }

    public void addGrade(Long userId, boolean positive) {
        grades.put(userId, positive);
    }

    public void delGrade(Long userId) {
        grades.remove(userId);
    }

    public Map<Long, Boolean> getGrades() {
        return new HashMap<>(grades);
    }

}
