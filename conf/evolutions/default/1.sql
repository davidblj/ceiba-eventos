# Event schema

# --- !Ups

CREATE TABLE event {
    id INT AUTO_INCREMENT PRIMARY KEY
    name CHAR(30)
    description CHAR(150)
}

# --- !Downs

DROP TABLE event