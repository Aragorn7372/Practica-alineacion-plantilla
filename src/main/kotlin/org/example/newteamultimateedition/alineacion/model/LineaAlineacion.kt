package org.example.newteamultimateedition.alineacion.model

import java.util.UUID

/**
 * Clase que representa una línea de alineación
 * @param id Identificador de la línea de alineación
 * @param idAlineacion Identificador de la alineación a la que pertenece
 * @param idPersona Identificador del jugador
 * @param posicion posición del jugador en la alineación
 * @see [Alineacion]
 */
data class LineaAlineacion(
    val id: UUID=UUID.randomUUID(),
    val idAlineacion: Long=0L,
    val idPersona: Long,
    val posicion: Int,
)
