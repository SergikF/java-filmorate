package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.LikeDbStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmDbStorage filmStorage;
    private final UserStorage userStorage;
    private final LikeDbStorage likeStorage;

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public Film getById(Integer id) {
        return filmStorage.getById(id);
    }

    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    // Ставим лайк фильму
    public void addLike(Integer idFilm, Integer idUser) {
        checkForLike(idFilm, idUser);
        likeStorage.addLike(idFilm, idUser);
    }

    // Убираем лайк фильму
    public void deleteLike(Integer idFilm, Integer idUser) {
        checkForLike(idFilm, idUser);
        likeStorage.deleteLike(idFilm, idUser);
    }

    // Получаем список лучших фильмов
    public List<Film> listBestFilms(int count) {
        return filmStorage.findAll().stream()
                .sorted((f1, f2) -> f2.getLikes().size() - f1.getLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

    // проверка условий для установки и удаления лайка
    private void checkForLike(Integer idFilm, Integer idUser) {
        if (idFilm == null) {
            throw new ValidationException("Не указан id фильма");
        }
        if (idUser == null) {
            throw new ValidationException("Не указан id пользователя");
        }
        if (filmStorage.getById(idFilm) == null) {
            throw new NotFoundException("Фильм с id = " + idFilm + " не найден");
        }
        if (userStorage.getById(idUser) == null) {
            throw new NotFoundException("Пользователь с id = " + idUser + " не найден");
        }
    }
}
