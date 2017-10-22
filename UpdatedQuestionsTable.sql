CREATE TABLE Questions
(
     id INT UNSIGNED NOT NULL AUTO_INCREMENT,
     question VARCHAR(3000) NOT NULL,
     questionType VARCHAR(3000) NOT NULL,
     answer VARCHAR(3000) NOT NULL,
     multipleChoiceA VARCHAR(1000),
     multipleChoiceB VARCHAR(1000),
     multipleChoiceC VARCHAR(1000),
     multipleChoiceD VARCHAR(1000),
     multipleChoiceE VARCHAR(1000),
     session_id VARCHAR(16),
     FOREIGN KEY (session_id) REFERENCES Sessions(id),
     PRIMARY KEY (id)
);
