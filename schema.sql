CREATE TABLE USERS (
	USERNAME 	VARCHAR(10) NOT NULL PRIMARY KEY,
	PASSWORD 	VARCHAR(8) NOT NULL,
	IS_ADMIN		BOOLEAN	NOT NULL,	
	NICKNAME 	VARCHAR(20) UNIQUE,
	EMAIL		VARCHAR(50) NOT NULL,
	TELEPHONE	VARCHAR(10) NOT NULL,
	ADDRESS		VARCHAR(300) NOT NULL,
	DESCRIPTION 	VARCHAR(50),
	PHOTO_URL 	VARCHAR(150),
	STATUS 		BOOLEAN NOT NULL
);

CREATE TABLE BOOKS (
	ISBN 			VARCHAR(30) PRIMARY KEY,
	TITLE			VARCHAR(150) NOT NULL,
	DESCRIPTION		VARCHAR(500) NOT NULL,
	AUTHOR			VARCHAR(50) NOT NULL,
	PUBLISH_DATE 	TIMESTAMP NOT NULL,
	PRICE			DOUBLE NOT NULL DEFAULT 0.0
);

CREATE TABLE PURCHASES (
	ID			INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
	USERNAME		VARCHAR(10) NOT NULL REFERENCES USERS(USERNAME) ON DELETE CASCADE,
	BOOK_ISBN 	VARCHAR(30) NOT NULL REFERENCES BOOKS(ISBN) ON DELETE CASCADE
);

CREATE TABLE LIKES (
	ID			INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
	USERNAME		VARCHAR(10) NOT NULL REFERENCES USERS(USERNAME) ON DELETE CASCADE,
	BOOK_ISBN 	VARCHAR(30) NOT NULL REFERENCES BOOKS(ISBN) ON DELETE CASCADE
);

CREATE TABLE REVIEWS (
	ID			INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY,
	USERNAME		VARCHAR(10) NOT NULL REFERENCES USERS(USERNAME) ON DELETE CASCADE,
	BOOK_ISBN 	VARCHAR(30) NOT NULL REFERENCES BOOKS(ISBN) ON DELETE CASCADE,
	TEXT 		VARCHAR(500) NOT NULL,
	WRITE_DATE	TIMESTAMP NOT NULL,
	APPROVED 	BOOLEAN NOT NULL
);

/* Statements */
SELECT * FROM USERS;
SELECT * FROM USERS WHERE USERNAME=?
SELECT * FROM USERS WHERE NICKNAME=?
SELECT * FROM USERS WHERE USERNAME=? OR NICKNAME=?
SELECT * FROM USERS WHERE USERNAME=? AND PASSWORD=?
INSERT INTO USERS (USERNAME, PASSWORD, NICKNAME, DESCRIPTION, PHOTO_URL, STATUS, LAST_SEEN) VALUES (?,?,?,?,?,?,?)
UPDATE USERS SET USERNAME=?, PASSWORD=?, NICKNAME=?, DESCRIPTION=?, PHOTOURL=?, STATUS=?, LASTSEEN=? WHERE USERNAME=?
UPDATE USERS SET STATUS=?, LAST_SEEN=? WHERE NICKNAME=?
UPDATE USERS SET STATUS=?, LAST_SEEN=? WHERE STATUS=?

/**
* Messages Table predefined statement.
*/
SELECT * FROM MESSAGES
SELECT * FROM MESSAGES WHERE SENDER=?
SELECT * FROM MESSAGES WHERE RECEIVER=?
SELECT * FROM MESSAGES WHERE PARENT_ID=0 AND RECEIVER=? ORDER BY LAST_UPDATE DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY
SELECT * FROM MESSAGES WHERE PARENT_ID=0 AND ((RECEIVER=? AND SENDER=?) OR (RECEIVER=? AND SENDER=?)) ORDER BY LAST_UPDATE DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY
SELECT * FROM MESSAGES WHERE PARENT_ID=? ORDER BY LAST_UPDATE DESC OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY
SELECT * FROM MESSAGES WHERE PARENT_ID=? ORDER BY SENT_TIME DESC FETCH NEXT 1 ROWS ONLY
SELECT * FROM MESSAGES WHERE PARENT_ID=? ORDER BY LAST_UPDATE DESC
SELECT * FROM MESSAGES WHERE SENDER=? AND RECEIVER=?
INSERT INTO MESSAGES (PARENT_ID, SENDER, RECEIVER, TEXT, LAST_UPDATE, SENT_TIME) VALUES (?,?,?,?,?,?)
UPDATE MESSAGES SET LAST_UPDATE=? WHERE ID=?

/**
* Channels Table predefined statement.
*/
SELECT * FROM CHANNELS
SELECT * FROM CHANNELS WHERE NAME=?
INSERT INTO CHANNELS (NAME, DESCRIPTION, CREATED_BY, CREATED_TIME) VALUES (?,?,?,?)
UPDATE CHANNELS SET DESCRIPTION=? WHERE NAME=?
UPDATE CHANNELS SET NAME=? WHERE NAME=? AND CREATED_BY=?
DELETE FROM CHANNELS WHERE NAME=? AND CREATED_BY=?

/**
* Subscriptions Table predefined statement.
*/
SELECT * FROM SUBSCRIPTIONS WHERE NICKNAME=?
SELECT * FROM SUBSCRIPTIONS WHERE CHANNEL=?
SELECT * FROM SUBSCRIPTIONS
DELETE FROM SUBSCRIPTIONS WHERE NICKNAME=? AND CHANNEL=?
INSERT INTO SUBSCRIPTIONS (NICKNAME, CHANNEL) VALUES (?,?)