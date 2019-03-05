# -- Attendant schema

# --- !Ups

CREATE TABLE attendant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullName CHAR(30)
);

# --- !Downs

DROP TABLE attendant;