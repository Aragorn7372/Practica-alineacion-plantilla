package org.example.newteamultimateedition.personal.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
//import org.example.newteamultimateedition.alineacion.storage.AlineacionStorageHTML
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Persona
import java.io.File

/**
 * Clase que unifica los tipos de storage
 * @property storageCSV [EquipoStorageCSV] El almacenamiento CSV
 * @property storageJSON [EquipoStorageJSON] El almacenamiento JSON
 * @property storageXML [EquipoStorageXML] El almacenamiento XML
 * @property storageBIN [EquipoStorageBIN] El almacenamiento BIN
 */
class EquipoStorageImpl(
    private val storageCSV: EquipoStorageCSV ,
    private val storageXML: EquipoStorageXML ,
    private val storageJSON: EquipoStorageJSON ,
    private val storageBIN: EquipoStorageBIN ,
    private val storageZip: PersonalStorageZip
): EquipoStorage {
    /**
     * Llama a uno u otro storage en función de la extensión del archivo a leer que le entra por parámetro
     * @param file [File] archivo a leer
     * @return [Result] de [List] [Persona] o [PersonasError.PersonasStorageError]
     * @see [EquipoStorageCSV]
     * @see [EquipoStorageJSON]
     * @see [EquipoStorageXML]
     * @see [EquipoStorageBIN]
     */
    override fun fileRead(file: File): Result<List<Persona>, PersonasError> {
        when {
            file.name.endsWith(".csv") -> {
                return storageCSV.fileRead(file)
            }
            file.name.endsWith(".json") -> {
                return storageJSON.fileRead(file)
            }
            file.name.endsWith(".xml") -> {
                return storageXML.fileRead(file)
            }
            file.name.endsWith(".zip") -> {
                return storageZip.leerDelArchivo(file)
            }
            else -> {
                return storageBIN.fileRead(file)
            }
        }
    }

    /**
     * Llama a uno u otro storage en función de la extensión del archivo a escribir que le entra por parámetro
     * @param equipo Lista de integrantes a escribir
     * @param file [File] Archivo a escribir
     * @return [Result] de [List] [Persona] o [PersonasError.PersonasStorageError]
     * @see [EquipoStorageCSV]
     * @see [EquipoStorageJSON]
     * @see [EquipoStorageXML]
     * @see [EquipoStorageBIN]
     */
    override fun fileWrite(equipo: List<Persona>, file: File): Result<Unit, PersonasError> {
        return when {
            file.name.endsWith(".csv") -> {
                storageCSV.fileWrite(equipo,file)
            }
            file.name.endsWith(".json") -> {
                storageJSON.fileWrite(equipo,file)
            }
            file.name.endsWith(".xml") -> {
                storageXML.fileWrite(equipo,file)
            }
            file.name.endsWith(".zip") -> {
                storageZip.escribirAUnArchivo(file,equipo)
            }
            file.name.endsWith(".bin") -> {
                storageBIN.fileWrite(equipo,file)
            } else -> {
                return Err(PersonasError.PersonasStorageError("Extensión inválida."))
            }
        }
        return Ok(Unit)
    }
}