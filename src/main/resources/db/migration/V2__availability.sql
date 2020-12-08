DROP TABLE IF EXISTS availability;

CREATE TABLE availability(
    id BIGSERIAL PRIMARY KEY,
--    user_id BIGINT,
--    FOREIGN KEY(user_id) REFERENCES users(id),
    start_time TIMESTAMP
);