package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dbmapper.UserRowMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Slf4j
@Primary
@Repository
@RequiredArgsConstructor
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbc;
    private final UserRowMapper userRowMapper;

    @Override
    public User create(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO users (login, email, name, birthday) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(3, user.getLogin());
            ps.setString(2, user.getEmail());
            ps.setString(1, user.getName());
            ps.setDate(4, user.getBirthday());
            return ps;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public User update(User user) {
        int count = jdbc.update("UPDATE users SET login = ?, email = ?, name = ?, birthday = ? WHERE id = ?",
                user.getLogin(), user.getEmail(), user.getName(), user.getBirthday(), user.getId());
        if (count == 0) {
            throw new NotFoundException("Пользователь с id " + user.getId() + " не найден");
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        try {
            return jdbc.query("SELECT * FROM users", userRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Список пользователей пуст");
        }
    }

    @Override
    public User getById(Integer id) {
        try {
            return jdbc.queryForObject("SELECT * FROM users WHERE id = ?", userRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Пользователь с id " + id + " не найден");
        }
    }

    public List<User> getFriends(Integer id) {
        return jdbc.query("SELECT * FROM users WHERE id IN (SELECT friend_id FROM friends WHERE user_id = ?)",
                userRowMapper, id);
    }
}
