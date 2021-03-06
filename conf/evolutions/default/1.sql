# -- Event schema

# --- !Ups

CREATE TABLE event (
    id INT AUTO_INCREMENT PRIMARY KEY,
    insertion_date DATETIME,
    name CHAR(30),
    description CHAR(150),
    favorite_resource CHAR(30),
    finished BOOLEAN
);

# --- !Downs

DROP TABLE event;