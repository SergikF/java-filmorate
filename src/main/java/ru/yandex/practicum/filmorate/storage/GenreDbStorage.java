package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dbmapper.GenreRowMapper;

import java.util.List;

@Slf4j
@Primary
@Repository
@RequiredArgsConstructor
public class GenreDbStorage {
    private final JdbcTemplate jdbc;
    private final GenreRowMapper genreRowMapper;

    // Получение всех жанров
    public List<Genre> findAll() {
        try {
            return jdbc.query("SELECT * FROM genres", genreRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Список жанров пуст");
        }
    }

    // Получение жанра по id
    public Genre getById(Integer id) {
        try {
            return jdbc.queryForObject("SELECT * FROM genres WHERE id = ?", genreRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Жанр с id " + id + " не найден");
        }
    }

    // Получение жанров по id фильма
    public List<Genre> getByFilmId(Integer filmId) {
        try {
            return jdbc.query("SELECT * FROM genres WHERE id IN (SELECT genre_id FROM film_genres WHERE film_id = ?)",
                    genreRowMapper, filmId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Жанры для фильма с id " + filmId + " не найдены");
        }
    }
}
