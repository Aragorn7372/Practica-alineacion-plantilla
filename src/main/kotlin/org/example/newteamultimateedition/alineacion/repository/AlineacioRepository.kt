package org.example.newteamultimateedition.alineacion.repository

import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.common.repository.Repository
import java.time.LocalDate

/**
 * Interfaz que incluye la operación de obtener una alineación por fecha.
 */
interface AlineacioRepository:Repository<Alineacion,Long> {
    fun getByDate(date: LocalDate): Alineacion?
}