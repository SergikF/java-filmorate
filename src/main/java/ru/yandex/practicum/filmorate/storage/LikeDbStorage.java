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
public class LikeDbStorage {
    private final JdbcTemplate jdbc;

    // Ставим лайк фильму
    public void addLike(Integer filmId, Integer userId) {
        try {
            jdbc.update("INSERT INTO likes (film_id, user_id) VALUES (?, ?)", filmId, userId);
        } catch (DuplicateKeyException e) {
            log.error("DuplicateKeyException");
        }
    }

    // Удаляем лайк фильма
    public void deleteLike(Integer filmId, Integer userId) {
        jdbc.update("DELETE FROM likes WHERE film_id = ? AND user_id = ?", filmId, userId);
    }

    // Получаем список id пользователей, которые лайкнули фильм
    public List<Integer> getLikes(Integer filmId) {
        return jdbc.queryForList("SELECT user_id FROM likes WHERE film_id = ?",
                Integer.class, filmId);
    }
}
