package org.example.newteamultimateedition.alineacion.mapper

import org.example.newteamultimateedition.alineacion.dao.AlineacionEntity
import org.example.newteamultimateedition.alineacion.dao.LineaAlineacionEntity
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.model.LineaAlineacion
import org.example.newteamultimateedition.personal.models.Entrenador
import java.util.*

class AlineacionMapper {
    fun toModel(alineacionEntity: AlineacionEntity, list:List<LineaAlineacion>,entrenador: Entrenador): Alineacion {

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
    fun toEntity(model: Alineacion): AlineacionEntity {

        return AlineacionEntity(
            id = model.id,
            createdAt = model.createdAt,
            updatedAt = model.updatedAt,
            juegoDate = model.juegoDate,
            idEntrenador = model.entrenador.id,
            descripcion = model.descripcion,
        )
    }
    fun toEntity(model: LineaAlineacion): LineaAlineacionEntity {
        return LineaAlineacionEntity(
            id = model.id.toString(),
            personalId = model.idPersona,
            alineacionId = model.idAlineacion,
            posicion = model.posicion
        )
    }
    fun toModel(model: LineaAlineacionEntity): LineaAlineacion {
        return LineaAlineacion(
            id = UUID.fromString(model.id),
            idPersona = model.personalId,
            idAlineacion = model.alineacionId,
            posicion = model.posicion
        )
    }
}