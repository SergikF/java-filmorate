package ru.yandex.practicum.filmorate.storage.dbmapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendDbStorage;

import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserRowMapper implements RowMapper<User> {
    private final FriendDbStorage friendDbStorage;

    // Маппер для User
    @Override
    public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setLogin(resultSet.getString("login"));
        user.setEmail(resultSet.getString("email"));
        user.setBirthday(resultSet.getDate("birthday"));
        user.setFriends(friendDbStorage.getFriends(resultSet.getInt("id")));
        return user;
    }


}