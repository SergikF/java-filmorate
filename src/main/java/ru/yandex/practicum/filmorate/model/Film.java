package ru.yandex.practicum.filmorate.model;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Slf4j
public class Film {
    private Integer id;

    private Set<Integer> likes = new HashSet<>();

    @NotNull(message = "Необходимо указать название фильма")
    @NotBlank(message = "Название фильма не может быть пустым")
    private String name;

    @Size(min = 1, max = 200, message = "Описание фильма должно быть от 1 до 200 символов")
    private String description;

    @MinimumDate(message = "Фильм не может выйти в прокат ранее 28.12.1895")
    private LocalDate releaseDate;

    @NotNull(message = "Необходимо указать продолжительность фильма")
    @Min(value = 1, message = "Продолжительность фильма должна быть не меньше 1 секунды")
    private int duration;

    public void addLike(Integer id) {
        likes.add(id);
    }

    public void removeLike(Integer id) {
        likes.remove(id);
    }
}
