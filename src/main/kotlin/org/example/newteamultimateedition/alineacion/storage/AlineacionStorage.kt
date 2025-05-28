package org.example.newteamultimateedition.alineacion.storage

import com.github.michaelbull.result.Result
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Persona
import java.io.File

interface AlineacionStorage {
    fun fileWrite(equipo: List<Persona>, file: File): Result<Unit, PersonasError>
}