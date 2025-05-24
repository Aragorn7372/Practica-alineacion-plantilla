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
    @SqlQuery("SELECT * FROM alineacion")
    fun getAll(): List<AlineacionEntity>

    @SqlQuery("SELECT * FROM alineacion where id = :id")
    fun getById(@Bind("id") id: Long): AlineacionEntity?

    @SqlQuery("SELECT * FROM alineacion where fecha_juego = :fecha ")
    fun getByFechaJuego(@Bind("fecha") fecha: LocalDate): AlineacionEntity?

    @SqlUpdate("INSERT INTO alineacion (fecha_creacion,fecha_actualizacion,fecha_juego) VALUES (:createAt, :updateAt, :juegoDate)")
    @GetGeneratedKeys("id")
    fun save(@BindBean alineacion: AlineacionEntity): Int

    @SqlUpdate("UPDATE alineacion SET fecha_creacion= :createAt, fecha_actualizacion = :updatedAt, fecha_juego= :juegoDate WHERE id = :identificacion")
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
