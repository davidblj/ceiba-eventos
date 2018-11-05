# -- Input schema

# -- -!Ups

CREATE TABLE input (
    id INT AUTO_INCREMENT PRIMARY KEY,

    event_id INT,
    FOREIGN KEY (event_id) REFERENCES event(id),

    name CHAR(30),
    description CHAR(150),
    price INT NOT NULL
);

# --- !Downs

DROP TABLE input;