package org.example.newteamultimateedition.users.cache

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.example.newteamultimateedition.common.config.Config
import org.example.newteamultimateedition.personal.models.Persona
import org.example.newteamultimateedition.users.models.User
import org.lighthousegames.logging.logging

fun darUsersCache(
): Cache<Long, User> {
    val size= 1L
    val logger= logging()
    logger.debug { "Creando cache de sesion" }
    return Caffeine.newBuilder().maximumSize(size).build()
}