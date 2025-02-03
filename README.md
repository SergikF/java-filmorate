_java-filmorate_

# Рейтинг фильмов
### Согласно задания спринта № 12.1 ( Промежуточное задание для проверки P2P )
## выполнено Филипповских Сергеем

_**Когорта-53**_

![](/er_diagram_filmrate.png)

Примеры запросов:
1. Получение пользователя с ID = 1: \
   SELECT *
   FROM users
   WHERE user_id = 1;

2. Получение фильма с ID = 10: \
   SELECT *
   FROM films
   WHERE film_id = 10.

3. Получение списка фильмов, понравившихся пользователю с ID = 5: \
   SELECT title
   FROM films
   WHERE film_id IN (SELECT film_id FROM likes WHERE user_id = 5);
