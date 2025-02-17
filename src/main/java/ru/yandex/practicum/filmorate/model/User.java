package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class User {
    private Integer id;

    private List<Integer> friends = new ArrayList<>();

    @NotNull(message = "Необходимо указать логин")
    @NotBlank(message = "Логин не может быть пустым")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Логин может содержать только буквы и цифры без пробелов и спецсимволов")
    private String login;

    @Email(message = "Укажите корректный email")
    @NotNull(message = "Необходимо указать email")
    @NotBlank(message = "Email не может быть пустым")
    private String email;

    private String name;

    @PastOrPresent(message = "Дата рождения не должна находиться в будущем")
    private Date birthday;

    public void addFriend(Integer id) {
        friends.add(id);
    }

    public void removeFriend(Integer id) {
        friends.remove(id);
    }
}