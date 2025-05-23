package org.example.newteamultimateedition.personal.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
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
 * Almacenamiento que Implementa la interfaz [EquipoStorage] para manejar el trato con archivos CSV para poder leer de un archivo un tipo de objeto o al reves
 */
class EquipoStorageCSV: EquipoStorage {
    private var logger = logging()

    /**
     * Permite leer de un archivo una lista de [Persona]
     * Lee el archivo como una lista de DTO de integrante y lo mapea al modelo segun va leyendo
     * @return [Result] de [List] [Persona] o [PersonasError.PersonasStorageError]
     */
    override fun fileRead(file: File): Result<List<Persona>, PersonasError> {
        logger.debug { "Leyendo fichero CSV" }

        if (!file.exists() || !file.isFile || !file.canRead()) {
            return Err(PersonasError.PersonasStorageError("El fichero no existe, la ruta especificada no es un fichero o no se tienen permisos de lectura"))
        }

        return Ok( file.readLines()
            .drop(1)
            .map{it.split(",")}
            .map{
                IntegranteDTO(
                    id = it[0].toLong(),
                    nombre = it[1],
                    apellidos = it[2],
                    fechaNacimiento = it[3],
                    fechaIncorporacion = it[4],
                    salario = it[5].toDouble(),
                    pais = it[6],
                    rol = it[7],
                    especialidad = it[8],
                    posicion = it[9],
                    dorsal = it[10].toIntOrNull(),
                    altura = it[11].toDoubleOrNull(),
                    peso = it[12].toDoubleOrNull(),
                    goles = it[13].toIntOrNull(),
                    partidosJugados = it[14].toIntOrNull(),
                    minutosJugados = it[15].toIntOrNull(),
                    imagen = it[16],
                ).toModel()
            } ) // Fin OK
    }
    /**
     * Escribe en un fichero dada una lista de [Persona] y una ruta especificada
     * @param equipo La lista de integrantes
     * @param file El archivo donde se escribira la lista
     * @return [Result] de [Unit] o [PersonasError.PersonasStorageError]
     */
    override fun fileWrite(equipo: List<Persona>, file: File): Result<Unit, PersonasError> {
        logger.debug { "Escribiendo integrantes del equipo en fichero CSV" }

        if (!file.parentFile.exists() || !file.parentFile.isDirectory) {
            return Err(PersonasError.PersonasStorageError("El directorio padre del fichero no existe"))
        }

        file.writeText("id,nombre,apellidos,fecha_nacimiento,fecha_incorporacion,salario,pais,rol,especialidad,posicion,dorsal,altura,peso,goles,partidos_jugados,minutos_jugados,imagen\n")

        equipo.map {
            if (it is Jugador) {
                it.toDto()
                file.appendText("${it.id},${it.nombre},${it.apellidos},${it.fechaNacimiento},${it.fechaIncorporacion},${it.salario},${it.pais},Jugador,,${it.posicion},${it.dorsal},${it.altura},${it.peso},${it.goles},${it.partidosJugados},${it.minutosJugados},${it.imagen}\n")
            }
            if (it is Entrenador) {
                it.toDto()
                file.appendText("${it.id},${it.nombre},${it.apellidos},${it.fechaNacimiento},${it.fechaIncorporacion},${it.salario},${it.pais},Entrenador,${it.especialidad},,,,,,,,${it.imagen}\n")
            }
        }
        return Ok(Unit)
    }
}