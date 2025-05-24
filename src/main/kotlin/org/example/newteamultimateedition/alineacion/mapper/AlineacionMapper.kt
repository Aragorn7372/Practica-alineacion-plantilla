package org.example.newteamultimateedition.alineacion.mapper

import org.example.newteamultimateedition.alineacion.dao.AlineacionEntity
import org.example.newteamultimateedition.alineacion.dao.CodigoAlineacionEntity
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.model.CodigoAlineacion
import java.util.*

class AlineacionMapper {
    fun toModel(alineacionEntity: AlineacionEntity, list:List<CodigoAlineacion>): Alineacion {

        return Alineacion(
            id = alineacionEntity.id,
            createdAt = alineacionEntity.createdAt,
            updatedAt = alineacionEntity.updatedAt,
            juegoDate = alineacionEntity.juegoDate,
            personalList = list
        )
    }
    fun toEntity(model: Alineacion): AlineacionEntity {

        return AlineacionEntity(
            id = model.id,
            createdAt = model.createdAt,
            updatedAt = model.updatedAt,
            juegoDate = model.juegoDate,
        )
    }
    fun toEntity(model: CodigoAlineacion): CodigoAlineacionEntity {
        return CodigoAlineacionEntity(
            id = model.id.toString(),
            personalId = model.idPersona,
            alineacionId = model.idAlineacion,
            posicion = model.posicion
        )
    }
    fun toModel(model: CodigoAlineacionEntity): CodigoAlineacion {
        return CodigoAlineacion(
            id = UUID.fromString(model.id),
            idPersona = model.personalId,
            idAlineacion = model.alineacionId,
            posicion = model.posicion
        )
    }
}