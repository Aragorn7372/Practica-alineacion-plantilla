package org.example.newteamultimateedition.alineacion.service

import com.github.michaelbull.result.Result
import org.example.newteamultimateedition.alineacion.error.AlineacionError
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.common.service.Service
import java.time.LocalDate

interface AlineacionService: Service<Alineacion,AlineacionError,Long> {
    fun getByFecha(fecha: LocalDate):Result<Alineacion,AlineacionError>
}