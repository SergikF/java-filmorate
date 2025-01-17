package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Добавляем пользователя
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public User create(@RequestBody @Valid User user) {
        userService.create(user);
        return user;
    }

    // Обновляем данные пользователя
    @PutMapping
    public User update(@RequestBody @Valid User user) {
        userService.update(user);
        return user;
    }

    // Получаем список всех пользователей
    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    // Получаем пользователя по id
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable Integer userId) {
        return userService.getById(userId);
    }

    // Добавляем друга friendId для пользователя userId
    @PutMapping("/{userId}/friends/{friendId}")
    public User addFriend(@PathVariable Integer userId, @PathVariable Integer friendId) {
        userService.addFriend(userId, friendId);
        return userService.getById(userId);
    }

    // Удаляем друга friendId у пользователя userId
    @DeleteMapping("/{userId}/friends/{friendId}")
    public User deleteFriend(@PathVariable Integer userId, @PathVariable Integer friendId) {
        userService.deleteFriend(userId, friendId);
        return userService.getById(userId);
    }

    // Получаем список друзей пользователя userId
    @GetMapping("/{userId}/friends")
    public List<User> getUserFriends(@PathVariable Integer userId) {
        return userService.userFriends(userId);
    }

    // Получаем общий список друзей пользователя userId и и пользователя otherUserId
    @GetMapping("/{userId}/friends/common/{otherUserId}")
    public List<User> getCrossingFriends(@PathVariable Integer userId, @PathVariable Integer otherUserId) {
        return userService.crossingFriends(userId, otherUserId);
    }

}
