 DROP TABLE IF EXISTS applicationuser;

 CREATE TABLE applicationuser (
     id bigserial PRIMARY KEY,
     username VARCHAR(100) NOT NULL,
     password varchar(100) NOT NULL
 );
