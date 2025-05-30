package org.example.newteamultimateedition.alineacion.dao

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean

import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.lighthousegames.logging.logging


interface LineaAlineacionDao {
    @SqlQuery("SELECT uuid AS id, id_persona AS personalId, id_alineacion AS alineacionId, posicion AS posicion  FROM linea_alineacion")
    fun getAll(): List<LineaAlineacionEntity>

    @SqlQuery("SELECT uuid AS id, id_persona AS personalId, id_alineacion AS alineacionId, posicion AS posicion  FROM linea_alineacion where uuid = :id")
    fun getById(@Bind("id") id: String): LineaAlineacionEntity?

    @SqlQuery("SELECT uuid AS id, id_persona AS personalId, id_alineacion AS alineacionId, posicion AS posicion  FROM linea_alineacion where id_alineacion = :alinecionId ")
    fun getByAlineacionId(@Bind("alinecionId") alinecionId: Long): List<LineaAlineacionEntity>

    @SqlUpdate("DELETE FROM linea_alineacion WHERE id_alineacion = :alinecionId")
    fun deleteByAlinecionId(@Bind("alinecionId") alinecionId: Long):Int

    @SqlUpdate("INSERT INTO linea_alineacion (uuid,id_persona,id_alineacion,posicion) VALUES (:id, :personalId,:alineacionId,:posicion) ")
    fun save(@BindBean codigo: LineaAlineacionEntity): Int

    @SqlUpdate("UPDATE linea_alineacion SET posicion=:posicion WHERE uuid = :identificacion")
    fun updateById(@BindBean codigo: LineaAlineacionEntity, @Bind("identificacion") identificador:String): Int


    @SqlUpdate("DELETE  FROM linea_alineacion WHERE uuid=:id")
    fun deleteById(@Bind("id") id: String): Int

    @SqlUpdate("DELETE  FROM linea_alineacion")
    fun deleteAll(): Int
}
fun provideLineaAlineacionDao(jdbi: Jdbi): LineaAlineacionDao {
    val logger= logging()
    logger.info { "obteniendo personas dao" }
    return jdbi.onDemand(LineaAlineacionDao::class.java)
}
/*
 uuid VARCHAR NOT NULL PRIMARY KEY,
    id_persona BIGINT NOT NULL,
    id_alineacion BIGINT NOT NULL,
    posicion INT NOT NULL
 */
/*
 val id: String,
    val personalId: Long,
    val alineacionId: Long,
    val posicion: Int,
 */
