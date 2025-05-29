package org.example.newteamultimateedition.alineacion.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Persona
import org.lighthousegames.logging.logging
import java.io.File
/*
class AlineacionStorageHTML : AlineacionStorage {
    private val logger = logging()

    override fun fileWrite(equipo: List<Persona>, file: File): Result<Unit, PersonasError> {
        logger.debug { "Escribiendo equipo en formato HTML" }

        if (!file.parentFile.exists() || !file.parentFile.isDirectory) {
            return Err(PersonasError.PersonasStorageError("El directorio padre del fichero no existe"))
        }

        val html = buildString {
            appendLine("<!DOCTYPE html>")
            appendLine("<html><head><meta charset=\"UTF-8\"><title>Equipo</title></head><body>")
            appendLine("<h1>Listado del equipo</h1>")
            appendLine("<ul>")

            equipo.forEach {
                appendLine("<li>")
                when (it) {
                    is Jugador -> appendLine("Jugador: ${it.nombre} ${it.apellidos} - Posición: ${it.posicion} - Dorsal: ${it.dorsal}")
                    is Entrenador -> appendLine("Entrenador: ${it.nombre} ${it.apellidos} - Especialidad: ${it.especialidad}")
                }
                appendLine("</li>")
            }
            appendLine("</ul>")
            appendLine("</body></html>")
        }
        file.writeText(html)
        return Ok(Unit)
    }
}
*/