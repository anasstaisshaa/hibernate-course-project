CREATE TABLE users
(
    id BIGSERIAL PRIMARY KEY ,
    username VARCHAR(128) ,
    firstname VARCHAR(128) ,
    lastname VARCHAR(128),
    birthday DATE,
    role VARCHAR(32),
    company_id INT REFERENCES company (id)
);

CREATE TABLE company
(
    id SERIAL PRIMARY KEY ,
    name VARCHAR(64) NOT NULL UNIQUE
);

DROP TABLE company;
DROP TABLE users;