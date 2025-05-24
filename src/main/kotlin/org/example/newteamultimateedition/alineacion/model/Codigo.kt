package org.example.newteamultimateedition.alineacion.model

import java.util.UUID

data class Codigo(
    val id: UUID,
    val idAlineacion: Long,
    val idPersona: Long,
    val posicion: Int,
)
