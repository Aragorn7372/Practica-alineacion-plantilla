package org.example.newteamultimateedition.alineacion.storage

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Persona
import java.io.File

class AlineacionStorageImpl(
    private val storageHTML: AlineacionStorageHTML
): AlineacionStorage<Persona,PersonasError,Alineacion> {
    override fun fileWrite(alineacion: Alineacion,equipo: List<Persona>, file: File): Result<Unit, PersonasError> {
        when {
            file.name.endsWith(".html") -> {
                storageHTML.fileWrite(alineacion,equipo,file)
            } else -> {
            return Err(PersonasError.PersonasStorageError("Extensión inválida."))
            }
        }
        return Ok(Unit)
    }
}
