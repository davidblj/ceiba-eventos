# -- location schema

# --- !Ups

CREATE TABLE location (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name CHAR(50)
);

# --- !Downs

DROP TABLE event;