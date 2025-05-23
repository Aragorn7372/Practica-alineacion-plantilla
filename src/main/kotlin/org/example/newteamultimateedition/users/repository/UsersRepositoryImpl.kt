package org.example.newteamultimateedition.users.repository

import org.example.newteamultimateedition.users.dao.UsersDao
import org.example.newteamultimateedition.users.mapper.UsersMapper
import org.example.newteamultimateedition.users.models.User

/**
 * @param usersDao el dao a usar
 * @param mapper el que castea y transforma las entidades a modelos y al revers
 */
class UsersRepositoryImpl(
    private val usersDao: UsersDao,
    private val mapper: UsersMapper
): UsersRepository {
    /**
     * devuelve una lista de usuarios
     * @return lista a devolver
     */
    override fun getAll(): List<User> {
        return usersDao.getAll().map { mapper.toModel(it) }
    }

    /**
     * obtiene un usuario en base a una id
     * @param id indicador principal para obtener un usuario
     * @return devuelve un usuario o nulo si no lo encuentra
     */
    override fun getById(id: String): User? {
        return usersDao.getByName(id)?.let { mapper.toModel(it) }
    }

    /**
     * actualiza los datos de un usuario en base a un usuario y un id
     * @param objeto datos a actualizar
     * @param id indicativo del usuario a cambiar
     * @return devuelve el usuario o nulo dependiendo de si lo actualiza o no
     */
    override fun update(objeto: User, id: String): User? {
        val updated = usersDao.update(mapper.toEntity(objeto), id)
        return if (updated == 1) {
            objeto.copy(name = id)
        } else {
            null
        }
    }

    /**
     * elimina un usuario en base a una id
     * @param id indicativo de los datos a eliminar
     * @return devuelve el usuario o nulo si no lo encuentra o no es capaz de eliminarlo
     */
    override fun delete(id: String): User? {
        val user = usersDao.getByName(id)?.let {
            if(usersDao.delete(id)==1) return mapper.toModel(it) else null
        }
        return user
    }

    /**
     * guarda un usuario
     * @param objeto usuario a guardar
     * @return usuario guardado
     */
    override fun save(objeto: User): User {
        usersDao.save(mapper.toEntity(objeto))
        return objeto
    }
}