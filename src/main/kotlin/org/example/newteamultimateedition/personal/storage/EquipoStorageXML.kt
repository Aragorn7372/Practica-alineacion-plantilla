package org.example.newteamultimateedition.personal.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import nl.adaptivity.xmlutil.serialization.XML
import org.example.newteamultimateedition.personal.dto.EquipoDTO
import org.example.newteamultimateedition.personal.dto.IntegranteXmlDTO
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.mapper.toModel
import org.example.newteamultimateedition.personal.mapper.toXmlDTO
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Persona
import org.lighthousegames.logging.logging
import java.io.File

class EquipoStorageXML: EquipoStorage {
    private var logger = logging()

    /**
     * Permite leer de un archivo una lista de [Persona]
     * Lee el archivo como una lista de DTO de integrante y lo mapea al modelo segun va leyendo
     *  @return [Result] de [List] [Persona] o [PersonasError.PersonasStorageError]
     */
    override fun fileRead(file: File): Result<List<Persona>, PersonasError> {
        logger.debug { "Leyendo fichero XML" }

        if (!file.exists() || !file.isFile || !file.canRead()) return Err(PersonasError.PersonasStorageError("El fichero no existe, la ruta especificada no es un fichero o no se tienen permisos de lectura"))

        val xml = XML {}

        val xmlString = file.readText() // Leemos cada línea del fichero
        val listaEquipoDTO = xml.decodeFromString <EquipoDTO>(xmlString) // Convertimos el texto anteriormente leido a la clase EquipoDTO, que contiene la lista en la que se almacena cada IntegranteDTO
        val listaIntegrantesDTO = listaEquipoDTO.equipo // De la clase listaEquipoDTO, nos quedamos solo con la lista que contiene los IntegrantesDTO
        val listaIntegrantes = listaIntegrantesDTO.map { it.toModel() } // Mapeamos la lista de DTO para convertir cada elemento a un modelo

        return  Ok(listaIntegrantes)
    }

    /**
     * Escribe en un fichero dada una lista de [Persona] y una ruta especificada
     * @param equipo La lista de integrantes
     * @param file El archivo donde se escribira la lista
     * @return [Result] de [List] [Persona] o [PersonasError.PersonasStorageError]
     */
    override fun fileWrite(equipo: List<Persona>, file: File): Result<Unit, PersonasError> {
        logger.debug { "Escribiendo en fichero XML" }

        if (!file.parentFile.exists() || !file.parentFile.isDirectory) {
            return Err(PersonasError.PersonasStorageError("El directorio padre del fichero no existe"))
        }

        val xml = XML {indent = 4}

        val listaIntegrantesDTO: List<IntegranteXmlDTO> = equipo.mapNotNull {
            when (it) {
                is Jugador -> {it.toXmlDTO()}
                is Entrenador -> {it.toXmlDTO()}
                else -> null
            }
        } //Pasamos la lista de Integrantes (modelo) a IntegranteXmlDTO

        val equipoDTO = EquipoDTO(listaIntegrantesDTO) //Instanciamos la clase EquipoDTO, que es la que contiene la lista con los IntegranteXmlDTO

        val xmlString = xml.encodeToString<EquipoDTO>(equipoDTO) //La convertimos en un String

        file.writeText(xmlString) //Escribimos el String en el fichero .xml

        return Ok(Unit)
    }
}