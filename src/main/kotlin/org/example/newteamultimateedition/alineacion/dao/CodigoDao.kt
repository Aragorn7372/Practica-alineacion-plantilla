package org.example.newteamultimateedition.alineacion.dao

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean

import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.lighthousegames.logging.logging


interface CodigoDao {
    @SqlQuery("SELECT * FROM codigo")
    fun getAll(): List<CodigoAlineacionEntity>

    @SqlQuery("SELECT * FROM codigo where uuid = :id")
    fun getById(@Bind("id") id: String): CodigoAlineacionEntity?

    @SqlQuery("SELECT * FROM codigo where id_alineacion = :alinecionId ")
    fun getByAlineacionId(@Bind("alinecionId") alinecionId: Long): List<CodigoAlineacionEntity>

    @SqlUpdate("DELETE FROM codigo WHERE id = :alinecionId")
    fun deleteByAlinecionId(@Bind("alinecionId") alinecionId: Long):Int

    @SqlUpdate("INSERT INTO codigo (uuid,id_persona,id_alineacion,posicion) VALUES (:id, :personalId,:alineacionId,:posicion) ")
    fun save(@BindBean codigo: CodigoAlineacionEntity): Int

    @SqlUpdate("UPDATE codigo SET posicion=:posicion WHERE id = :identificacion")
    fun updateById(@BindBean codigo: CodigoAlineacionEntity, @Bind("identificacion") identificador:String): Int


    @SqlUpdate("DELETE  FROM codigo WHERE uuid=:id")
    fun deleteById(@Bind("id") id: String): Int

    @SqlUpdate("DELETE  FROM codigo")
    fun deleteAll(): Int
}
fun provideCodigoDao(jdbi: Jdbi): CodigoDao {
    val logger= logging()
    logger.info { "obteniendo personas dao" }
    return jdbi.onDemand(CodigoDao::class.java)
}
