package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Primary
@Repository
@RequiredArgsConstructor
public class FriendDbStorage {
    private final JdbcTemplate jdbc;

    public void addFriend(Integer userId, Integer friendId) {
        try {
            jdbc.update("INSERT INTO friends (user_id, friend_id) VALUES (?, ?)", userId, friendId);
        } catch (DuplicateKeyException e) {
            log.error("DuplicateKeyException");
        }
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        jdbc.update("DELETE FROM friends WHERE user_id = ? AND friend_id = ?", userId, friendId);
    }

    public List<Integer> getFriends(Integer userId) {
        return jdbc.queryForList("SELECT friend_id FROM friends WHERE user_id = ?",
                Integer.class, userId);
    }
}
