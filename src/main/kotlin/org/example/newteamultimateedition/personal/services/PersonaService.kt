package org.example.newteamultimateedition.personal.services

import com.github.michaelbull.result.Result
import org.example.newteamultimateedition.common.service.Service
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Persona
import java.nio.file.Path

/**
 * Interfaz que realiza el contrato de operaciones para las clases que la implementen
 */
interface PersonaService: Service<Persona, PersonasError, Long> {
    fun importarDatosDesdeFichero(fichero: Path): Result<List<Persona>, PersonasError>
    fun exportarDatosDesdeFichero(fichero: Path): Result<Unit, PersonasError>
}