package org.example.newteamultimateedition.alineacion.mapper

import org.example.newteamultimateedition.alineacion.dao.AlineacionEntity
import org.example.newteamultimateedition.alineacion.dao.LineaAlineacionEntity
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.model.LineaAlineacion
import org.example.newteamultimateedition.personal.models.Entrenador
import org.lighthousegames.logging.logging
import java.util.*

private val logger = logging()
/**
 * Clase que representa el mapeador entre los distintos modelos de alineaciones.
 */
class AlineacionMapper {

    /**
     * Convierte una [AlineacionEntity] en una [Alineacion]
     * @param alineacionEntity la [AlineacionEntity] a mapear
     * @param list Lista de [LineaAlineacion]
     * @param entrenador asignado a dicha alineación
     */
    fun toModel(alineacionEntity: AlineacionEntity, list:List<LineaAlineacion>,entrenador: Entrenador): Alineacion {
        logger.debug { "Pasando alineación de Entidad a Modelo" }
        return Alineacion(
            id = alineacionEntity.id,
            createdAt = alineacionEntity.createdAt,
            updatedAt = alineacionEntity.updatedAt,
            juegoDate = alineacionEntity.juegoDate,
            personalList = list,
            entrenador = entrenador,
            descripcion = alineacionEntity.descripcion,
        )
    }

    /**
     * Convierte una [Alineacion] en una [AlineacionEntity]
     * @param model la [Alineacion] a mapear
     */
    fun toEntity(model: Alineacion): AlineacionEntity {
        logger.debug { "Pasando alineación de Modelo a Entidad" }
        return AlineacionEntity(
            id = model.id,
            createdAt = model.createdAt,
            updatedAt = model.updatedAt,
            juegoDate = model.juegoDate,
            idEntrenador = model.entrenador.id,
            descripcion = model.descripcion,
        )
    }

    /**
     * Convierte una [LineaAlineacion] en una [LineaAlineacionEntity]
     * @param model la [LineaAlineacion] a mapear
     */
    fun toEntity(model: LineaAlineacion): LineaAlineacionEntity {
        logger.debug { "Pasando línea de alineación de Modelo a Entidad" }
        return LineaAlineacionEntity(
            id = model.id.toString(),
            personalId = model.idPersona,
            alineacionId = model.idAlineacion,
            posicion = model.posicion
        )
    }

    /**
     * Convierte una [LineaAlineacionEntity] en una [LineaAlineacion]
     * @param model la [LineaAlineacionEntity] a mapear
     */
    fun toModel(model: LineaAlineacionEntity): LineaAlineacion {
        logger.debug { "Pasando línea de alineación de Entidad a Modelo" }
        return LineaAlineacion(
            id = UUID.fromString(model.id),
            idPersona = model.personalId,
            idAlineacion = model.alineacionId,
            posicion = model.posicion
        )
    }
}