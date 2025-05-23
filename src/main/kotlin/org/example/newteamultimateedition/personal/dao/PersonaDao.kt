package org.example.newteamultimateedition.personal.dao

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.lighthousegames.logging.logging

interface PersonaDao {
    @SqlQuery("SELECT * FROM empleados")
    fun getAll(): List<PersonaEntity>

    @SqlQuery("SELECT * FROM empleados where id = :id")
    fun getById(@Bind("id") id: Long): PersonaEntity?

    @SqlUpdate("INSERT INTO empleados (nombre, apellidos, fecha_nacimiento, fecha_incorporacion, salario, pais, rol, especialidad, posicion, dorsal, altura, peso, goles, partidos_jugados, minutos_jugados, createdAt, updatedAt, imagen) VALUES (:nombre, :apellidos, :fechaNacimiento, :fechaIncorporacion, :salario, :pais, :rol, :especialidad, :posicion, :dorsal, :altura, :peso, :goles, :partidosJugados, :minutosJugados, :createdAt, :updatedAt, :imagen)")
    @GetGeneratedKeys("id") //Por que como el id es autonumérico y generado por la BBDD, lo necesitamos, es lo que devuelve la función
    fun save(@BindBean persona: PersonaEntity): Int

    @SqlUpdate("UPDATE empleados SET nombre = :nombre, apellidos = :apellidos, fecha_nacimiento = :fechaNacimiento, fecha_incorporacion = :fechaIncorporacion, salario = :salario, pais = :pais, rol = :rol, especialidad = :especialidad, posicion = :posicion, dorsal = :dorsal, altura = :altura, peso = :peso, goles = :goles, partidos_jugados = :partidosJugados, minutos_jugados = :minutosJugados, createdAt = :createdAt, updatedAt = :updatedAt, imagen = :imagen WHERE id = :identification")
    fun update(@BindBean persona: PersonaEntity, @Bind("identification") identification: Long): Int

    @SqlUpdate("DELETE  FROM empleados WHERE id=:id")
    fun deleteById(@Bind("id") id: Long): Int

    @SqlUpdate("DELETE  FROM empleados")
    fun deleteAll(): Int
}
fun getPersonasDao(jdbi: Jdbi): PersonaDao {
    val logger= logging()
    logger.info { "obteniendo personas dao" }
    return jdbi.onDemand(PersonaDao::class.java)
}
/*
val id: Int=0,
    val tipo: String,
    val nombre: String,
    val apellidos: String,
    val fechaNacimiento: LocalDate,
    val fechaIncorporacion: LocalDate,
    val salario: Double,
    val pais: String,
    val especialidad: String?=null,
    val posicion: String?=null,
    val dorsal: Int?=null,
    val altura: Double?=null,
    val peso: Double?=null,
    val goles: Int?=null,
    val partidosJugados: Int?=null,
id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    nombre VARCHAR NOT NULL,
    apellidos VARCHAR NOT NULL,
    fecha_nacimiento DATE NOT NULL ,
    fecha_incorporacion DATE NOT NULL ,
    salario DECIMAL(2-9,2) NOT NULL,
    pais VARCHAR NOT NULL,
    rol VARCHAR NOT NULL,
    especialidad VARCHAR DEFAULT NULL,
    posicion VARCHAR DEFAULT NULL,
    dorsal INT DEFAULT NULL,
    altura DOUBLE(1,2) DEFAULT NULL,
    peso DOUBLE(1,2) DEFAULT NULL,
    goles INT DEFAULT NULL,
    partidos_jugados INT DEFAULT NULL
 */