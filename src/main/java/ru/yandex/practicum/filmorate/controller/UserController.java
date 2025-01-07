package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> users = new HashMap<>();

    //Получаем список всех пользователей
    @GetMapping
    public Collection<User> findAll() {
        return users.values();
    }

    // Добавляем пользователя
    @PostMapping
    public User create(@RequestBody @Valid User user) {
        // проверяем выполнение необходимых условий
        if (users.values().stream()
                .anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            throw new ValidationException("Этот email уже используется");
        }
        checkUser(user);
        // формируем дополнительные данные
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(getNextId());
        // сохраняем нового пользователя
        users.put(user.getId(), user);
        return user;
    }


    // вспомогательный метод для генерации идентификатора нового пользователя
    private long getNextId() {
        long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    // Обновляем данные пользователя
    @PutMapping
    public User update(@RequestBody @Valid User newUser) {
        // проверяем необходимые условия
        if (newUser.getId() == null) {
            throw new ValidationException("Id пользователя должен быть указан");
        }
        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());
            checkUser(newUser);
            if (!oldUser.getEmail().equals(newUser.getEmail())) {
                if (users.values().stream()
                        .anyMatch(u -> u.getEmail().equals(newUser.getEmail()))) {
                    throw new ValidationException("Этот email уже используется");
                }
            }
            if (newUser.getName() == null || newUser.getName().isBlank()) {
                newUser.setName(newUser.getLogin());
            }
            users.put(newUser.getId(), newUser);
            return newUser;
        }
        throw new ValidationException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    // вспомогательный метод для проверки данных условий пользователя
    private void checkUser(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank() || !user.getEmail().contains("@")) {
            throw new ValidationException("Email должен быть указан корректно");
        }
        if (user.getLogin() == null || user.getLogin().isBlank() || user.getLogin().contains(" ")) {
            throw new ValidationException("Логин должен быть задан и без пробелов");
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            throw new ValidationException("День рождения не должен быть позже текущей даты");
        }
    }
}
