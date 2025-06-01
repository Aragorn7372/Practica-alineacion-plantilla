package org.example.newteamultimateedition.users.service

import org.example.newteamultimateedition.common.service.Service
import org.example.newteamultimateedition.users.exception.UsersException
import org.example.newteamultimateedition.users.models.User

/**
 * Interfaz que tipifica las operaciones CRUD en [User], [UsersException] y [String]
 */
interface UsersService: Service<User, UsersException, String> {
}