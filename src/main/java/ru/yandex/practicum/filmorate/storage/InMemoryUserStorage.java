package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();

    // Добавляем пользователя
    @Override
    public User create(User user) {
        // проверяем выполнение необходимых условий
        if (users.values().stream()
                .anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            throw new ValidationException("Этот email уже кем-то используется");
        }
        // формируем дополнительные данные
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(getNextId());
        // сохраняем нового пользователя
        users.put(user.getId(), user);
        return user;
    }

    // Обновляем данные пользователя
    @Override
    public User update(User newUser) {
        // проверяем необходимые условия
        if (newUser.getId() == null) {
            throw new ValidationException("Для изменения данных, Id пользователя должен быть указан");
        }
        if (users.containsKey(newUser.getId())) {
            User oldUser = users.get(newUser.getId());
            if (!oldUser.getEmail().equals(newUser.getEmail())) {
                if (users.values().stream()
                        .anyMatch(u -> u.getEmail().equals(newUser.getEmail()))) {
                    throw new ValidationException("Этот email уже кем-то используется");
                }
            }
            if (newUser.getName() == null || newUser.getName().isBlank()) {
                newUser.setName(newUser.getLogin());
            }
            users.put(newUser.getId(), newUser);
            return newUser;
        }
        throw new NotFoundException("Пользователь с id = " + newUser.getId() + " не найден");
    }

    // Ищем пользователя по его id
    @Override
    public User getById(Integer id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        }
        return users.get(id);
    }

    //Получаем список всех пользователей
    @Override
    public List<User> findAll() {
        return users.values().stream().toList();
    }

    // вспомогательный метод для генерации идентификатора нового пользователя
    private Integer getNextId() {
        return users.keySet().stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0) + 1;
    }

    /*
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
     */
}