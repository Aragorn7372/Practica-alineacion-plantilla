package org.example.newteamultimateedition.alineacion.dao

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean

import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.lighthousegames.logging.logging


interface CodigoDao {
    @SqlQuery("SELECT * FROM codigo")
    fun getAll(): List<CodigoEntity>

    @SqlQuery("SELECT * FROM codigo where id = :id")
    fun getById(@Bind("id") id: String): CodigoEntity?

    @SqlQuery("SELECT * FROM codigo where id_alineacion = :alinecionId ")
    fun getByAlineacionId(@Bind("alinecionId") alinecionId: Long): List<CodigoEntity>

    @SqlUpdate("INSERT INTO codigo (uuid,id_persona,id_alineacion,posicion) VALUES (:id, :personalId,:alineacionId,:posicion) ")
    fun save(@BindBean codigo: CodigoEntity): Int

    @SqlUpdate("UPDATE codigo SET posicion=:posicion WHERE id = :identificacion")
    fun updateById(@BindBean codigo: CodigoEntity, @Bind("identificacion") identificador:String): Int


    @SqlUpdate("DELETE  FROM codigo WHERE id=:id")
    fun deleteById(@Bind("id") id: Long): Int

    @SqlUpdate("DELETE  FROM codigo")
    fun deleteAll(): Int
}
fun provideCodigoonDao(jdbi: Jdbi): CodigoDao {
    val logger= logging()
    logger.info { "obteniendo personas dao" }
    return jdbi.onDemand(CodigoDao::class.java)
}
