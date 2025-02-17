package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    // получаем список жанров
    @GetMapping
    public List<Genre> findAll() {
        return genreService.findAll();
    }

    // Получаем жанр по id
    @GetMapping("/{Id}")
    public Genre getById(@PathVariable Integer Id) {
        return genreService.getById(Id);
    }
}
