DROP TABLE IF EXISTS language_preferences;

CREATE TABLE language_preferences (
    id bigserial PRIMARY KEY,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES applicationuser(id),
    language_id BIGINT,
    FOREIGN KEY (language_id) REFERENCES languages(id)
);