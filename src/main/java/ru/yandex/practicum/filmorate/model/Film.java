package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Data
@Slf4j
@EqualsAndHashCode(of = { "name", "releaseDate" })
public class Film {
    private Long id;

    @NotNull(message = "Необходимо указать название фильма")
    @NotBlank(message = "Необходимо указать название фильма")
    private String name;

    @Size(min = 1, max = 200, message = "Описание фильма должно быть от 1 до 200 символов")
    private String description;

    @MinimumDate(message = "Некорректная дата выхода фильма")
    private LocalDate releaseDate;

    @Min(value = 1, message = "Продолжительность фильма должна быть не меньше 1 секунды")
    @NotNull(message = "Необходимо указать продолжительность фильма")
    private int duration;
}
