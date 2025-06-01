package org.example.newteamultimateedition.alineacion.dao


import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.lighthousegames.logging.logging
import java.time.LocalDate
/**
 * Interfaz que representa el DAO que realiza las operaciones CRUD sobre la tabla alineacion de la base de datos.
 */
interface AlineacionDao {

    /**
     * Ejecuta una consulta que devuelve todas las alineaciones de la tabla.
     * @return Una lista de todas las alineaciones de la tabla.
     * @see [AlineacionEntity]
     */
    @SqlQuery("SELECT id AS id, fecha_creacion AS createdAt, fecha_actualizacion AS updatedAt, fecha_juego AS juegoDate, id_entrenador AS idEntrenador, descripcion AS descripcion FROM alineacion")
    fun getAll(): List<AlineacionEntity>


    /**
     * Ejecuta una consulta que devuelve la alineación  de la tabla cuyo id coincide con el id buscado, en caso de existir.
     * @return La alineación con el id buscado, o null en caso de no existir.
     * @see [AlineacionEntity]
     */
    @SqlQuery("SELECT id AS id, fecha_creacion AS createdAt, fecha_actualizacion AS updatedAt, fecha_juego AS juegoDate, id_entrenador AS idEntrenador, descripcion AS descripcion  FROM alineacion where id = :id")
    fun getById(@Bind("id") id: Long): AlineacionEntity?


    /**
     * Ejecuta una consulta que devuelve la alineación  de la tabla cuya fecha coincide con la buscada, en caso de existir.
     * @return La alineación con la fecha buscada, o null en caso de no existir.
     * @see [AlineacionEntity]
     */
    @SqlQuery("SELECT id AS id, fecha_creacion AS createdAt, fecha_actualizacion AS updatedAt, fecha_juego AS juegoDate, id_entrenador AS idEntrenador, descripcion AS descripcion  FROM alineacion where fecha_juego = :fecha ")
    fun getByFechaJuego(@Bind("fecha") fecha: LocalDate): AlineacionEntity?

    /**
     * Inserta una nueva alineación en la base de datos.
     * @return el id que la base de datos le asigna a la nueva alineación insertada.
     * @see [AlineacionEntity]
     */
    @SqlUpdate("INSERT INTO alineacion ( fecha_creacion,fecha_actualizacion,fecha_juego, id_entrenador, descripcion) VALUES (:createdAt, :updatedAt, :juegoDate, :idEntrenador, :descripcion) ")
    @GetGeneratedKeys("id")
    fun save(@BindBean alineacion: AlineacionEntity): Int

    /**
     * Actualiza una alineación en la base de datos.
     * @return El número de filas de la tabla alineacion actualizadas.
     * @see [AlineacionEntity]
     */
    @SqlUpdate("UPDATE alineacion SET fecha_creacion= :createdAt, fecha_actualizacion = :updatedAt, fecha_juego= :juegoDate, id_entrenador= :idEntrenador, descripcion= :descripcion WHERE id = :identificacion")
    fun updateById(@BindBean alineacion: AlineacionEntity,@Bind("identificacion") identificacion:Long): Int

    /**
     * Elimina de la base de datos la alineación con el id que le entra por parámetro, en caso de existir.
     * @return El número de filas de la tabla alineacion eliminadas.
     * @see [AlineacionEntity]
     */
    @SqlUpdate("DELETE  FROM alineacion WHERE id=:id")
    fun deleteById(@Bind("id") id: Long): Int

    /**
     * Elimina todo el contenido de la tabla alineacion
     */
    @SqlUpdate("DELETE  FROM alineacion")
    fun deleteAll(): Int
}


/**
 * Proporciona un DAO de alineaciones.
 * @param jdbi el JDBI.
 */
fun provideAlineacionDao(jdbi: Jdbi): AlineacionDao {
    val logger= logging()
    logger.info { "obteniendo personas dao" }
    return jdbi.onDemand(AlineacionDao::class.java)
}
