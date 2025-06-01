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

/**
 * Clase que representa la fachada del storage de alineación en HTML
 * @property storageHTML [AlineacionStorageHTML] storage de alineaciones en HTML
 */
class AlineacionStorageImpl(
    private val storageHTML: AlineacionStorageHTML,
    private val storagePDF: AlineacionStoragePDF
): AlineacionStorage<Persona,Errors,Alineacion> {
    private val logger = logging()

    /**
     * Funcion que recibe una alineación y una lista de miembros y los escribe en un archivo. Finalmente en función de su extensión se llama a un storage u a otro y si no se reconoce la extensión devuelve un error
     * @return [Result] de [Unit] en caso correcto o de [Errors] en caso incorrecto
     */
    override fun fileWrite(alineacion: Alineacion,equipo: List<Persona>, file: File): Result<Unit, Errors> {
        logger.debug { "Exportando equipo" }
        return when {
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

    }
}
