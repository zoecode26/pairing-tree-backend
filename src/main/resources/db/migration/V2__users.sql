DROP TABLE if EXISTS users;

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,

    username VARCHAR(90),
    password VARCHAR(30)
)