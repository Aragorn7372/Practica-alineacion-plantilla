package org.example.newteamultimateedition.users.service

import com.github.michaelbull.result.*

import org.example.newteamultimateedition.users.exception.UsersException
import org.example.newteamultimateedition.users.models.User
import org.example.newteamultimateedition.users.repository.UsersRepository
import org.lighthousegames.logging.logging
import org.mindrot.jbcrypt.BCrypt

private val logger = logging()
class UsersServiceImpl(
    private val repositorio: UsersRepository,
): UsersService {

    override fun getAll(): Result<List<User>, UsersException> {
        try {
            return Ok(repositorio.getAll())
        }catch (e:Exception){
            return Err(UsersException.DatabaseException(e.message.toString()))
        }

    }


    override fun getByID(id: String): Result<User, UsersException> {

        try {
            return repositorio.getById(id)?.let { Ok(it) } ?: Err(UsersException.UsersNotFoundException(id))
        }catch (e:Exception){
            return Err(UsersException.DatabaseException(e.message.toString()))
        }
    }

    override fun save(item: User): Result<User, UsersException> {
        try {
            return Ok(repositorio.save(item))
        }catch (e:Exception){
            return Err(UsersException.DatabaseException(e.message.toString()))
        }

    }

    override fun delete(id: String): Result<User, UsersException> {
        try {
            return repositorio.delete(id)?.let { Ok(it) }?:
            Err(UsersException.UsersNotFoundException(id))
        }catch (e:Exception){
            return Err(UsersException.DatabaseException(e.message.toString()))
        }

    }

    override fun update(id: String, item: User): Result<User, UsersException> {
        try {
            return repositorio.update(item,id)?.let { Ok(it) }?:
            Err(UsersException.UsersNotFoundException(id))
        }catch (e:Exception){
            return Err(UsersException.DatabaseException(e.message.toString()))
        }
    }
    /*
    fun goodLogin(username: String, password: String): Result<User, UsersException> {
        val userResult = getUserByUsername(username)
        return if (userResult != null) {
            if (BCrypt.checkpw(password, userResult.password)) {
                logger.debug { "contraseña valida" }
                Ok(userResult)
            } else {
                logger.debug { "contraseña invalida $password ${userResult.password}" }
                Err(UsersException.ContraseniaEquivocadaException("Contraseña errónea"))
            }
        } else {
            logger.debug { "contraseña vacia" }
            Err(UsersException.UsersNotFoundException(username))
        }
    }

     */
}