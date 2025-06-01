package org.example.newteamultimateedition.users.dao

/**
 * Representa a la entidad de un usuario
 * @property name nombre del usuario
 * @property password el hash de la contraseña
 * @property admin [true] si es admin [false] si no lo es
 */
data class UsersEntity(
    val name: String,
    val password: String,
    val admin : Boolean,
)
