package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {

    // добавляем фильм
    Film create(Film film);

    // обновляем данные о фильме
    Film update(Film film);

    // Ищем фильм по его id
    Film getById(Integer id);

    // получаем список фильмов
    List<Film> findAll();

}
