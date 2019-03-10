
# -- Attendant Assigned Resource schema

# --- !Ups

# --- TODO: set the primary key as a combination of attendant_id, and resource_id
CREATE TABLE attendant_assigned_resource (
    id INT AUTO_INCREMENT PRIMARY KEY,

    attendant_id INT,
    FOREIGN KEY (attendant_id) REFERENCES attendant(id),

    resource_id INT,
    FOREIGN KEY (resource_id) REFERENCES resource(id),

    shared_amount INT
);

# --- !Downs

DROP TABLE attendant_assigned_resource;