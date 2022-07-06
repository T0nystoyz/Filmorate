package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

//рейтинг ассоциации кинокомпаний MPA

@Getter
@Setter
@ToString
public class Rating extends AbstractEntity {
    @Size(max = 10)
    private String name;
}