package org.example.newteamultimateedition.alineacion.dao

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean

import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.lighthousegames.logging.logging

/**
 * Interfaz que representa el DAO que realiza las operaciones CRUD sobre la tabla linea_alineacion de la base de datos.
 */
interface LineaAlineacionDao {


    /**
     * Ejecuta una consulta que devuelve todas las líneas de alineación de la tabla.
     * @return Una lista de todas las lineas de alienación de la tabla.
     * @see [LineaAlineacionEntity]
     */
    @SqlQuery("SELECT uuid AS id, id_persona AS personalId, id_alineacion AS alineacionId, posicion AS posicion  FROM linea_alineacion")
    fun getAll(): List<LineaAlineacionEntity>

    /**
     * Ejecuta una consulta que devuelve la línea de alineación  de la tabla cuyo id coincide con el id buscado, en caso de existir.
     * @return La línea de alineación con el id buscado, o null en caso de no existir.
     * @see [LineaAlineacionEntity]
     */
    @SqlQuery("SELECT uuid AS id, id_persona AS personalId, id_alineacion AS alineacionId, posicion AS posicion  FROM linea_alineacion where uuid = :id")
    fun getById(@Bind("id") id: String): LineaAlineacionEntity?

    /**
     * Ejecuta una consulta que devuelve la línea de alineación  de la tabla cuyo id coincide con el id buscado, en caso de existir.
     * @return La línea de alineación con el id buscado, o null en caso de no existir.
     * @see [LineaAlineacionEntity]
     */
    @SqlQuery("SELECT uuid AS id, id_persona AS personalId, id_alineacion AS alineacionId, posicion AS posicion  FROM linea_alineacion where id_alineacion = :alinecionId ")
    fun getByAlineacionId(@Bind("alinecionId") alinecionId: Long): List<LineaAlineacionEntity>


    /**
     * Elimina de la base de datos la línea de alineación con el id que le entra por parámetro, en caso de existir.
     * @return El número de filas de la tabla alineacion eliminadas.
     * @see [AlineacionEntity]
     */
    @SqlUpdate("DELETE FROM linea_alineacion WHERE id_alineacion = :alinecionId")
    fun deleteByAlinecionId(@Bind("alinecionId") alinecionId: Long):Int

    /**
     * Inserta una nueva línea de alineación en la base de datos.
     * @return el id que la base de datos le asigna a la nueva alineación insertada.
     * @see [LineaAlineacionEntity]
     */
    @SqlUpdate("INSERT INTO linea_alineacion (uuid,id_persona,id_alineacion,posicion) VALUES (:id, :personalId,:alineacionId,:posicion) ")
    fun save(@BindBean codigo: LineaAlineacionEntity): Int

    /**
     * Actualiza una línea de alineación en la base de datos.
     * @return El número de filas de la tabla linea_alineacion actualizadas.
     * @see [LineaAlineacionEntity]
     */
    @SqlUpdate("UPDATE linea_alineacion SET posicion=:posicion WHERE uuid = :identificacion")
    fun updateById(@BindBean codigo: LineaAlineacionEntity, @Bind("identificacion") identificador:String): Int

    /**
     * Elimina de la base de datos la línea de alineación con el id que le entra por parámetro, en caso de existir.
     * @return El número de filas de la tabla linea_alineacion eliminadas.
     * @see [LineaAlineacionEntity]
     */
    @SqlUpdate("DELETE  FROM linea_alineacion WHERE uuid=:id")
    fun deleteById(@Bind("id") id: String): Int

    /**
     * Elimina todo el contenido de la tabla linea_alineacion
     */
    @SqlUpdate("DELETE  FROM linea_alineacion")
    fun deleteAll(): Int
}

/**
 * Proporciona un DAO de líneas de alineación.
 *  @param jdbi el JDBI.
 */
fun provideLineaAlineacionDao(jdbi: Jdbi): LineaAlineacionDao {
    val logger= logging()
    logger.info { "obteniendo personas dao" }
    return jdbi.onDemand(LineaAlineacionDao::class.java)
}
