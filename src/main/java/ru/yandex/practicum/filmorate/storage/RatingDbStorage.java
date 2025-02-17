package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.dbmapper.RatingRowMapper;

import java.util.List;

@Slf4j
@Primary
@Repository
@RequiredArgsConstructor
public class RatingDbStorage {
    private final JdbcTemplate jdbc;
    private final RatingRowMapper ratingRowMapper;

    public List<Rating> findAll() {
        try {
            return jdbc.query("SELECT * FROM mpa", ratingRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Список жанров пуст");
        }
    }

    public Rating getById(Integer id) {
        try {
            return jdbc.queryForObject("SELECT * FROM mpa WHERE id = ?", ratingRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Рейтинг с id " + id + " не найден");
        }
    }



}