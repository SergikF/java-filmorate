package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendDbStorage;
import ru.yandex.practicum.filmorate.storage.UserDbStorage;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDbStorage userStorage;
    private final FriendDbStorage friendStorage;

    public User create(User user) {
        log.info("create user with id: {}", user.getId());
        return userStorage.create(user);
    }

    public User update(User user) {
        log.info("update user with id: {}", user.getId());
        return userStorage.update(user);
    }

    public User getById(Integer id) {
        log.info("get user by id: {}", id);
        return userStorage.getById(id);
    }

    public List<User> findAll() {
        log.info("get all users");
        return userStorage.findAll();
    }

    public void addFriend(Integer idUser, Integer idFriend) {
        if (idUser.equals(idFriend)) {
            throw new ValidationException("Нельзя добавить себя в друзья");
        }
        checkForFriend(idUser, idFriend);
        log.info("add friend with idUser: {}, idFriend: {}", idUser, idFriend);
        friendStorage.addFriend(idUser, idFriend);
    }

    public void deleteFriend(Integer idUser, Integer idFriend) {
        checkForFriend(idUser, idFriend);
        log.info("delete friend with idUser: {}, idFriend: {}", idUser, idFriend);
        friendStorage.deleteFriend(idUser, idFriend);
    }

    public List<User> userFriends(Integer idUser) {
        if (userStorage.getById(idUser) == null) {
            throw new NotFoundException("Пользователь с id = " + idUser + " не найден");
        }
        log.info("get user friends with idUser: {}", idUser);
        return userStorage.getFriends(idUser);
    }

    public List<User> crossingFriends(Integer idUser, Integer idFriend) {
        User user = userStorage.getById(idUser);
        User friend = userStorage.getById(idFriend);
        Set<Integer> commonFriends = new HashSet<>(user.getFriends());
        commonFriends.retainAll(friend.getFriends());
        log.info("get common friends with idUser: {}, idFriend: {}", idUser, idFriend);
        return userStorage.findAll().stream()
                .filter(u -> commonFriends.contains(u.getId()))
                .collect(Collectors.toList());
    }

    private void checkForFriend(Integer idUser, Integer idFriend) {
        if (idUser == null || idFriend == null) {
            throw new ValidationException("Не указаны id пользователя или его друга");
        }
        if (userStorage.getById(idUser) == null) {
            throw new NotFoundException("Пользователь с id = " + idUser + " не найден");
        }
        if (userStorage.getById(idFriend) == null) {
            throw new NotFoundException("Друг с id = " + idFriend + " не найден");
        }
    }
}
