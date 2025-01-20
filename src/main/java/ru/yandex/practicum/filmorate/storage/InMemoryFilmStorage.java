package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository("inMemoryFilmStorage")
@Primary
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();

    // добавляем фильм
    @Override
    public Film create(Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);
        return film;
    }

    // обновляем данные о фильме
    @Override
    public Film update(Film film) {
        if (film.getId() == null) {
            throw new ValidationException("Для изменения данных, Id фильма должен быть указан");
        }
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            return film;
        }
        throw new NotFoundException("Фильм с id = " + film.getId() + " не найден");
    }

    // Ищем фильм по его id
    @Override
    public Film getById(Integer id) {
        if (!films.containsKey(id)) {
            throw new NotFoundException("Фильм с id " + id + " не найден");
        }
        return films.get(id);
    }

    // получаем список фильмов
    @Override
    public List<Film> findAll() {
        return films.values().stream().toList();
    }

    // вспомогательный метод для генерации идентификатора фильма
    private Integer getNextId() {
        return films.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0) + 1;
    }
}
