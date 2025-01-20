package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Slf4j
public class User {
    private Integer id;

    private Set<Integer> friends = new HashSet<>();

    @Email(message = "Укажите корректный email")
    @NotNull(message = "Необходимо указать email")
    @NotBlank(message = "Email не может быть пустым")
    private String email;

    @NotNull(message = "Необходимо указать логин")
    @NotBlank(message = "Логин не может быть пустым")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Логин может содержать только буквы и цифры без пробелов и спецсимволов")
    private String login;

    private String name;

    @PastOrPresent(message = "Дата рождения не должна находиться в будущем")
    private LocalDate birthday;

    public void addFriend(Integer id) {
        friends.add(id);
    }

    public void removeFriend(Integer id) {
        friends.remove(id);
    }
}