# -- Attendant schema

# --- !Ups

CREATE TABLE employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    full_name CHAR(30)
);

INSERT INTO employee(full_name)
values("Armando Zarate"),
("Diego Escobar"),
("Eliana Zapata"),
("Jorge Menco"),
("Edison Mejia"),
("Alvaro Perez"),
("Manuel Henao"),
("Daniel Correa"),
("Kedwin Perez"),
("Maria Ocampo"),
("Leidy Martinez"),
("Angel Morales"),
("Liliana Mariaca"),
("Juan Herrera"),
("Daniel Rios"),
("Naren Avila"),
("Abner Trejos"),
("Christian Herrera"),
("Edgar Parra"),
("Juan Gaviria"),
("Ivan Espinosa"),
("Adriana Frases"),
("Guillermo Díaz"),
("Jorge Arbelaez"),
("Milton Velilla"),
("Santiago Vasco"),
("Jhonatan Rojas"),
("Melissa Tejada"),
("Juan Arango"),
("Pedro Torres"),
("Jonathan Muñoz");

# --- !Downs

DROP TABLE employee;