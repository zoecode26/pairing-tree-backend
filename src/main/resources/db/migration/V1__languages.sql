 DROP TABLE IF EXISTS languages;

 CREATE TABLE languages (
     id bigserial PRIMARY KEY,
     lang VARCHAR(100)
 );

 INSERT INTO languages (lang) VALUES ('Java');
 INSERT INTO languages (lang) VALUES ('Python');
 INSERT INTO languages (lang) VALUES ('JavaScript');
 INSERT INTO languages (lang) VALUES ('C#');
 INSERT INTO languages (lang) VALUES ('C');
 INSERT INTO languages (lang) VALUES ('PHP');
 INSERT INTO languages (lang) VALUES ('Ruby');
 INSERT INTO languages (lang) VALUES ('Swift');
 INSERT INTO languages (lang) VALUES ('Go');