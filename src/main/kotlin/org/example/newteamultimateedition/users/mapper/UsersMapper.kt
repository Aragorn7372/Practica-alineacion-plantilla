package org.example.newteamultimateedition.users.mapper


import org.example.newteamultimateedition.users.dao.UsersEntity
import org.example.newteamultimateedition.users.models.User

/**
 * Clase que representa un mapper de usuarios
 */
class UsersMapper {
    /**
     * Transforma un [User] a un [UsersEntity]
     * @param user El usuario
     * @return [UsersEntity]
     */
    fun toEntity(user: User): UsersEntity {
        return UsersEntity(
            name = user.name,
            password = user.password,
            admin = user.isAdmin,
        )
    }

    /**
     * Transforma un [UsersEntity] en un [User]
     * @param user El [UsersEntity]
     * @return [User]
     */
    fun toModel(user: UsersEntity): User {
        return User(
            name = user.name,
            password = user.password,
            isAdmin = user.admin,
        )
    }
}