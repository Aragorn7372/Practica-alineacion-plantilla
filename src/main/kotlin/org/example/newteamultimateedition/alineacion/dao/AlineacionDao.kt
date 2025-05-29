package org.example.newteamultimateedition.alineacion.dao


import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.lighthousegames.logging.logging
import java.time.LocalDate

interface AlineacionDao {
    @SqlQuery("SELECT id AS id, fecha_creacion AS createdAt, fecha_actualizacion AS updatedAt, fecha_juego AS juegoDate, id_entrenador AS idEntrenador FROM alineacion")
    fun getAll(): List<AlineacionEntity>

    @SqlQuery("SELECT id AS id, fecha_creacion AS createdAt, fecha_actualizacion AS updatedAt, fecha_juego AS juegoDate, id_entrenador AS idEntrenador  FROM alineacion where id = :id")
    fun getById(@Bind("id") id: Long): AlineacionEntity?

    @SqlQuery("SELECT id AS id, fecha_creacion AS createdAt, fecha_actualizacion AS updatedAt, fecha_juego AS juegoDate, id_entrenador AS idEntrenador  FROM alineacion where fecha_juego = :fecha ")
    fun getByFechaJuego(@Bind("fecha") fecha: LocalDate): AlineacionEntity?

    @SqlUpdate("INSERT INTO alineacion (fecha_creacion,fecha_actualizacion,fecha_juego, id_entrenador) VALUES (:createdAt, :updatedAt, :juegoDate, :idEntrenador)")
    @GetGeneratedKeys("id")
    fun save(@BindBean alineacion: AlineacionEntity): Int

    @SqlUpdate("UPDATE alineacion SET fecha_creacion= :createdAt, fecha_actualizacion = :updatedAt, fecha_juego= :juegoDate, id_entrenador= :idEntrenador WHERE id = :identificacion")
    fun updateById(@BindBean alineacion: AlineacionEntity,@Bind("identificacion") identificacion:Long): Int


    @SqlUpdate("DELETE  FROM alineacion WHERE id=:id")
    fun deleteById(@Bind("id") id: Long): Int

    @SqlUpdate("DELETE  FROM alineacion")
    fun deleteAll(): Int
}
fun provideAlineacionDao(jdbi: Jdbi): AlineacionDao {
    val logger= logging()
    logger.info { "obteniendo personas dao" }
    return jdbi.onDemand(AlineacionDao::class.java)
}
