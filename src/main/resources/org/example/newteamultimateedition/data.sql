DELETE FROM users;
DELETE FROM empleados;

INSERT INTO users (nombre_usuario, contraseña, admin) VALUES
    ('admin','$2a$12$njVi6cZIbyGYQNGvOmZW7udWo88v.6hmbqHT5m2VhmY94sJA/xRT.', true),
    ('emilis246','$2a$12$jSF56a/No8aZA/3ANkGpWOwEd4mpCyVgwdA4MO0ZIiQoZoZwswFxS', false);
