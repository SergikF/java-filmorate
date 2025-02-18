package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreDbStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreDbStorage genreStorage;

    public Genre getById(Integer id) {
        log.info("get genre by id: {}", id);
        return genreStorage.getById(id);
    }

    public List<Genre> findAll() {
        log.info("get all genres");
        return genreStorage.findAll();
    }
}
