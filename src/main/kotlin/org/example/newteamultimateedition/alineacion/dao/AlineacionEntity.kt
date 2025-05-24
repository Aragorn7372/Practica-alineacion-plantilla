package org.example.newteamultimateedition.alineacion.dao

import java.time.LocalDate
import java.time.LocalDateTime

data class AlineacionEntity(
    val id: Long,
    val uuidPrimero: String,
    val uuidSegundo: String,
    val uuidTercero: String,
    val uuidCuarto: String,
    val uuidQuinto: String,
    val uuidSexto: String,
    val uuidSeptimo: String,
    val uuidOctavo: String,
    val uuidNoveno: String,
    val uuidDecimo: String,
    val uuidOnceavo: String,
    val uuidDoceavo: String,
    val uuidTreceavo: String,
    val uuidCatorceavo: String,
    val uuidEntrenador: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val juegoDate: LocalDate
) {
    companion object {
        fun fromList(
            id: Long,
            uuidList: List<String>,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime,
            juegoDate: LocalDate
        ): AlineacionEntity {
            return AlineacionEntity(
                id = id,
                uuidPrimero = uuidList[0],
                uuidSegundo = uuidList[1],
                uuidTercero = uuidList[2],
                uuidCuarto = uuidList[3],
                uuidQuinto = uuidList[4],
                uuidSexto = uuidList[5],
                uuidSeptimo = uuidList[6],
                uuidOctavo = uuidList[7],
                uuidNoveno = uuidList[8],
                uuidDecimo = uuidList[9],
                uuidOnceavo = uuidList[10],
                uuidDoceavo = uuidList[11],
                uuidTreceavo = uuidList[12],
                uuidCatorceavo = uuidList[13],
                uuidEntrenador = uuidList[14],
                createdAt = createdAt,
                updatedAt = updatedAt,
                juegoDate = juegoDate
            )
        }
    }
}
/*
 uuid_primero VARCHAR NOT NULL,
    uuid_segundo VARCHAR NOT NULL,
    uuid_tercero VARCHAR NOT NULL,
    uuid_cuarto VARCHAR NOT NULL,
    uuid_quinto VARCHAR NOT NULL,
    uuid_sexto VARCHAR NOT NULL,
    uuid_septimo VARCHAR NOT NULL,
    uuid_octavo VARCHAR NOT NULL,
    uuid_noveno VARCHAR NOT NULL,
    uuid_decimo VARCHAR NOT NULL,
    uuid_onceavo VARCHAR NOT NULL,
    uuid_doceavo VARCHAR NOT NULL,
    uuid_treceavo VARCHAR NOT NULL,
    uuid_catorceavo VARCHAR NOT NULL,
    uuid_entrenador VARCHAR NOT NULL
 */