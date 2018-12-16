# -- Event locations schema

# --- !Ups

CREATE TABLE event_locations (

    id INT AUTO_INCREMENT PRIMARY KEY,

    event_id INT,
    location_id INT,
    FOREIGN KEY (event_id) REFERENCES event(id),
    FOREIGN KEY (location_id) REFERENCES location(id)
);

# --- !Downs

DROP TABLE event_locations;