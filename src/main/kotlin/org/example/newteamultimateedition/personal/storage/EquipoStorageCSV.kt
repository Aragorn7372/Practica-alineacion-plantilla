package org.example.newteamultimateedition.personal.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.newteam.gestion.errors.GestionErrors
import org.example.newteamultimateedition.personal.mapper.toDto
import org.example.newteamultimateedition.personal.mapper.toModel
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteam.gestion.models.Integrante
import org.example.newteamultimateedition.personal.models.Jugador
import org.lighthousegames.logging.logging
import java.io.File

/**
 * Almacenamiento que Implementa la interfaz [EquipoStorage] para manejar el trato con archivos CSV para poder leer de un archivo un tipo de objeto o al reves
 */
class EquipoStorageCSV: EquipoStorage {
    private var logger = logging()

    /**
     * Permite leer de un archivo una lista de [Integrante]
     * Lee el archivo como una lista de DTO de integrante y lo mapea al modelo segun va leyendo
     * @return [Result] de [List] [Integrante] o [GestionErrors.StorageError]
     */
    override fun fileRead(file: File): Result<List<Integrante>, GestionErrors> {
        logger.debug { "Leyendo fichero CSV" }

        if (!file.exists() || !file.isFile || !file.canRead()) return Err(GestionErrors.StorageError("El fichero no existe, la ruta especificada no es un fichero o no se tienen permisos de lectura"))

        return Ok( file.readLines()
            .drop(1)
            .map{it.split(",")}
            .map{
                org.example.newteamultimateedition.personal.mapper.toModel()
            } ) // Fin OK
    }
    /**
     * Escribe en un fichero dada una lista de [Integrante] y una ruta especificada
     * @param equipo La lista de integrantes
     * @param file El archivo donde se escribira la lista
     * @return [Result] de [Unit] o [GestionErrors.StorageError]
     */
    override fun fileWrite(equipo: List<Integrante>, file: File): Result<Unit, GestionErrors> {
        logger.debug { "Escribiendo integrantes del equipo en fichero CSV" }

        if (!file.parentFile.exists() || !file.parentFile.isDirectory) {
            return Err(GestionErrors.StorageError("El directorio padre del fichero no existe"))
        }

        file.writeText("id,nombre,apellidos,fecha_nacimiento,fecha_incorporacion,salario,pais,rol,especialidad,posicion,dorsal,altura,peso,goles,partidos_jugados\n")

        equipo.map {
            if (it is Jugador) {
                it.toDto()
                file.appendText("${it.id},${it.nombre},${it.apellidos},${it.fecha_nacimiento},${it.fecha_incorporacion},${it.salario},${it.pais},Jugador,,${it.posicion},${it.dorsal},${it.altura},${it.peso},${it.goles},${it.partidos_jugados},${it.minutos_jugados},${it.imagen}\n")
            }
            if (it is Entrenador) {
                it.toDto()
                file.appendText("${it.id},${it.nombre},${it.apellidos},${it.fecha_nacimiento},${it.fecha_incorporacion},${it.salario},${it.pais},Entrenador,${it.especialidad},,,,,,,,${it.imagen}\n")
            }
        }
        return Ok(Unit)
    }
}