
# -- Attendant Assigned Resource schema

# --- !Ups

CREATE TABLE attendant (

    id INT AUTO_INCREMENT PRIMARY KEY,
    insertion_date DATETIME,

    event_id INT,
    FOREIGN KEY (event_id) REFERENCES event(id),

    employee_id INT,
    FOREIGN KEY (employee_id) REFERENCES employee(id),

    location_id INT,
    FOREIGN KEY (location_id) REFERENCES location(id)
);

# --- !Downs

DROP TABLE attendant;