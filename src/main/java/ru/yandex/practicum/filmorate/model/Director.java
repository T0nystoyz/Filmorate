package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class Director extends AbstractEntity {
    @Pattern(regexp = "\\S+\\s?\\S*") //"^\\S*$"
    private String name;

    public Director(Long id, String name) {
        super(id);
        this.name = name;
    }
}