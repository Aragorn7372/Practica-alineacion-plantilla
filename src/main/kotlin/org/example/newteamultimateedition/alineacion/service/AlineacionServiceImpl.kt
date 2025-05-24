package org.example.newteamultimateedition.alineacion.service

import com.github.benmanes.caffeine.cache.Cache
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.newteamultimateedition.alineacion.error.AlineacionError
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.repository.AlineacionRepositoryImpl
import org.example.newteamultimateedition.alineacion.validador.AlineacionValidate
import org.example.newteamultimateedition.personal.repository.PersonalRepository
import java.time.LocalDate
/*
class AlineacionServiceImpl(
    private val validator: AlineacionValidate,
    private val cache: Cache<Long, Alineacion>,
    private val repository: AlineacionRepositoryImpl,
    private val personalRepository: PersonalRepository
): AlineacionService {
    override fun getByFecha(fecha: LocalDate): Result<Alineacion, AlineacionError> {
        return try {
            repository.getByDate(fecha)?.let {
                cache.getIfPresent(it.id)?:run { cache.put(it.id, it) }
                Ok(it)
            }?:run { Err(AlineacionError.AlineacionNotFoundError(fecha.toString())) }
        }catch (e: Exception) {
            Err(AlineacionError.AlineacionDatabaseError(e.message.toString()))
        }
    }
    override fun getAll(): Result<List<Alineacion>, AlineacionError> {
        return try {
            Ok(repository.getAll())
        }catch (e:Exception){
            Err(AlineacionError.AlineacionDatabaseError(e.message.toString()))
        }
    }

    override fun getByID(id: Long): Result<Alineacion, AlineacionError> {
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
        val validado=validator.validator(item)
        if (validado.isOk){
            try {
                repository.update(item,id)?.let { it ->
                    cache.getIfPresent(id)?.let {
                        cache.invalidate(id)
                        cache.put(id,it)
                    }
                    return Ok(it)
                }?: return Err(AlineacionError.AlineacionNotFoundError(id.toString()))
            }catch (e:Exception){
                return Err(AlineacionError.AlineacionDatabaseError(e.message.toString()))
            }
        }
        return Err(validado.error)
    }
}*/