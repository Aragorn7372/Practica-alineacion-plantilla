package org.example.newteamultimateedition.alineacion.cache

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.common.config.Config

import org.lighthousegames.logging.logging

fun darAlineacionCache(
): Cache<Long, Alineacion> {
    val size= Config.configProperties.cacheSize
    val logger= logging()
    logger.debug { "creando cache" }
    return Caffeine.newBuilder().maximumSize(size).build()
}