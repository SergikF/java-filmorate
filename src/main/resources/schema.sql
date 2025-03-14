DROP TABLE IF EXISTS film_genres;
DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS films;
DROP TABLE IF EXISTS mpa;



CREATE TABLE IF NOT EXISTS genres (
    id INT NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS mpa (
    id INT NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS films (
    id INT NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    duration INT NOT NULL,
    release_date DATE NOT NULL,
    mpa INT REFERENCES mpa(id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    login VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    name VARCHAR(100),
    birthday DATE
);

CREATE TABLE IF NOT EXISTS film_genres (
    film_id INT NOT NULL REFERENCES films (id) ON DELETE CASCADE ON UPDATE CASCADE,
    genre_id INT NOT NULL REFERENCES genres (id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (film_id, genre_id)
);

CREATE TABLE IF NOT EXISTS likes (
    film_id INT NOT NULL REFERENCES films (id) ON DELETE CASCADE ON UPDATE CASCADE,
    user_id INT NOT NULL REFERENCES users (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    PRIMARY KEY (film_id, user_id)
);

CREATE TABLE IF NOT EXISTS friends (
    user_id INT NOT NULL REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    friend_id INT NOT NULL REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (user_id, friend_id)
);