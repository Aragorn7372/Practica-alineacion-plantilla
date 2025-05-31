package org.example.newteamultimateedition.users.dao



import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.lighthousegames.logging.logging

/**
 * DAO de usuarios para la tabla de users
 */
interface UsersDao {

    /**
     * Ejecuta una consulta SQL que obtiene todos los usuarios de la tabla users
     */
    @SqlQuery("SELECT nombre_usuario AS name, contraseña AS password, admin FROM users")
    fun getAll(): List<UsersEntity>

    /**
     * Ejecuta una consulta SQL que obtiene un usuario dado un nombre de usuario
     */
    @SqlQuery("SELECT nombre_usuario AS name, contraseña AS password, admin FROM users WHERE nombre_usuario = :name")
    fun getByName(@Bind("name") name: String): UsersEntity?

    /**
     * Ejecuta una operación SQL que inserta un nuevo usuario en la tabla users
     */
    @SqlUpdate("INSERT INTO users (nombre_usuario, contraseña, admin) VALUES (:name, :password, :admin)")
    fun save(@BindBean user: UsersEntity): Int

    /**
     * Ejecuta una operación SQL que actualiza un nuevo usuario de la tabla users
     */
    @SqlUpdate("UPDATE users SET contraseña = :password, admin = :admin WHERE nombre_usuario = :nameuser")
    fun update(@BindBean user: UsersEntity, @Bind("nameuser") nameuser: String): Int

    /**
     * Ejecuta una operación SQL que elimina un usuario de la tabla users dado su nombre
     */
    @SqlUpdate("DELETE FROM users WHERE nombre_usuario = :name")
    fun delete(@Bind("name") name: String): Int

    /**
     * Elimina toda la información de la tabla users
     */
    @SqlUpdate("DELETE FROM users")
    fun deleteAll(): Int
}

/**
 * Proporciona un DAO de usuarios
 */
fun provideUsersDao(jdbi: Jdbi): UsersDao {
    val logger= logging()
    logger.info { "obteniendo dao de usuarios" }
    return jdbi.onDemand(UsersDao::class.java)
}