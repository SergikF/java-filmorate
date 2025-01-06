package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final Map<Long, Film> films = new HashMap<>();

    // получаем список фильмов
    @GetMapping
    public Collection<Film> findAll() {
        return films.values();
    }

    // добавляем фильм
    @PostMapping
    public Film create(@RequestBody @Valid Film film) {
        // проверяем выполнение необходимых условий
        checkFilm(film);
        // формируем дополнительные данные
        film.setId(getNextId());
        // сохраняем данные фильма
        films.put(film.getId(), film);
        return film;
    }

    // вспомогательный метод для генерации идентификатора фильма
    private long getNextId() {
        long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

    // обновляем данные о фильме
    @PutMapping
    public Film update(@RequestBody @Valid Film newFilm) {
        // проверяем необходимые условия
        if (newFilm.getId() == null) {
            throw new ValidationException("Id фильма должен быть указан");
        }
        if (films.containsKey(newFilm.getId())) {
            checkFilm(newFilm);
            // если фильм с таким id найден и все условия соблюдены, обновляем его содержимое
            films.put(newFilm.getId(), newFilm);
            return newFilm;
        }
        throw new ValidationException("Фильм с id = " + newFilm.getId() + " не найден");
    }

    // вспомогательный метод для проверки условий фильма
    private void checkFilm(@RequestBody @Valid Film newFilm) {
        if (newFilm.getName() == null || newFilm.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (newFilm.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания фильма - 200 символов");
        }
        if (newFilm.getDuration() < 1 ) {
            throw new ValidationException("Продолжительность фильма должна быть больше 1 секунды");
        }
        if (newFilm.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))  ) {
            throw new ValidationException("Минимальная дата выхода фильма 28.12.1895");
        }
    }
}
