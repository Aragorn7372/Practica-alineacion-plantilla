package org.example.newteamultimateedition.alineacion.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.common.error.Errors
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Persona
import org.lighthousegames.logging.logging
import java.io.File

class AlineacionStorageImpl(
    private val storageHTML: AlineacionStorageHTML,
    private val storagePDF: AlineacionStoragePDF
): AlineacionStorage<Persona,Errors,Alineacion> {
    private val logger = logging()
    override fun fileWrite(alineacion: Alineacion,equipo: List<Persona>, file: File): Result<Unit, Errors> {
        logger.debug { "Exportando equipo" }
        when {
            file.name.endsWith(".html") -> {
                return storageHTML.fileWrite(alineacion,equipo,file)
            }
            file.name.endsWith(".pdf") -> {
                return storagePDF.fileWrite(alineacion,equipo,file)
            }
            else -> {
            return Err(PersonasError.PersonasStorageError("Extensión inválida."))
            }
        }
        return Ok(Unit)
    }
}
