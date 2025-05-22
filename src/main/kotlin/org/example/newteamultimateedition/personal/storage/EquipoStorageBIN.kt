package org.example.newteamultimateedition.personal.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.newteamultimateedition.personal.dto.IntegranteDTO
import org.example.newteamultimateedition.personal.mapper.toDto
import org.example.newteamultimateedition.personal.mapper.toModel
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Persona
import org.lighthousegames.logging.logging
import java.io.File
import java.io.RandomAccessFile

/**
 * Almacenamiento que Implementa la interfaz [EquipoStorage] para
 * manejar el trato con archivos binarios para poder leer de un archivo
 * un tipo de objeto o al reves
 */
class EquipoStorageBIN: EquipoStorage {
    private var logger = logging()

    /**
     * Permite leer de un archivo una lista de [Integrante] usa la codificacion UTF-8
     * Lee el archivo como una lista de DTO de integrante y lo mapea al modelo segun va leyendo
     * @return [Result] de [List] [Integrante] o [GestionErrors.StorageError]
     */
    override fun fileRead(file: File): Result<List<Persona>, PersonasError> {
        logger.debug{"Leyendo archivo BIN"}

        if (!file.exists() || !file.isFile || !file.canRead()) return Err(PersonasError.PersonasStorageError("El fichero no existe, la ruta especificada no es un fichero o no se tienen permisos de lectura"))

        val equipo = mutableListOf<IntegranteDTO>() // Mutable para poder añadir sobre la marcha los objetos

        RandomAccessFile(file, "r").use { raf ->
            while(raf.filePointer < raf.length()) {

                val id = raf.readLong()
                val nombre = raf.readUTF()
                val apellidos = raf.readUTF()
                val fechaNacimiento = raf.readUTF()
                val fechaIncorporacion = raf.readUTF()
                val salario = raf.readDouble()
                val pais = raf.readUTF()
                val rol = raf.readUTF()
                if(rol == "Jugador"){
                    val posicion = raf.readUTF()
                    val dorsal = raf.readInt()
                    val altura = raf.readDouble()
                    val peso = raf.readDouble()
                    val goles = raf.readInt()
                    val partidosJugados = raf.readInt()
                    val minutosJugados = raf.readInt()
                    val imagen = raf.readUTF()

                    val integranteJugador = IntegranteDTO(
                        id, nombre, apellidos, fechaNacimiento,
                        fechaIncorporacion, salario, pais, rol,
                        especialidad = null, posicion, dorsal,
                        altura, peso, goles, partidosJugados,
                        minutosJugados, imagen
                    )
                    equipo.add(integranteJugador)
                }
                else{
                    val especialidad = raf.readUTF()
                    val imagen = raf.readUTF()

                    val integranteEntrenador = IntegranteDTO(
                        id, nombre, apellidos, fechaNacimiento,
                        fechaIncorporacion, salario, pais, rol,
                        especialidad, posicion = null, dorsal = null,
                        altura = null, peso = null, goles = null,
                        partidosJugados = null, minutosJugados = null, imagen
                    )
                    equipo.add(integranteEntrenador)
                }
            }
        }
        return Ok(equipo.map{ it.toModel() })
    }

    /**
     * Escribe en un fichero dada una lista de [Integrante] y una ruta especificada
     * @param equipo La lista de integrantes
     * @param file El archivo donde se escribira la lista
     * @return [Result] de [Unit] o [GestionErrors.StorageError]
     */
    override fun fileWrite(equipo: List<Persona>, file: File): Result<Unit, PersonasError> {
        logger.debug { "Escribiendo archivo BIN" }

        if(!file.parentFile.exists() || !file.parentFile.isDirectory){
            return Err(PersonasError.PersonasStorageError("El directorio padre del fichero no existe"))
        }

        val integrantesDTO = equipo.map {
            when (it) {
                is Jugador -> {
                    it.toDto()
                }
                is Entrenador -> {
                    it.toDto()
                }
                else -> null
            }
        }
        RandomAccessFile(file, "rw").use { raf -> // usamos .use para abrirlo y cerrarlo e forma segura
            raf.setLength(0) // Borramos el contenido del archivo porque cada vez que queramos escribir en este es mejor para no ir agrandando la lista de integrantes con informacion no real.
            for(integrante in integrantesDTO) {
                // Forzamos todas las llamadas con nulls porque o bien sabemos que no peude haber null en ese campo, o bien nos interesa procesarlo para los otros formatos de archivo
                if (integrante?.rol == "Jugador"){
                    raf.writeLong(integrante.id)
                    raf.writeUTF(integrante.nombre)
                    raf.writeUTF(integrante.apellidos)
                    raf.writeUTF(integrante.fechaNacimiento)
                    raf.writeUTF(integrante.fechaIncorporacion)
                    raf.writeDouble(integrante.salario)
                    raf.writeUTF(integrante.pais)
                    raf.writeUTF(integrante.rol)
                    raf.writeUTF(integrante.posicion)
                    raf.writeInt(integrante.dorsal!!)
                    raf.writeDouble(integrante.altura!!)
                    raf.writeDouble(integrante.peso!!)
                    raf.writeInt(integrante.goles!!)
                    raf.writeInt(integrante.partidosJugados!!)
                    raf.writeInt(integrante.minutosJugados!!)
                    raf.writeUTF(integrante.imagen)
                }
                else{
                    raf.writeLong(integrante!!.id)
                    raf.writeUTF(integrante.nombre)
                    raf.writeUTF(integrante.apellidos)
                    raf.writeUTF(integrante.fechaNacimiento)
                    raf.writeUTF(integrante.fechaIncorporacion)
                    raf.writeDouble(integrante.salario)
                    raf.writeUTF(integrante.pais)
                    raf.writeUTF(integrante.rol)
                    raf.writeUTF(integrante.especialidad)
                    raf.writeUTF(integrante.imagen)
                }
            }
        }
        return Ok(Unit)
    }
}