package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class User extends StorageData {
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp="\\S+")
    private String login;
    private String name;
    @Past
    private LocalDate birthday;
}
