package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Integer, Film> films = new HashMap<>();

    // добавляем фильм
    @Override
    public Film create(Film film) {
        // формируем дополнительные данные
        film.setId(getNextId());
        // сохраняем данные фильма
        films.put(film.getId(), film);
        return film;
    }

    // обновляем данные о фильме
    @Override
    public Film update(Film film) {
        // проверяем необходимые условия
        if (film.getId() == null) {
            throw new ValidationException("Для изменения данных, Id фильма должен быть указан");
        }
        if (films.containsKey(film.getId())) {
            // если фильм с таким id найден и все условия соблюдены, обновляем его содержимое
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

    /*
    // вспомогательный метод для проверки условий фильма
    private void checkFilm(Film newFilm) {
        if (newFilm.getName() == null || newFilm.getName().isBlank()) {
            throw new ValidationException("Название фильма не может быть пустым");
        }
        if (newFilm.getDescription().length() > 200) {
            throw new ValidationException("Максимальная длина описания фильма - 200 символов");
        }
        if (newFilm.getDuration() < 1) {
            throw new ValidationException("Продолжительность фильма должна быть больше 1 секунды");
        }
        if (newFilm.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            throw new ValidationException("Минимальная дата выхода фильма 28.12.1895");
        }
    }
     */
}
