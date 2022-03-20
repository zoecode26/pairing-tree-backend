 DROP TABLE IF EXISTS availability;

 CREATE TABLE availability(
     id BIGSERIAL PRIMARY KEY,
     user_id BIGINT,
     FOREIGN KEY(user_id) REFERENCES applicationuser(id),
     start_time TIMESTAMP
 );