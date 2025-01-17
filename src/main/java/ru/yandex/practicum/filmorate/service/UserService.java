package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Component
public class UserService implements UserStorage {

    private final UserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    // нашёл только такой способ совместить хранилище inMemoryUserStorage и интерфейс UserStorage
    @Override
    public User create(User user) {

        return userStorage.create(user);
    }

    @Override
    public User update(User user) {
        return userStorage.update(user);
    }

    @Override
    public User getById(Integer id) {
        return userStorage.getById(id);
    }

    @Override
    public List<User> findAll() {
        return userStorage.findAll();
    }

    // Добавляем друга в список друзей
    public void addFriend(Integer idUser, Integer idFriend) {
        if (idUser == idFriend) {
            throw new ValidationException("Нельзя добавить себя в друзья");
        }
        if (idUser == null || idFriend == null) {
            throw new ValidationException("Не указаны id пользователя или его друга");
        }
        if (userStorage.getById(idUser) == null) {
            throw new NotFoundException("Пользователь с id = " + idUser + " не найден");
        }
        if (userStorage.getById(idFriend) == null) {
            throw new NotFoundException("Друг с id = " + idFriend + " не найден");
        }
        userStorage.getById(idUser).addFriend(idFriend);
        userStorage.getById(idFriend).addFriend(idUser);
    }

    // Удаляем друга из списка друзей
    public void deleteFriend(Integer idUser, Integer idFriend) {
        if (idUser == null || idFriend == null) {
            throw new ValidationException("Не указаны id пользователя или его друга");
        }
        if (userStorage.getById(idUser) == null) {
            throw new NotFoundException("Пользователь с id = " + idUser + " не найден");
        }
        if (userStorage.getById(idFriend) == null) {
            throw new NotFoundException("Друг с id = " + idFriend + " не найден");
        }
        userStorage.getById(idUser).removeFriend(idFriend);
        userStorage.getById(idFriend).removeFriend(idUser);
    }

    // Получаем список друзей
    public List<User> userFriends(Integer idUser) {
        User user = userStorage.getById(idUser);
        return userStorage.findAll().stream()
                .filter(u -> user.getFriends().contains(u.getId()))
                .collect(Collectors.toList());
    }

    // Получаем общий список друзей
    public List<User> crossingFriends(Integer idUser, Integer idFriend) {
        User user = userStorage.getById(idUser);
        User friend = userStorage.getById(idFriend);
        Set<Integer> commonFriends = new HashSet<>(user.getFriends());
        commonFriends.retainAll(friend.getFriends());
        return userStorage.findAll().stream()
                .filter(u -> commonFriends.contains(u.getId()))
                .collect(Collectors.toList());
    }
}
