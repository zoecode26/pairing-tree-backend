 DROP TABLE IF EXISTS languages;

 CREATE TABLE languages (
     id bigserial PRIMARY KEY,
     name VARCHAR(100)
 );

 INSERT INTO languages (name) VALUES ('java');
 INSERT INTO languages (name) VALUES ('python');
 INSERT INTO languages (name) VALUES ('javascript');
 INSERT INTO languages (name) VALUES ('C#');
 INSERT INTO languages (name) VALUES ('C');
 INSERT INTO languages (name) VALUES ('PHP');
 INSERT INTO languages (name) VALUES ('ruby');
 INSERT INTO languages (name) VALUES ('swift');
 INSERT INTO languages (name) VALUES ('go');