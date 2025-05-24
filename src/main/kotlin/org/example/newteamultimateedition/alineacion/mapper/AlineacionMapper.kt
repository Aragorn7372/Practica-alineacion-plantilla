package org.example.newteamultimateedition.alineacion.mapper

import org.example.newteamultimateedition.alineacion.dao.AlineacionEntity
import org.example.newteamultimateedition.alineacion.dao.CodigoEntity
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.model.Codigo
import java.util.*

class AlineacionMapper {
    fun toDatabaseModel(alineacionEntity: AlineacionEntity,list:List<Codigo>): Alineacion {

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
    fun toEntity(model: Codigo): CodigoEntity {
        return CodigoEntity(
            id = model.id.toString(),
            personalId = model.idPersona,
            alineacionId = model.idAlineacion,
            posicion = model.posicion
        )
    }
    fun toModel(model: CodigoEntity): Codigo {
        return Codigo(
            id = UUID.fromString(model.id),
            idPersona = model.personalId,
            idAlineacion = model.alineacionId,
            posicion = model.posicion
        )
    }
}