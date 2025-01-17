package ru.yandex.practicum.filmorate;

import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest()
@Validated
public class UserControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserController userController;

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        User createdUser = userController.create(user);
        assertEquals(user, createdUser);
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setId(1);
        user.setName("Updated User");
        user.setEmail("updated@example.com");
        User updatedUser = userController.update(user);
        assertEquals(user, updatedUser);
    }

    @Test
    public void testFindAllUsers() {
        List<User> users = List.of(new User(), new User());
        List<User> allUsers = userController.findAll();
        assertEquals(users, allUsers);
    }

    @Test
    public void testGetUserById() {
        Integer userId = 1;
        User user = new User();
        user.setId(userId);
        user.setName("Test User");
        user.setEmail("test@example.com");
        User foundUser = userController.getUserById(userId);
        assertEquals(user, foundUser);
    }

    @Test
    public void testAddFriend() {
        Integer userId = 1;
        Integer friendId = 2;
        User user = new User();
        user.setId(userId);
        user.setName("Test User");
        user.setEmail("test@example.com");
        User addedFriend = userController.addFriend(userId, friendId);
        assertEquals(user, addedFriend);
    }

    @Test
    public void testDeleteFriend() {
        Integer userId = 1;
        Integer friendId = 2;
        User user = new User();
        user.setId(userId);
        user.setName("Test User");
        user.setEmail("test@example.com");
        User deletedFriend = userController.deleteFriend(userId, friendId);
        assertEquals(user, deletedFriend);
    }

    @Test
    public void testGetUserFriends() {
        Integer userId = 1;
        List<User> friends = List.of(new User(), new User());
        List<User> userFriends = userController.getUserFriends(userId);
        assertEquals(friends, userFriends);
    }

    @Test
    public void testGetCrossingFriends() {
        Integer userId = 1;
        Integer otherUserId = 2;
        List<User> crossingFriends = List.of(new User(), new User());
        List<User> foundCrossingFriends = userController.getCrossingFriends(userId, otherUserId);
        assertEquals(crossingFriends, foundCrossingFriends);
    }
}
