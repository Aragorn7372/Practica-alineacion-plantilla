package org.example.newteamultimateedition.users.service

import org.example.newteamultimateedition.common.service.Service
import org.example.newteamultimateedition.users.exception.UsersException
import org.example.newteamultimateedition.users.models.User

interface UsersService: Service<User, UsersException, String> {
    fun getUserByUsername(username: String): User?
}