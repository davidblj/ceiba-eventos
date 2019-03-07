# -- Attendant schema

# --- !Ups

CREATE TABLE attendant (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name CHAR(30)
);

# --- !Downs

DROP TABLE attendant;