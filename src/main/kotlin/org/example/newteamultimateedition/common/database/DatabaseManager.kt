package org.example.newteamultimateedition.common.database

import org.example.newteamultimateedition.common.config.Config
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.lighthousegames.logging.logging
import java.util.*

/**
 * Clase que representa el JDBI, para simplificar las interacciones con la base de datos.
 * @see [Config.configProperties]
 */
class DatabaseManager(
    private val url: String,
    private val initData: Boolean,
    private val initTables: Boolean,
){
    private val logger= logging()
    val jdbi: Jdbi by lazy {
        logger.debug { "inicializando jdbi" }

        Jdbi.create(url)

    }
    init {
        jdbi.installPlugin(KotlinPlugin())
        jdbi.installPlugin(SqlObjectPlugin())

        if(initTables) (ejecutarScriptSql("tables.sql"))

        if (initData) ejecutarScriptSql("data.sql")
    }

    private fun ejecutarScriptSql(file: String) {
        val scriptString= ClassLoader.getSystemResourceAsStream(file)?.bufferedReader()!!
        val script = scriptString.readText()
        jdbi.useHandle<Exception> {it->
            it.createScript(script).execute()
        }
    }
}
fun provideDatabaseManager(): Jdbi {
    val logger = logging()
    logger.debug { "Obteniendo config properties..." }

    val config = Config.configProperties

    logger.debug {
        "Config.url=${config.databaseUrl}, initData=${config.databaseInitData}, initTables=${config.databaseInitTables}"
    }

    val databaseManager = DatabaseManager(
        config.databaseUrl,
        config.databaseInitData,
        config.databaseInitTables,
    )
    return databaseManager.jdbi
}
