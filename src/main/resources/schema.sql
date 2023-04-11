DROP TABLE IF EXISTS film_like;
DROP TABLE IF EXISTS friends;
DROP TABLE IF EXISTS film_genre;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS film;
DROP TABLE IF EXISTS mpa;

CREATE TABLE IF NOT EXISTS mpa (
    id INTEGER generated by default as identity PRIMARY KEY,
    name varchar
);

CREATE TABLE IF NOT EXISTS genres (
    id INTEGER generated by default as identity PRIMARY KEY,
    name varchar
);
CREATE TABLE IF NOT EXISTS users (
    id INTEGER generated by default as identity PRIMARY KEY,
    email VARCHAR NOT NULL UNIQUE,
    login VARCHAR NOT NULL UNIQUE,
    name VARCHAR,
    birthday DATE
);

CREATE TABLE IF NOT EXISTS film (
    film_id INTEGER generated by default as identity PRIMARY KEY,
    name VARCHAR NOT NULL UNIQUE,
    description VARCHAR,
    release_date DATE,
    duration INTEGER,
    id INTEGER REFERENCES mpa (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS film_like (
        film_id INTEGER REFERENCES film (film_id),
        user_id INTEGER,
        PRIMARY KEY (film_id, user_id)
);

CREATE TABLE IF NOT EXISTS film_genre (
    film_id INTEGER REFERENCES film (film_id) ON DELETE CASCADE,
    genre_id INTEGER REFERENCES genres (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS friends (
    user_id INTEGER REFERENCES users (id) ON DELETE CASCADE,
    friend_id INTEGER REFERENCES users (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, friend_id)
);