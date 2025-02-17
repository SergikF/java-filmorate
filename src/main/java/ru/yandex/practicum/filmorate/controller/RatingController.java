package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.RatingService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    // получаем список рейтингов
    @GetMapping
    public List<Rating> findAll() {
        return ratingService.findAll();
    }

    // Получаем рейтинг по id
    @GetMapping("/{id}")
    public Rating getById(@PathVariable Integer id) {
        return ratingService.getById(id);
    }
}
