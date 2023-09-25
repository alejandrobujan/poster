DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Image;
DROP TABLE IF EXISTS Post;
DROP TABLE IF EXISTS Category;

CREATE TABLE Users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(60) NOT NULL,
    password VARCHAR(60) NOT NULL, 
    firstName VARCHAR(60) NOT NULL,
    lastName VARCHAR(60) NOT NULL, 
    email VARCHAR(60) NOT NULL,
	avatar VARBINARY(102400),
    role TINYINT NOT NULL
);

CREATE TABLE Category (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	name VARCHAR(60) NOT NULL
);

CREATE TABLE Post (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	title VARCHAR(60) NOT NULL, 
	description VARCHAR(256) NOT NULL, 
	url VARCHAR(2048), 
	price DECIMAL(5,2) NOT NULL, 
	creationDate DATETIME NOT NULL,
	userId BIGINT NOT NULL, 
	categoryId BIGINT,
	
	CONSTRAINT PostFK_Users FOREIGN KEY (userId) REFERENCES Users(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT PostFK_Category FOREIGN KEY (categoryId) REFERENCES Category(id) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Image (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	data VARBINARY(102400) NOT NULL,
	postId BIGINT NOT NULL,

	CONSTRAINT ImageFK_Post FOREIGN KEY (postId) REFERENCES Post(id) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO Category(name) VALUES ('Comida');
INSERT INTO Category(name) VALUES ('Motor');
INSERT INTO Category(name) VALUES ('Hogar');
INSERT INTO Category(name) VALUES ('Juguetes');
INSERT INTO Category(name) VALUES ('Tecnologia');
INSERT INTO Category(name) VALUES ('Entretenimiento');