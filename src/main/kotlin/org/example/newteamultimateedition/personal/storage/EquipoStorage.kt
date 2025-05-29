package org.example.newteamultimateedition.personal.storage

import com.github.michaelbull.result.Result
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Persona

import java.io.File

/**
 * Interfaz que representa el contrato para crear un almacenamiento
 */
interface EquipoStorage {
    fun fileRead(file: File): Result<List<Persona>, PersonasError>
    fun fileWrite(equipo: List<Persona>, file: File): Result<Unit, PersonasError>
}