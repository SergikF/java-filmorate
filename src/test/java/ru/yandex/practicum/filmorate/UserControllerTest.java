package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private UserController userController;

    private User user;

    @BeforeEach
    void setUp() {
        userController = new UserController();
        user = new User();
        user.setEmail("test@test.ru");
        user.setLogin("test");
        user.setBirthday(LocalDate.of(2000, 1, 1));
    }

    @Test
    void findAll() {
        Collection<User> users = userController.findAll();
        assertNotNull(users);
    }

    @Test
    void create() {
        User createdUser = userController.create(user);
        assertNotNull(createdUser);
        assertEquals(user.getEmail(), createdUser.getEmail());
        assertEquals(user.getLogin(), createdUser.getLogin());
        assertEquals(user.getBirthday(), createdUser.getBirthday());
    }

    @Test
    void createWithInvalidEmail() {
        user.setEmail("testtest.ru");
        assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test
    void createWithExistingEmail() {
        userController.create(user);
        User newUser = new User();
        newUser.setEmail("test@test.ru");
        newUser.setLogin("newUser");
        newUser.setBirthday(LocalDate.of(2000, 1, 1));
        assertThrows(ValidationException.class, () -> userController.create(newUser));
    }

    @Test
    void createWithInvalidLogin() {
        user.setLogin(" ");
        assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test
    void createWithFutureBirthday() {
        user.setBirthday(LocalDate.now().plusDays(1));
        assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test
    void update() {
        User createdUser = userController.create(user);
        User newUser = new User();
        newUser.setId(createdUser.getId());
        newUser.setEmail("newEmail@test.ru");
        newUser.setLogin("newUser");
        newUser.setBirthday(LocalDate.of(2000, 1, 1));
        User updatedUser = userController.update(newUser);
        assertNotNull(updatedUser);
        assertEquals(newUser.getEmail(), updatedUser.getEmail());
        assertEquals(newUser.getLogin(), updatedUser.getLogin());
        assertEquals(newUser.getBirthday(), updatedUser.getBirthday());
    }

    @Test
    void updateWithInvalidId() {
        User newUser = new User();
        newUser.setId(1L);
        assertThrows(ValidationException.class, () -> userController.update(newUser));
    }

    @Test
    void updateWithInvalidEmail() {
        User createdUser = userController.create(user);
        User newUser = new User();
        newUser.setId(createdUser.getId());
        newUser.setEmail("newEmailtest.ru");
        newUser.setLogin("newUser");
        newUser.setBirthday(LocalDate.of(2000, 1, 1));
        assertThrows(ValidationException.class, () -> userController.update(newUser));
    }

    @Test
    void updateWithInvalidLogin() {
        User createdUser = userController.create(user);
        User newUser = new User();
        newUser.setId(createdUser.getId());
        newUser.setEmail("newEmail@test.ru");
        newUser.setLogin(" ");
        newUser.setBirthday(LocalDate.of(2000, 1, 1));
        assertThrows(ValidationException.class, () -> userController.update(newUser));
    }

    @Test
    void updateWithFutureBirthday() {
        User createdUser = userController.create(user);
        User newUser = new User();
        newUser.setId(createdUser.getId());
        newUser.setEmail("newEmail@test.ru");
        newUser.setLogin("newUser");
        newUser.setBirthday(LocalDate.now().plusDays(1));
        assertThrows(ValidationException.class, () -> userController.update(newUser));
    }
}
