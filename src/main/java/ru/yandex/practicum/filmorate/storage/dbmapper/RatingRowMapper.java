package ru.yandex.practicum.filmorate.storage.dbmapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Rating;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Component
public class RatingRowMapper implements RowMapper<Rating> {

    // Маппер для Rating
    @Override
    public Rating mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Rating rating = new Rating();
        rating.setId(resultSet.getInt("id"));
        rating.setName(resultSet.getString("name"));
        return rating;
    }
}