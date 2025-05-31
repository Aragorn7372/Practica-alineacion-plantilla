package org.example.newteamultimateedition.users.repository

import org.example.newteamultimateedition.common.repository.Repository
import org.example.newteamultimateedition.users.models.User

/**
 * Interfaz que tipifica las operaciones Crud para que sean válidas solo con [User] y [String]
 */
interface UsersRepository: Repository<User, String>
