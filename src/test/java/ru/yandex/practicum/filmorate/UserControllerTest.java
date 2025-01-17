package ru.yandex.practicum.filmorate;
/*
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() {
        User user = new User();
        User createdUser = userService.create(user);

        assertEquals(user, createdUser);
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        User updatedUser = userService.update(user);

        assertEquals(user, updatedUser);
    }

    @Test
    public void testGetUserById() {
        Integer userId = 1;
        User user = new User();
        User foundUser = userService.getById(userId);

        assertEquals(user, foundUser);
    }

    @Test
    public void testFindAllUsers() {
        List<User> users = List.of(new User(), new User());
        List<User> allUsers = userService.findAll();

        assertEquals(users, allUsers);
    }

    @Test
    public void testAddFriend() {
        Integer userId = 1;
        Integer friendId = 2;
        User user = new User();
        User friend = new User();
        userService.addFriend(userId, friendId);

        assertTrue(user.getFriends().contains(friendId));
        assertTrue(friend.getFriends().contains(userId));
    }

    @Test
    public void testAddFriendWithSameId() {
        Integer userId = 1;
        assertThrows(ValidationException.class, () -> userService.addFriend(userId, userId));
    }

    @Test
    public void testAddFriendWithNullIds() {
        assertThrows(ValidationException.class, () -> userService.addFriend(null, null));
    }

    @Test
    public void testAddFriendWithNonExistentUser() {
        Integer userId = 1;
        Integer friendId = 2;
        assertThrows(NotFoundException.class, () -> userService.addFriend(userId, friendId));
    }

    @Test
    public void testAddFriendWithNonExistentFriend() {
        Integer userId = 1;
        Integer friendId = 2;
        User user = new User();
        assertThrows(NotFoundException.class, () -> userService.addFriend(userId, friendId));
    }

    @Test
    public void testDeleteFriend() {
        Integer userId = 1;
        Integer friendId = 2;
        User user = new User();
        User friend = new User();
        userService.deleteFriend(userId, friendId);

        assertFalse(user.getFriends().contains(friendId));
        assertFalse(friend.getFriends().contains(userId));
    }

    @Test
    public void testDeleteFriendWithNullIds() {
        assertThrows(ValidationException.class, () -> userService.deleteFriend(null, null));
    }

    @Test
    public void testDeleteFriendWithNonExistentUser() {
        Integer userId = 1;
        Integer friendId = 2;
        assertThrows(NotFoundException.class, () -> userService.deleteFriend(userId, friendId));
    }

    @Test
    public void testDeleteFriendWithNonExistentFriend() {
        Integer userId = 1;
        Integer friendId = 2;
        User user = new User();
        assertThrows(NotFoundException.class, () -> userService.deleteFriend(userId, friendId));
    }

    @Test
    public void testGetUserFriends() {
        Integer userId = 1;
        User user = new User();
        List<User> friends = List.of(new User(), new User());
        List<User> userFriends = userService.userFriends(userId);

        assertEquals(friends, userFriends);
    }

    @Test
    public void testGetCrossingFriends() {
        Integer userId = 1;
        Integer friendId = 2;
        User user = new User();
        User friend = new User();
        List<User> commonFriends = List.of(new User(), new User());
        List<User> crossingFriends = userService.crossingFriends(userId, friendId);

        assertEquals(commonFriends, crossingFriends);
    }
}


 */