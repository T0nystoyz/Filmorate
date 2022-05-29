package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class Film {

    private Long id;
    @NotBlank
    @Size(max = 200)
    private String name;
    private String description;
    private LocalDate releaseDate;
    private int duration;
}
