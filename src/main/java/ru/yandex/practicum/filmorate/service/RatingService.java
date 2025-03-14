package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.RatingDbStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingDbStorage ratingStorage;

    public Rating getById(Integer id) {
        log.info("get rating by id: {}", id);
        return ratingStorage.getById(id);
    }

    public List<Rating> findAll() {
        log.info("get all ratings");
        return ratingStorage.findAll();
    }
}