package org.example.newteamultimateedition.alineacion.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.newteamultimateedition.alineacion.error.AlineacionError
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.model.LineaAlineacion
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Persona
import org.lighthousegames.logging.logging
import java.io.File

/**
 * Clase que representa el storage de HTML de [Alineacion]
 */
class AlineacionStorageHTML : AlineacionStorage<Persona,AlineacionError,Alineacion> {
    private val logger = logging()

    /**
     * Función que escribe en un archivo HTMl toda la información relativa a una alineación.
     * @param alineacion La alineación en cuestión
     * @param lista Lista de jugadores de la alineación
     * @param file El archivo en el que se escribirá toda la información
     * @return [Result] de [Unit] en caso correcto o [AlineacionError] en caso incorrecto o de fallo
     */
    override fun fileWrite(alineacion: Alineacion,lista: List<Persona>, file: File): Result<Unit, AlineacionError> {
        logger.debug { "Escribiendo equipo en formato HTML" }

        if (!file.isFile || !file.parentFile.exists()) {
            return Err(AlineacionError.AlineacionStorageError("El directorio padre del fichero no existe"))
        }


        val html = buildString {
            appendLine("<!DOCTYPE html>")
            appendLine("<html><head><meta charset=\"UTF-8\"/><title>Equipo</title></head><body>")
            appendLine("<h1>Alineacion del equipo</h1>")

            appendLine("<h2>Información</h2>")
            appendLine("<p>Id: ${alineacion.id}</p>")
            appendLine("<p>Entrenador: ${alineacion.entrenador}</p>")
            appendLine("<p>Fecha de creación: ${alineacion.createdAt}</p>")
            appendLine("<p>Última actualización: ${alineacion.updatedAt}</p>")
            appendLine("<p>Fecha de convocatoria: ${alineacion.juegoDate} </p>")
            appendLine("<p>Descripción: ${alineacion.descripcion}</p>")
            appendLine("<h2>Jugadores</h2>")
            appendLine("<ul>")
            lista.forEach { it as Jugador

                appendLine("<li>")
                appendLine("Nombre completo: ${it.nombre} ${it.apellidos} - Posición: ${it.posicion} - Dorsal: ${it.dorsal}")
                appendLine("</li>")
            }
            appendLine("</ul>")
            appendLine("</body></html>")
        }
        file.writeText(html)
        return Ok(Unit)
    }

    /**
     * crea un html
     * @param alineacion La alineación en cuestión
     * @param lista Lista de jugadores de la alineación
     * @param file El archivo en el que se escribirá toda la información
     * @return [String] html creado
     */
    fun createHtml(alineacion: Alineacion,lista: List<Persona>, ): String {
        logger.debug { "Escribiendo equipo en formato HTML" }

        val html = buildString {
            appendLine("<!DOCTYPE html>")
            appendLine("<html><head><meta charset=\"UTF-8\"/><title>Equipo</title></head><body>")
            appendLine("<h1>Alineacion del equipo</h1>")

            appendLine("<h2>Información</h2>")
            appendLine("<p>Id: ${alineacion.id}</p>")
            appendLine("<p>Entrenador: ${alineacion.entrenador}</p>")
            appendLine("<p>Fecha de creación: ${alineacion.createdAt}</p>")
            appendLine("<p>Última actualización: ${alineacion.updatedAt}</p>")
            appendLine("<p>Fecha de convocatoria: ${alineacion.juegoDate} </p>")
            appendLine("<p>Descripción: ${alineacion.descripcion}</p>")
            appendLine("<h2>Jugadores</h2>")
            appendLine("<ul>")
            lista.forEach { it as Jugador

                appendLine("<li>")
                appendLine("Nombre completo: ${it.nombre} ${it.apellidos} - Posición: ${it.posicion} - Dorsal: ${it.dorsal}")
                appendLine("</li>")
            }
            appendLine("</ul>")
            appendLine("</body></html>")
        }
        return html
    }
}
