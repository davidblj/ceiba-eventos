
# -- Attendant Assigned Resource schema

# --- !Ups

# --- TODO: set the primary key as a combination of event_id, and employee_id
CREATE TABLE attendant (
    id INT AUTO_INCREMENT PRIMARY KEY,

    event_id INT,
    FOREIGN KEY (event_id) REFERENCES event(id),

    employee_id INT,
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);

# --- !Downs

DROP TABLE attendant;