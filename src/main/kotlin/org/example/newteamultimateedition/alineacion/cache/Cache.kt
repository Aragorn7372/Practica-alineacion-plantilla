package org.example.newteamultimateedition.alineacion.cache

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.common.config.Config

import org.lighthousegames.logging.logging

private val logger = logging()

/**
 * Proporciona una caché de [Alineacion], con un tamaño determinado por [Config.configProperties]
 */
fun darAlineacionCache(
): Cache<Long, Alineacion> {
    logger.debug { "Proporcionando caché de Alineación" }
    val size= Config.configProperties.cacheSize
    val logger= logging()
    logger.debug { "creando cache" }
    return Caffeine.newBuilder().maximumSize(size).build()
}