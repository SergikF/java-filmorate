package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        filmService.create(film);
        return film;
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        filmService.update(film);
        return film;
    }

    @GetMapping
    public List<Film> findAll() {
        return filmService.findAll();
    }

    @GetMapping("/{filmId}")
    public Film getFilmById(@PathVariable Integer filmId) {
        return filmService.getById(filmId);
    }

    @PutMapping("/{filmId}/like/{userId}")
    public Film addLike(@PathVariable Integer filmId, @PathVariable Integer userId) {
        filmService.addLike(filmId, userId);
        return filmService.getById(filmId);
    }

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
