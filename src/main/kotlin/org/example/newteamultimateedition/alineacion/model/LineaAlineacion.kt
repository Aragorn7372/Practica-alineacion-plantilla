package org.example.newteamultimateedition.alineacion.model

import java.util.UUID

data class LineaAlineacion(
    val id: UUID=UUID.randomUUID(),
    val idAlineacion: Long=0L,
    val idPersona: Long,
    val posicion: Int,
)
