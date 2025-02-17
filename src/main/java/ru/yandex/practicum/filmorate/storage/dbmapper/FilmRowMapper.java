package ru.yandex.practicum.filmorate.storage.dbmapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.GenreDbStorage;
import ru.yandex.practicum.filmorate.storage.LikeDbStorage;
import ru.yandex.practicum.filmorate.storage.RatingDbStorage;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmRowMapper implements RowMapper<Film> {
    private final RatingDbStorage ratingDbStorage;
    private final LikeDbStorage likeDbStorage;
    private final GenreDbStorage genreDbStorage;

    // Маппер для Film
    @Override
    public Film mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(resultSet.getInt("id"));
        film.setName(resultSet.getString("name"));
        film.setDescription(resultSet.getString("description"));
        film.setDuration(resultSet.getInt("duration"));
        film.setReleaseDate(resultSet.getDate("release_date"));
        film.setMpa(ratingDbStorage.getById(resultSet.getInt("mpa")));
        film.setLikes(likeDbStorage.getLikes(resultSet.getInt("id")));
        film.setGenres(genreDbStorage.getByFilmId(resultSet.getInt("id")));
        return film;
    }
}