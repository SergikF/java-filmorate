package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dbmapper.FilmRowMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Primary
@Repository
@RequiredArgsConstructor
public class FilmDbStorage implements FilmStorage {
    private final JdbcTemplate jdbc;
    private final FilmRowMapper filmRowMapper;

    // Добавление фильма
    @Override
    public Film create(Film film) {
        List<Integer> ratings = jdbc.queryForList("SELECT id FROM mpa", Integer.class);
        List<Integer> genres = jdbc.queryForList("SELECT id FROM genres", Integer.class);

        if (!ratings.contains(film.getMpa().getId())) {
            throw new NotFoundException("Рейтинг MPA с id " + film.getMpa().getId() + " не найден");
        }

        List<Integer> notFoundGenres = film.getGenres().stream()
                .filter(genre -> !genres.contains(genre.getId()))
                .map(Genre::getId)
                .collect(Collectors.toList());

        if (!notFoundGenres.isEmpty()) {
            throw new NotFoundException("Жанры с id " + notFoundGenres + " не найдены");
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO films (name, description, duration, release_date, mpa) VALUES (?, ?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setInt(3, film.getDuration());
            ps.setDate(4, film.getReleaseDate());
            ps.setObject(5, film.getMpa().getId());
            return ps;
        }, keyHolder);
        film.setId(keyHolder.getKey().intValue());
        for (Genre genre : film.getGenres()) {
            try {
                jdbc.update("INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)",
                        keyHolder.getKey().intValue(), genre.getId());
            } catch (DuplicateKeyException e) {
            }
        }
        return film;
    }

    // Обновление фильма
    @Override
    public Film update(Film film) {
        int count = jdbc.update("UPDATE films SET name = ?, description = ?, duration = ?, release_date = ?, mpa = ? WHERE id = ?",
                film.getName(), film.getDescription(), film.getDuration(), film.getReleaseDate(), film.getMpa().getId(), film.getId());
        if (count == 0) {
            throw new NotFoundException("Фильм с id " + film.getId() + " не найден");
        }
        jdbc.update("DELETE FROM film_genres WHERE film_id = ?", film.getId());
        for (Genre genre : film.getGenres()) {
            try {
                jdbc.update("INSERT INTO film_genres (film_id, genre_id) VALUES (?, ?)",
                        film.getId(), genre.getId());
            } catch (DuplicateKeyException e) {
            }
        }
        return film;
    }

    // Выборка всех фильмов
    @Override
    public List<Film> findAll() {
        try {
            return jdbc.query("SELECT * FROM films", filmRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Список фильмов пуст");
        }
    }

    // Получение фильма по id
    @Override
    public Film getById(Integer id) {
        try {
            return jdbc.queryForObject("SELECT * FROM films WHERE id = ?", filmRowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("Фильм с id " + id + " не найден");
        }
    }
}
