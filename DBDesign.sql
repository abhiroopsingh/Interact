CREATE TABLE Users
(
    username VARCHAR (32) NOT NULL,
    password VARCHAR (32) NOT NULL,
    first_name VARCHAR (32) NOT NULL,
    last_name VARCHAR (32) NOT NULL,
    security_question INT NOT NULL, /* Refers to the number of the selected security question */
    security_answer VARCHAR (128) NOT NULL,
    email VARCHAR (128) NOT NULL,      
    PRIMARY KEY (username)
);

CREATE TABLE Sessions
(
    id VARCHAR(16) NOT NULL,
    master VARCHAR (32) NOT NULL,
    active BOOLEAN NOT NULL,
    FOREIGN KEY (master) REFERENCES Users(username) ON DELETE CASCADE,
    PRIMARY KEY (id)  
);

CREATE TABLE Questions
(
     id INT UNSIGNED NOT NULL AUTO_INCREMENT,
     question VARCHAR(3000) NOT NULL,
     questionType VARCHAR(3000) NOT NULL,
     answer VARCHAR(3000) NOT NULL,
     answerChoices VARCHAR(3000) NOT NULL,
     session_id VARCHAR(16),
     FOREIGN KEY (session_id) REFERENCES Sessions(id),
     PRIMARY KEY (id)
);

CREATE TABLE UserAnswers 
(
     id INT UNSIGNED NOT NULL AUTO_INCREMENT,
     username VARCHAR (32) NOT NULL,
     answers VARCHAR (3000) NOT NULL,
     session_id VARCHAR(16),
     FOREIGN KEY (username) REFERENCES Users(username) ON DELETE CASCADE,
     FOREIGN KEY (session_id) REFERENCES Sessions(id),
     PRIMARY KEY (id)
);