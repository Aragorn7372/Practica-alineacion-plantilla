package org.example.newteamultimateedition.users.service

import com.github.michaelbull.result.*

import org.example.newteamultimateedition.users.exception.UsersException
import org.example.newteamultimateedition.users.models.User
import org.example.newteamultimateedition.users.repository.UsersRepository
import org.lighthousegames.logging.logging
import org.mindrot.jbcrypt.BCrypt

/**
 * @param repositorio repositorio a usar
 */
class UsersServiceImpl(
    private val repositorio: UsersRepository,
): UsersService {
    private val logger = logging()

    /**
     * devuelve una lista de usuarios
     * @return devuelve una lista si transcurre sin inconvenientes o un error si falla la base de datos
     */
    override fun getAll(): Result<List<User>, UsersException> {
        logger.debug { "obteniendo todos los usuarios" }
        try {
            return Ok(repositorio.getAll())
        }catch (e:Exception){
            return Err(UsersException.DatabaseException(e.message.toString()))
        }

    }

    /**
     * devuelve un usuario en base a una id
     * @param id indicativo para encontrar al usuario
     * @return el usuario o un error
     */
    override fun getByID(id: String): Result<User, UsersException> {

        try {
            return repositorio.getById(id)?.let { Ok(it) } ?: Err(UsersException.UsersNotFoundException(id))
        }catch (e:Exception){
            return Err(UsersException.DatabaseException(e.message.toString()))
        }
    }

    /**
     * guarda un usuario
     * @param item usuario a guardar
     * @return usuario o error
     */
    override fun save(item: User): Result<User, UsersException> {
        try {
            return Ok(repositorio.save(item))
        }catch (e:Exception){
            return Err(UsersException.DatabaseException(e.message.toString()))
        }

    }

    /**
     * elimina un usuario
     * @param id inidicativo del usuario a guardar
     * @return usuario eliminado o error
     */
    override fun delete(id: String): Result<User, UsersException> {
        try {
            return repositorio.delete(id)?.let { Ok(it) }?:
            Err(UsersException.UsersNotFoundException(id))
        }catch (e:Exception){
            return Err(UsersException.DatabaseException(e.message.toString()))
        }

    }

    /**
     * actualiza un usuario
     * @param id inidicativo del usuario a eliminar
     * @param item usuario a eliminar
     * @return usuario o error
     */
    override fun update(id: String, item: User): Result<User, UsersException> {
        try {
            return repositorio.update(item,id)?.let { Ok(it) }?:
            Err(UsersException.UsersNotFoundException(id))
        }catch (e:Exception){
            return Err(UsersException.DatabaseException(e.message.toString()))
        }
    }

    fun goodLogin(username: String, password: String): Result<User, UsersException> {
        try {
        val userResult = repositorio.getById(username)
        return if (userResult != null) {
            if (BCrypt.checkpw(password, userResult.password)) {
                logger.debug { "Contraseña valida" }
                Ok(userResult)
            } else {
                logger.debug { "Contraseña invalida $password ${userResult.password}" }
                Err(UsersException.ContraseniaEquivocadaException("Contraseña errónea"))
            }
        } else {
            logger.debug { "contraseña vacia" }
            Err(UsersException.UsersNotFoundException(username))
        }
        }catch (e:Exception){
            return Err(UsersException.DatabaseException(e.message.toString()))
        }
    }


}