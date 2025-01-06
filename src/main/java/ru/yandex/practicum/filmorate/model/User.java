package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Data
@Slf4j
@EqualsAndHashCode(of = { "email", "login" })
public class User {
    private Long id;

    @Email(message = "Некорректный email")
    @NotNull(message = "Необходимо указать email")
    private String email;

    @NotNull(message = "Необходимо указать логин")
    @NotBlank(message = "Необходимо указать логин")
    private String login;

    private String name;

    @Past(message = "Некорректная дата рождения")
    private LocalDate birthday;
}