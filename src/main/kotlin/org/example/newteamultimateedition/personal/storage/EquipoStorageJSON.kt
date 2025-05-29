package org.example.newteamultimateedition.personal.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import org.example.newteamultimateedition.personal.dto.IntegranteDTO
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.mapper.toDto
import org.example.newteamultimateedition.personal.mapper.toModel
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Persona
import org.lighthousegames.logging.logging
import java.io.File
/**
 * Almacenamiento que Implementa la interfaz [EquipoStorage] para
 * manejar el trato con archivos JSON para poder leer de un archivo
 * un tipo de objeto o al reves
 */
class EquipoStorageJSON: EquipoStorage {
    private var logger = logging()

    /**
     * Permite leer de un archivo una lista de [Persona]
     * Lee el archivo como una lista de DTO de integrante y lo mapea al modelo segun va leyendo
     *  @return [Result] de [List] [Persona] o [PersonasError.PersonasStorageError]
     */
    override fun fileRead(file: File): Result<List<Persona>, PersonasError> {
        logger.debug { "Leyendo fichero JSON" }

        if (!file.exists() || !file.isFile || !file.canRead()) return Err(PersonasError.PersonasStorageError("El fichero no existe, la ruta especificada no es un fichero o no se tienen permisos de lectura"))

        val json = Json { ignoreUnknownKeys = true }

        val jsonString = file.readText() // Leemos cada linea del fichero
        val listaIntegrantesDTO: List<IntegranteDTO> = json.decodeFromString(jsonString) // Convertimos el texto anteriormente leido a una lsita de IntegrantesDTO
        val listaIntegrantes = listaIntegrantesDTO.map { it.toModel() } // Mapeamos la lista de DTO para convertir cada elemento a un modelo segun convenga

        return  Ok(listaIntegrantes)
    }

    /**
     * Permite leer de un archivo una lista de [Persona]
     * Lee el archivo como una lista de DTO de integrante y lo mapea al modelo segun va leyendo
     * @return [Result] de [List] [Persona] o [PersonasError.PersonasStorageError]
     */
    override fun fileWrite(equipo: List<Persona>, file: File): Result<Unit, PersonasError> {
        logger.debug { "Escribiendo integrantes del equipo en fichero JSON" }

        if (!file.parentFile.exists() || !file.parentFile.isDirectory) {
            return Err(PersonasError.PersonasStorageError("El directorio padre del fichero no existe"))
        }

        val json = Json { ignoreUnknownKeys = true ; prettyPrint = true }

        val listaIntegrantesDTO: List<IntegranteDTO> = equipo.mapNotNull {
            when (it) {
                is Jugador -> {it.toDto()}
                is Entrenador -> {it.toDto()}
                else -> null
            }
        }  // Convertir integrantes a DTOs segun el modelo
        val jsonString: String = json.encodeToString(ListSerializer(IntegranteDTO.serializer()), listaIntegrantesDTO)  // Serializar a JSON
        file.writeText(jsonString)  // Guardar en el archivo
        return Ok(Unit)
    }
}