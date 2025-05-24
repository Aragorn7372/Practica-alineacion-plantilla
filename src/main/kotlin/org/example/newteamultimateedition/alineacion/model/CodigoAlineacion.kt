package org.example.newteamultimateedition.alineacion.model

import java.util.UUID

data class CodigoAlineacion(
    val id: UUID,
    val idAlineacion: Long,
    val idPersona: Long,
    val posicion: Int,
)
