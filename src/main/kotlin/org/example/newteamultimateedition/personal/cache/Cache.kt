package org.example.newteamultimateedition.personal.cache

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.example.newteamultimateedition.common.config.Config
import org.example.newteamultimateedition.personal.models.Persona
import org.lighthousegames.logging.logging


fun darPersonasCache(
):Cache<Long, Persona> {
    val size= Config.configProperties.cacheSize
    val logger= logging()
    logger.debug { "creando cache" }
    return Caffeine.newBuilder().maximumSize(size).build()
}