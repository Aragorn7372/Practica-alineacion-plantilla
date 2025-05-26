package org.example.newteamultimateedition.alineacion.service

import com.github.benmanes.caffeine.cache.Cache
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onSuccess
import org.example.newteamultimateedition.alineacion.error.AlineacionError
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.model.LineaAlineacion
import org.example.newteamultimateedition.alineacion.repository.AlineacionRepositoryImpl
import org.example.newteamultimateedition.alineacion.validador.AlineacionValidate
import org.example.newteamultimateedition.common.error.Errors
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Persona
import org.example.newteamultimateedition.personal.services.PersonaServiceImpl
import org.lighthousegames.logging.logging
import java.time.LocalDate

class AlineacionServiceImpl(
    private val validator: AlineacionValidate,
    private val cache: Cache<Long, Alineacion>,
    private val repository: AlineacionRepositoryImpl,
    private val personalService: PersonaServiceImpl
): AlineacionService {
    private val logger= logging()
    override fun getByFecha(fecha: LocalDate): Result<Alineacion, AlineacionError> {
        logger.debug { "getByFecha $fecha" }
        return try {
            repository.getByDate(fecha)?.let {
                cache.getIfPresent(it.id)?:run { cache.put(it.id, it) }
                Ok(it)
            }?:run { Err(AlineacionError.AlineacionNotFoundError(fecha.toString())) }
        }catch (e: Exception) {
            Err(AlineacionError.AlineacionDatabaseError(e.message.toString()))
        }
    }

    override fun getJugadores(): Result<List<Persona>, PersonasError> {
        logger.debug { "getJugadores" }
        return try {
            personalService.getAll()
        }catch (e: Exception) {
            Err(PersonasError.PersonaDatabaseError(e.message.toString()))
        }
    }

    override fun getJugadoresByLista(lista: List<LineaAlineacion>): Result<List<Persona>, PersonasError> {
        logger.debug { "getJugadoresByLista" }
        try {
            val list= lista.map { personalService.getByID(it.idPersona) }
            return if(list.count { it.isOk } == lista.size){
                Ok(list.map { it.value })
            }else {
                Err(PersonasError.PersonaDatabaseError(list.filter { it.isErr }.map {
                    it.error.message
                }.joinToString(",")))
            }
        }catch (e: Exception) {
            return Err(PersonasError.PersonaDatabaseError(e.message.toString()))
        }
    }

    override fun getJugadoresByAlinecionId(id: Long): Result<List<Persona>, Errors> {
        logger.debug { "getJugadoresByAlinecionId" }
        try{
            val alineacion= getByID(id).onSuccess { alineacion ->
                val lista=alineacion.personalList.map { personalService.getByID(it.idPersona) }
                return if (lista.count { it.isOk } == lista.size){
                    Ok(lista.map { it.value })
                }else {
                        Err(PersonasError.PersonaDatabaseError(lista.filter { it.isErr }.map {
                        it.error.message
                    }.joinToString(",")))
                }
            }
            return Err(alineacion.error)
        }catch (e:Exception){
            return Err(PersonasError.PersonaDatabaseError(e.message.toString()))
        }
    }

    override fun getAll(): Result<List<Alineacion>, AlineacionError> {
        logger.debug { "getAll" }
        return try {
            Ok(repository.getAll())
        }catch (e:Exception){
            Err(AlineacionError.AlineacionDatabaseError(e.message.toString()))
        }
    }

    override fun getByID(id: Long): Result<Alineacion, AlineacionError> {
        logger.debug { "getByID" }
        return cache.getIfPresent(id)?.let { Ok(it) }?:run {
            try {
                repository.getById(id)?.let {
                    cache.put(id,it)
                    return Ok(it)
                }?:run {
                    return Err(AlineacionError.AlineacionNotFoundError(id.toString()))
                }
            }catch (e:Exception){
                return Err(AlineacionError.AlineacionDatabaseError(e.message.toString()))
            }

        }
    }

    override fun save(item: Alineacion): Result<Alineacion, AlineacionError> {
        logger.debug { "save" }
        val validado=validator.validator(item)
        return if (validado.isOk){
            try {
                return Ok(repository.save(item))
            }catch (e:Exception){
                return Err(AlineacionError.AlineacionDatabaseError(e.message.toString()))
            }

        } else Err(validado.error)
    }

    override fun delete(id: Long): Result<Alineacion, AlineacionError> {
        logger.debug { "delete" }
        return try {
            repository.delete(id)?.let {
                cache.getIfPresent(id)?.let { cache.invalidate(id) }
                return Ok(it)
            } ?: Err(AlineacionError.AlineacionNotFoundError(id.toString()))
        }catch (e:Exception){
            return Err(AlineacionError.AlineacionDatabaseError(e.message.toString()))
        }
    }

    override fun update(id: Long, item: Alineacion): Result<Alineacion, AlineacionError> {
        logger.debug { "update" }
        val validado=validator.validator(item)
        if (validado.isOk){
            try {
                repository.update(item,id)?.let { alineacion ->
                    cache.getIfPresent(id)?.let {
                        cache.invalidate(id)
                        cache.put(id,it)
                    }
                    return Ok(alineacion)
                }?: return Err(AlineacionError.AlineacionNotFoundError(id.toString()))
            }catch (e:Exception){
                return Err(AlineacionError.AlineacionDatabaseError(e.message.toString()))
            }
        }
        return Err(validado.error)
    }
}