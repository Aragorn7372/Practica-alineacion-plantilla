package org.example.newteamultimateedition.users.mapper


import org.example.newteamultimateedition.users.dao.UsersEntity
import org.example.newteamultimateedition.users.models.User

class UsersMapper {
    fun toEntity(user: User): UsersEntity {
        return UsersEntity(
            name = user.name,
            password = user.password,
            admin = user.isAdmin,
        )
    }
    fun toModel(user: UsersEntity): User {
        return User(
            name = user.name,
            password = user.password,
            isAdmin = user.admin,
        )
    }
}