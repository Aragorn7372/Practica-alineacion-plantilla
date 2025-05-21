package org.example.newteamultimateedition.personal.services

import com.github.michaelbull.result.Result
import org.example.newteamultimateedition.common.service.Service
import org.example.newteamultimateedition.personal.exception.PersonasException
import org.example.newteamultimateedition.personal.models.Persona
import org.example.newteamultimateedition.personal.storage.Tipo
import java.nio.file.Path

interface PersonaService: Service<Persona, PersonasException, Long> {
    fun importarDatosDesdeFichero(fichero: Path): Result<List<Persona>, PersonasException>
    fun exportarDatosDesdeFichero(fichero: Path, tipo: Tipo): Result<String, PersonasException>
}