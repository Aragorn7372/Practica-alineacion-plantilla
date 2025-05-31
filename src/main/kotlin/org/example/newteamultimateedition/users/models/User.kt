package org.example.newteamultimateedition.users.models

/**
 * Clase que representa los usuarios del programa
 * @param name el nombre del usuario
 * @param password la contraseña del usuario
 * @param isAdmin si es admin o no
 */
data class User(
    val name: String,
    val password: String,
    val isAdmin: Boolean=false,
)
