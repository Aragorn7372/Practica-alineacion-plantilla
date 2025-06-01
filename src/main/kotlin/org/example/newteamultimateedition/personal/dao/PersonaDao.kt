package org.example.newteamultimateedition.personal.dao

import org.example.newteamultimateedition.alineacion.dao.AlineacionEntity
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.lighthousegames.logging.logging

/**
 * Interfaz que representa el DAO que realiza las operaciones CRUD sobre la tabla empleados de la base de datos.
 */
interface PersonaDao {

    /**
     * Ejecuta una consulta que devuelve todas las personas de la tabla.
     * @return Una lista de todas las personas de la tabla.
     * @see [PersonaEntity]
     */
    @SqlQuery("SELECT * FROM empleados")
    fun getAll(): List<PersonaEntity>


    /**
     * Ejecuta una consulta que devuelve la persona  de la tabla cuyo id coincide con el id buscado, en caso de existir.
     * @return La persona con el id buscado, o null en caso de no existir.
     * @see [PersonaEntity]
     */
    @SqlQuery("SELECT * FROM empleados where id = :id")
    fun getById(@Bind("id") id: Long): PersonaEntity?

    /**
     * Inserta una nueva persona en la base de datos.
     * @return El id que la base de datos le asigna a la nueva persona insertada.
     * @see [PersonaEntity]
     */
    @SqlUpdate("INSERT INTO empleados (nombre, apellidos, fecha_nacimiento, fecha_incorporacion, salario, pais, rol, especialidad, posicion, dorsal, altura, peso, goles, partidos_jugados, minutos_jugados, createdAt, updatedAt, imagen, isDeleted) VALUES (:nombre, :apellidos, :fechaNacimiento, :fechaIncorporacion, :salario, :pais, :rol, :especialidad, :posicion, :dorsal, :altura, :peso, :goles, :partidosJugados, :minutosJugados, :createdAt, :updatedAt, :imagen, :isDeleted)")
    @GetGeneratedKeys("id") //Porque como el id es autonumérico y generado por la BBDD, lo necesitamos, es lo que devuelve la función
    fun save(@BindBean persona: PersonaEntity): Int

    /**
     * Actualiza una persona en la base de datos.
     * @return El número de filas de la tabla empleados actualizadas.
     * @see [PersonaEntity]
     */
    @SqlUpdate("UPDATE empleados SET nombre = :nombre, apellidos = :apellidos, fecha_nacimiento = :fechaNacimiento, fecha_incorporacion = :fechaIncorporacion, salario = :salario, pais = :pais, rol = :rol, especialidad = :especialidad, posicion = :posicion, dorsal = :dorsal, altura = :altura, peso = :peso, goles = :goles, partidos_jugados = :partidosJugados, minutos_jugados = :minutosJugados, createdAt = :createdAt, updatedAt = :updatedAt, imagen = :imagen, isDeleted= :isDeleted WHERE id = :identification")
    fun update(@BindBean persona: PersonaEntity, @Bind("identification") identification: Long): Int

    /**
     * Elimina de la base de datos la persona con el id que le entra por parámetro, en caso de existir.
     * @return El número de filas de la tabla empleados eliminadas.
     * @see [PersonaEntity]
     */
    @SqlUpdate("DELETE  FROM empleados WHERE id=:id")
    fun deleteById(@Bind("id") id: Long): Int

    /**
     * Elimina el contenido de la tabla empleados
     */
    @SqlUpdate("DELETE  FROM empleados")
    fun deleteAll(): Int
}

/**
 * Proporciona un DAO de personas
 */
fun getPersonasDao(jdbi: Jdbi): PersonaDao {
    val logger= logging()
    logger.info { "Obteniendo personas DAO" }
    return jdbi.onDemand(PersonaDao::class.java)
}