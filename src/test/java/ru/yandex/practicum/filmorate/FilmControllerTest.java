package ru.yandex.practicum.filmorate;
/*
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {

    private InMemoryFilmStorage filmController;

    private Film film;

    @BeforeEach
    void setUp() {
        filmController = new InMemoryFilmStorage();
        film = new Film();
        film.setName("Test Film");
        film.setDescription("Test description");
        film.setDuration(120);
        film.setReleaseDate(LocalDate.of(2000, 1, 1));
    }

    @Test
    void findAll() {
        Collection<Film> films = filmController.findAll();
        assertNotNull(films);
    }

    @Test
    void create() {
        Film createdFilm = filmController.create(film);
        assertNotNull(createdFilm);
        assertEquals(film.getName(), createdFilm.getName());
        assertEquals(film.getDescription(), createdFilm.getDescription());
        assertEquals(film.getDuration(), createdFilm.getDuration());
        assertEquals(film.getReleaseDate(), createdFilm.getReleaseDate());
    }

    @Test
    void createWithInvalidName() {
        film.setName("");
        assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test
    void createWithLongDescription() {
        film.setDescription("a".repeat(201));
        assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test
    void createWithInvalidDuration() {
        film.setDuration(0);
        assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test
    void createWithInvalidReleaseDate() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test
    void update() {
        Film createdFilm = filmController.create(film);
        Film newFilm = new Film();
        newFilm.setId(createdFilm.getId());
        newFilm.setName("New Test Film");
        newFilm.setDescription("New test description");
        newFilm.setDuration(180);
        newFilm.setReleaseDate(LocalDate.of(2001, 1, 1));
        Film updatedFilm = filmController.update(newFilm);
        assertNotNull(updatedFilm);
        assertEquals(newFilm.getName(), updatedFilm.getName());
        assertEquals(newFilm.getDescription(), updatedFilm.getDescription());
        assertEquals(newFilm.getDuration(), updatedFilm.getDuration());
        assertEquals(newFilm.getReleaseDate(), updatedFilm.getReleaseDate());
    }

    @Test
    void updateWithInvalidId() {
        Film newFilm = new Film();
        newFilm.setId(1);
        assertThrows(ValidationException.class, () -> filmController.update(newFilm));
    }

    @Test
    void updateWithInvalidName() {
        Film createdFilm = filmController.create(film);
        Film newFilm = new Film();
        newFilm.setId(createdFilm.getId());
        newFilm.setName("");
        newFilm.setDescription("New test description");
        newFilm.setDuration(180);
        newFilm.setReleaseDate(LocalDate.of(2001, 1, 1));
        assertThrows(ValidationException.class, () -> filmController.update(newFilm));
    }

    @Test
    void updateWithLongDescription() {
        Film createdFilm = filmController.create(film);
        Film newFilm = new Film();
        newFilm.setId(createdFilm.getId());
        newFilm.setName("New Test Film");
        newFilm.setDescription("a".repeat(201));
        newFilm.setDuration(180);
        newFilm.setReleaseDate(LocalDate.of(2001, 1, 1));
        assertThrows(ValidationException.class, () -> filmController.update(newFilm));
    }

    @Test
    void updateWithInvalidDuration() {
        Film createdFilm = filmController.create(film);
        Film newFilm = new Film();
        newFilm.setId(createdFilm.getId());
        newFilm.setName("New Test Film");
        newFilm.setDescription("New test description");
        newFilm.setDuration(0);
        newFilm.setReleaseDate(LocalDate.of(2001, 1, 1));
        assertThrows(ValidationException.class, () -> filmController.update(newFilm));
    }

    @Test
    void updateWithInvalidReleaseDate() {
        Film createdFilm = filmController.create(film);
        Film newFilm = new Film();
        newFilm.setId(createdFilm.getId());
        newFilm.setName("New Test Film");
        newFilm.setDescription("New test description");
        newFilm.setDuration(180);
        newFilm.setReleaseDate(LocalDate.of(1895, 12, 27));
        assertThrows(ValidationException.class, () -> filmController.update(newFilm));
    }
}


 */