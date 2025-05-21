package org.example.newteamultimateedition.personal.services

import com.github.benmanes.caffeine.cache.Cache
import com.github.michaelbull.result.*
import org.example.newteamultimateedition.personal.exception.PersonasException
import org.example.newteamultimateedition.personal.models.Persona
import org.example.newteamultimateedition.personal.repository.PersonasRepositoryImplementation
import org.example.newteamultimateedition.personal.storage.EquipoStorage
import org.example.newteamultimateedition.personal.storage.EquipoStorageImpl
import org.example.newteamultimateedition.personal.validator.PersonaValidation
import org.lighthousegames.logging.logging
import java.nio.file.Path

class PersonaServiceImpl<Tipo>(
    private val repositorio: PersonasRepositoryImplementation,
    private val cache: Cache<Long, Persona>,
    private val validator: PersonaValidation,
    private val storage: EquipoStorageImpl
):PersonaService {
    private val logger= logging()
    override fun importarDatosDesdeFichero(fichero: Path): Result<List<Persona>, PersonasException> {
        logger.debug { "importando desde fichero" }
        val archivo= fichero.toFile()
        try {
            val lista = storage.fileRead(archivo)
            if (lista.isOk) {
                lista.value.forEach {
                    val validado = validator.validator(it)
                    if (validado.isOk) repositorio.save(it)
                }
            } else return Err(lista.error)
            return lista
        }catch (e:Exception){
            return Err(PersonasException.PersonaDatabaseException(e.message.toString()))
        }
    }

    override fun exportarDatosDesdeFichero(fichero: Path, tipo: Tipo): Result<String, PersonasException> {
        logger.debug { "exportando desde fichero" }
        val archivo= fichero.toFile()
        val lista: List<Persona>
        try {
            lista=repositorio.getAll()
        }catch (e:Exception){
            return Err(PersonasException.PersonaDatabaseException(e.message.toString()))
        }
        val result= storage.fileWrite(archivo,lista)
        if (result.isOk){
            return Ok(result.value)
        }
        return Err(result.error)
    }

    override fun getAll(): Result<List<Persona>,PersonasException> {
        try {
            return Ok(repositorio.getAll())
        }catch (e:Exception){
            return Err(PersonasException.PersonaDatabaseException(e.message.toString()))
        }
    }

    override fun getByID(id: Long): Result<Persona, PersonasException> {
        return cache.getIfPresent(id)?.let { Ok(it) }?:run {
            try {
                repositorio.getById(id)?.let {
                    cache.put(id,it)
                    return Ok(it)
                }?:run {
                    return Err(PersonasException.PersonaNotFoundException(id))
                }
            }catch (e:Exception){
                return Err(PersonasException.PersonaDatabaseException(e.message.toString()))
            }

        }
    }

    override fun save(item: Persona): Result<Persona, PersonasException> {
        val validado=validator.validator(item)
        return if (validado.isOk){
            try {
                return Ok(repositorio.save(item))
            }catch (e:Exception){
                return Err(PersonasException.PersonaDatabaseException(e.message.toString()))
            }

        } else Err(validado.error)
    }

    override fun delete(id: Long): Result<Persona, PersonasException> {

        return try {
            repositorio.delete(id)?.let {
                cache.getIfPresent(id)?.let { cache.invalidate(id) }
                return Ok(it)
            } ?: Err(PersonasException.PersonaNotFoundException(id))
        }catch (e:Exception){
            return Err(PersonasException.PersonaDatabaseException(e.message.toString()))
        }
    }

    override fun update(id: Long, item: Persona): Result<Persona, PersonasException> {
        val validado=validator.validator(item)
        if (validado.isOk){
            try {
                repositorio.update(item,id)?.let { it ->
                    cache.getIfPresent(id)?.let {
                        cache.invalidate(id)
                        cache.put(id,it)
                    }
                    return Ok(it)
                }?: return Err(PersonasException.PersonaNotFoundException(id))
            }catch (e:Exception){
                return Err(PersonasException.PersonaDatabaseException(e.message.toString()))
            }

        }
        return Err(validado.error)
    }

}