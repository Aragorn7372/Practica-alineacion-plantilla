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
    fun getbyFechaJuego(@Bind("fecha") fecha: LocalDate): AlineacionEntity?

    @SqlUpdate("INSERT INTO alineacion VALUES (fecha_creacion,fecha_actualizacion,fecha_juego,uuid_primero," +
            " uuid_segundo, uuid_tercero, uuid_cuarto, uuid_quinto, uuid_sexto, uuid_septimo, uuid_octavo, " +
            "uuid_noveno, uuid_decimo, uuid_onceavo, uuid_doceavo, uuid_treceavo, " +
            "uuid_catorceavo, uuid_entrenador) values(:id, :createdAt, :updatedAt, :juegoDate, :uuidPrimero, " +
            ":uuidSegundo, :uuidTercero, :uuidCuarto, :uuidQuinto, :uuidSexto, :uuidSeptimo, :uuidOctavo, :uuidNoveno," +
            " :uuidDecimo, :uuidOnceavo, :uuidDoceavo, :uuidTreceavo, :uuidCatorceavo, :uuidEntrenador))")
    @GetGeneratedKeys("id")
    fun save(@BindBean alineacion: AlineacionEntity): Int

    @SqlUpdate("UPDATE alineacion SET fecha_creacion= :createAt, fecha_actualizacion = :updatedAt, fecha_juego= :juegoDate" +
            "uuid_primero = :uuidPrimero, uuid_segundo = :uuidSegundo, uuid_tercero = :uuidTercero, " +
            "uuid_cuarto = :uuidCuarto, uuid_quinto = :uuidQuinto, uuid_sexto = :uuidSexto, uuid_septimo = :uuidSeptimo," +
            " uuid_octavo = :uuidOctavo, uuid_noveno = :uuidNoveno, uuid_decimo = :uuidDecimo, uuid_onceavo = :uuidOnceavo, " +
            "uuid_doceavo = :uuidDoceavo, uuid_treceavo = :uuidTreceavo, uuid_catorceavo = :uuidCatorceavo," +
            " uuid_entrenador = :uuidEntrenador WHERE id = :identificacion")
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
