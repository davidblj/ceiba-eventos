
# -- Assigned resources schema

# --- !Ups

# --- TODO: set the primary key as a combination of the resource_id, and the attendant_id
CREATE TABLE assigned_resources (
    id INT AUTO_INCREMENT PRIMARY KEY,

    resource_id INT,
    FOREIGN KEY (resource_id) REFERENCES resource(id),

    attendant_id INT,
    FOREIGN KEY (attendant_id) REFERENCES attendant(id),

    assigned_amount INT
);

# --- !Downs

DROP TABLE assigned_resources;