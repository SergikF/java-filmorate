package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    // добавляем фильм
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        filmService.create(film);
        return film;
    }

    // обновляем данные о фильме
    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        filmService.update(film);
        return film;
    }

    // получаем список фильмов
    @GetMapping
    public List<Film> findAll() {
        return filmService.findAll();
    }

    // Получаем фильм по id
    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable Integer filmId) {
        return filmService.getById(filmId);
    }

    // Добавляем like пользователя userId к фильму filmId
    @PutMapping("/{filmId}/like/{userId}")
    public Film addLike(@PathVariable Integer filmId, @PathVariable Integer userId) {
        filmService.addLike(filmId, userId);
        return filmService.getById(filmId);
    }

    // Удаляем like пользователя userId к фильму filmId
    @DeleteMapping("/{filmId}/like/{userId}")
    public Film deleteLike(@PathVariable Integer filmId, @PathVariable Integer userId) {
        filmService.deleteLike(filmId, userId);
        return filmService.getById(filmId);
    }

    @GetMapping("/popular")
    public List<Film> getListBestFilms(@RequestParam(defaultValue = "10") Integer count) {
        return filmService.listBestFilms(count);
    }

}
