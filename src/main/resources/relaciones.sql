--Relaciones entre tablas
ALTER TABLE codigo
    ADD CONSTRAINT fk_codigo_persona
        FOREIGN KEY (id_persona) REFERENCES empleados(id)
            ON DELETE CASCADE;


ALTER TABLE codigo
    ADD CONSTRAINT fk_codigo_alineacion
        FOREIGN KEY (id_alineacion) REFERENCES alineacion(id)
            ON DELETE CASCADE;