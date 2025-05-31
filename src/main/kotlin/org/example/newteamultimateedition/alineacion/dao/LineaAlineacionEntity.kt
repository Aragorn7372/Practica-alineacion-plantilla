package org.example.newteamultimateedition.alineacion.dao

/**
 * Clase que representa a una Línea de Alineación en forma de Entidad, para la base de datos.
 * @property id Identificador del objeto
 * @property personalId Identificador del jugador
 * @property alineacionId Identificador de la alineación
 * @property posicion la posición que ocupa dicho jugador en la alineación
 */
data class LineaAlineacionEntity(
    val id: String,
    val personalId: Long,
    val alineacionId: Long,
    val posicion: Int,
)
