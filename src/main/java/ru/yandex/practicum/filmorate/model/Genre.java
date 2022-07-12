package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class Genre extends AbstractEntity{
    @Size(max = 30)
    private String name;

    public Genre(Long id, String name) {
        super(id);
        this.name = name;
    }

    public Genre(Long id) {
        super(id);
        this.name = "";
    }

    public Genre() {
        this.name = "";
    }
}