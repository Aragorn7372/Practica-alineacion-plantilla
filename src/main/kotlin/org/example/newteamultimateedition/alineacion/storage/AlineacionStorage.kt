package org.example.newteamultimateedition.alineacion.storage

import com.github.michaelbull.result.Result
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Persona
import java.io.File

/**
 * Interfaz que representa operación de escribir en un fichero
 */
interface AlineacionStorage <T,E,U> {
    fun fileWrite(item:U,equipo: List<T>, file: File): Result<Unit, E>
}