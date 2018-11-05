# -- Resource schema

# -- -!Ups

CREATE TABLE resource (
    id INT AUTO_INCREMENT PRIMARY KEY,

    event_id INT,
    FOREIGN KEY (event_id) REFERENCES event(id),

    name CHAR(30),
    description CHAR(150),
    stock INT,
    quantity INT DEFAULT 0,
    price INT NOT NULL
);

# --- !Downs

DROP TABLE resource;