package org.example.newteamultimateedition.alineacion.mapper

import org.example.newteamultimateedition.alineacion.dao.AlineacionEntity
import org.example.newteamultimateedition.alineacion.model.Alineacion
import java.util.*

class AlineacionMapper {
    fun toDatabaseModel(alineacionEntity: AlineacionEntity): Alineacion {

        return Alineacion(
            id = alineacionEntity.id,
            createdAt = alineacionEntity.createdAt,
            updatedAt = alineacionEntity.updatedAt,
            juegoDate = alineacionEntity.juegoDate,
            personalList = listOf(UUID.fromString(alineacionEntity.uuidPrimero),
            UUID.fromString(alineacionEntity.uuidSegundo),
            UUID.fromString(alineacionEntity.uuidTercero),
            UUID.fromString(alineacionEntity.uuidCuarto),
            UUID.fromString(alineacionEntity.uuidQuinto),
            UUID.fromString(alineacionEntity.uuidSexto),
            UUID.fromString(alineacionEntity.uuidSeptimo),
            UUID.fromString(alineacionEntity.uuidOctavo),
            UUID.fromString(alineacionEntity.uuidNoveno),
            UUID.fromString(alineacionEntity.uuidDecimo),
            UUID.fromString(alineacionEntity.uuidOnceavo),
            UUID.fromString(alineacionEntity.uuidDoceavo),
            UUID.fromString(alineacionEntity.uuidTreceavo),
            UUID.fromString(alineacionEntity.uuidCatorceavo),
            UUID.fromString(alineacionEntity.uuidEntrenador)
            )
        )
    }
    fun toEntity(model: Alineacion): AlineacionEntity {
        val list= model.personalList.map { it.toString() }
        return AlineacionEntity.fromList(
            id = model.id,
            createdAt = model.createdAt,
            updatedAt = model.updatedAt,
            juegoDate = model.juegoDate,
            uuidList = list
        )
    }
}