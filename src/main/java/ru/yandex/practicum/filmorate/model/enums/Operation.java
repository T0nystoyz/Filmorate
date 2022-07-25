package ru.yandex.practicum.filmorate.model.enums;

public enum Operation {
    REMOVE("REMOVE"), ADD("ADD"), UPDATE("UPDATE");
    private final String title;

    Operation(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
