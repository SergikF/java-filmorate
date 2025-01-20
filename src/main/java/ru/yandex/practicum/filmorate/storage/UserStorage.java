package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    // Добавляем пользователя
    User create(User user);

    // Обновляем данные пользователя
    User update(User user);

    // Ищем пользователя по его id
    User getById(Integer id);

    //Получаем список всех пользователей
    List<User> findAll();

}
