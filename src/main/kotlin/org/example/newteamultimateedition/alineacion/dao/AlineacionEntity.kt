package org.example.newteamultimateedition.alineacion.dao

import java.time.LocalDate
import java.time.LocalDateTime

data class AlineacionEntity(
    val id: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val juegoDate: LocalDate
) {
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