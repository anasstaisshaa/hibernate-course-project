CREATE TABLE users
(
    username VARCHAR(128) PRIMARY KEY ,
    firstname VARCHAR(128) ,
    lastname VARCHAR(128),
    birthday DATE,
    role VARCHAR(32)
);

DROP TABLE users;