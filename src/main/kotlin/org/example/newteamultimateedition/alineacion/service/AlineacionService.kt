package org.example.newteamultimateedition.alineacion.service

import com.github.michaelbull.result.Result
import org.example.newteamultimateedition.alineacion.error.AlineacionError
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.model.LineaAlineacion
import org.example.newteamultimateedition.common.error.Errors
import org.example.newteamultimateedition.common.service.Service
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Persona
import java.time.LocalDate

interface AlineacionService: Service<Alineacion,AlineacionError,Long> {
    fun getByFecha(fecha: LocalDate):Result<Alineacion,AlineacionError>
    fun getAllPersonas(): Result<List<Persona>, PersonasError>
    fun getJugadoresByLista(lista: List<LineaAlineacion>):Result<List<Persona>, PersonasError>
    fun getJugadoresByAlinecionId(id:Long):Result<List<Persona>, Errors>
}