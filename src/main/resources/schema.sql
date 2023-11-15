DROP TABLE IF EXISTS Rating;
DROP TABLE IF EXISTS Image;
DROP TABLE IF EXISTS Coupon;
DROP TABLE IF EXISTS Offer;
DROP TABLE IF EXISTS Comment;
DROP TABLE IF EXISTS Post;
DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Category;

CREATE TABLE Users (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    userName VARCHAR(60) NOT NULL,
    password VARCHAR(60) NOT NULL, 
    firstName VARCHAR(60) NOT NULL,
    lastName VARCHAR(60) NOT NULL, 
    email VARCHAR(60) NOT NULL,
	avatar VARBINARY(1024000),
    role TINYINT NOT NULL
);

CREATE TABLE Category (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	name VARCHAR(16) NOT NULL
);

CREATE TABLE Post (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	title VARCHAR(16) NOT NULL, 
	description VARCHAR(256) NOT NULL, 
	url VARCHAR(2048), 
	price DECIMAL(8,2) NOT NULL, 
	creationDate TIMESTAMP NOT NULL,
	positiveRatings INTEGER NOT NULL DEFAULT 0,
	negativeRatings INTEGER NOT NULL DEFAULT 0,
	expired BOOLEAN NOT NULL DEFAULT FALSE,
	userId BIGINT NOT NULL, 
	categoryId BIGINT,
	
	CONSTRAINT PostFK_Users FOREIGN KEY (userId) REFERENCES Users(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT PostFK_Category FOREIGN KEY (categoryId) REFERENCES Category(id) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Offer (
	id BIGINT NOT NULL PRIMARY KEY,

	CONSTRAINT OfferFK_Post FOREIGN KEY (id) REFERENCES Post(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Coupon (
	id BIGINT NOT NULL PRIMARY KEY,
	code VARCHAR(64) NOT NULL,

	CONSTRAINT CouponFK_Post FOREIGN KEY (id) REFERENCES Post(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Image (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	data VARBINARY(1024000) NOT NULL,
	postId BIGINT NOT NULL,

	CONSTRAINT ImageFK_Post FOREIGN KEY (postId) REFERENCES Post(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Rating (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	positive BOOLEAN NOT NULL,
	userId BIGINT NOT NULL, 
	postId BIGINT NOT NULL,

	CONSTRAINT RatingFK_Users FOREIGN KEY (userId) REFERENCES Users(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT RatingFK_Post FOREIGN KEY (postId) REFERENCES Post(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Comment (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
	description VARCHAR(256) NOT NULL, 
	date TIMESTAMP NOT NULL,
	userId BIGINT NOT NULL, 
	postId BIGINT NOT NULL,
	parentId BIGINT,
	level INTEGER NOT NULL DEFAULT 1,
	answers INTEGER NOT NULL DEFAULT 0,
	
	CONSTRAINT CommentFK_Users FOREIGN KEY (userId) REFERENCES Users(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT CommentFK_Post FOREIGN KEY (postId) REFERENCES Post(id) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT CommentFK_Comment FOREIGN KEY (parentId) REFERENCES Comment(id) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO Category(name) VALUES ('Meals');
INSERT INTO Category(name) VALUES ('Motor');
INSERT INTO Category(name) VALUES ('Home');
INSERT INTO Category(name) VALUES ('Toys');
INSERT INTO Category(name) VALUES ('Tech');
INSERT INTO Category(name) VALUES ('Leisure');

INSERT INTO Users(userName, password, firstName, lastName, email, role) VALUES ('alejandrobujan', '$2a$10$8o34vbwlRURkBGETvQzr8OCuPrk52E.j2ilm4KGKPrwNR89eNV/YG', 'Alejandro', 'Bujan', 'alejandro.bujan.pampin@udc.es', 0);

INSERT INTO Post(title, description, url, price, creationDate, expired, userId, categoryId) VALUES ('Windows XP', 'Free windows XP license key', 'g2a.com', 0, '2004-05-12 00:00:00', true, 1, 5);
INSERT INTO Coupon(id, code) VALUES (1, 'XPFREE');

INSERT INTO Post(title, description, url, price, creationDate, expired, userId, categoryId) VALUES ('Windows 11', 'Free windows 11 license key', 'g2a.com' , 0, '2023-05-12 00:00:00', false, 1, 5);
INSERT INTO Offer(id) VALUES (2);

INSERT INTO Post(title, description, url, price, creationDate, expired, userId, categoryId) VALUES ('MG4 Brighton', 'Discover the future of mobility', 'www.mgmotor.eu/es-ES/configurator/mg4', 30480, '2023-10-09 00:00:00', false, 1, 2);
INSERT INTO Coupon(id, code) VALUES (3, 'EXTRAMG4');
