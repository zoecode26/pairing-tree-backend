DROP TABLE if EXISTS matches;

CREATE TABLE matches (
    id BIGSERIAL PRIMARY KEY,
    user1_id BIGINT,
    FOREIGN KEY (user1_id) REFERENCES users(id),
    user2_id BIGINT,
    FOREIGN KEY (user2_id) REFERENCES users(id),
    start_time TIMESTAMP
 );

