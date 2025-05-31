package org.example.newteamultimateedition.personal.services

import com.github.benmanes.caffeine.cache.Cache
import com.github.michaelbull.result.*
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import org.example.newteamultimateedition.common.database.provideDatabaseManager
import org.example.newteamultimateedition.personal.cache.darPersonasCache
import org.example.newteamultimateedition.personal.dao.getPersonasDao
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Persona
import org.example.newteamultimateedition.personal.repository.PersonasRepositoryImplementation
import org.example.newteamultimateedition.personal.storage.EquipoStorageImpl
import org.example.newteamultimateedition.personal.validator.PersonaValidation
import org.lighthousegames.logging.logging
import java.nio.file.Path

/**
 * Clase que representa el servicio del Personal
 * @property repositorio de [PersonasRepositoryImplementation]
 * @property cache de tipo [Cache] de [Long] como llave y [Persona] como value
 * @property validator de [PersonaValidation]
 * @property storage de [EquipoStorageImpl]
 * @property logger para representar el flujo de la aplicacion
 */
class PersonaServiceImpl(
    private val repositorio: PersonasRepositoryImplementation,
    private val cache: Cache<Long, Persona>,
    private val validator: PersonaValidation ,
    private val storage: EquipoStorageImpl,
):PersonaService {
    private val logger= logging()

    /**
     * Importa datos desde un archivo
     * @param fichero archivo de donde sacar datos
     * @return [List] de [Persona] si va bien o [PersonasError] si algo falla
     */
    override fun importarDatosDesdeFichero(fichero: Path): Result<List<Persona>, PersonasError> {
        logger.debug { "importando desde fichero" }
        val file= fichero.toFile()
        try {
            val lista = storage.fileRead(file)
            if (lista.isOk) {
                lista.value.forEach {
                    val validado = validator.validator(it)
                    if (validado.isOk) repositorio.save(it) else return Err(validado.error)
                }
            } else return Err(lista.error)
            return Ok(lista.value)
        }catch (e:Exception){
            return Err(PersonasError.PersonaDatabaseError(e.message.toString()))
        }
    }
    /**
     * Exporta datos a un archivo
     * @param fichero archivo donde guardar datos
     * @return [Unit] si va bien o [PersonasError] si algo falla
     */
    override fun exportarDatosDesdeFichero(fichero: Path): Result<Unit, PersonasError> {
        logger.debug { "exportando desde fichero" }
        val archivo= fichero.toFile()
        val lista: List<Persona>
        try {
            lista=repositorio.getAll()
        }catch (e:Exception){
            return Err(PersonasError.PersonaDatabaseError(e.message.toString()))
        }
        val result= storage.fileWrite(lista,archivo)
        if (result.isOk){
            return Ok(result.value)
        }
        return Err(result.error)
    }

    /**
     * Obtiene a todas las [Persona]
     * @return [List] de [Persona] si va bien o [PersonasError] si algo falla
     */
    override fun getAll(): Result<List<Persona>,PersonasError> {
        return try {
            Ok(repositorio.getAll().filter { !it.isDeleted })
        }catch (e:Exception){
            Err(PersonasError.PersonaDatabaseError(e.message.toString()))
        }
    }

    /**
     * Devuelve a una persona en base a una id
     * @param id [Long] identificador con el que buscar a la persona
     * @return [Persona] si va bien o [PersonasError] si algo falla
     */
    override fun getByID(id: Long): Result<Persona, PersonasError> {
        return cache.getIfPresent(id)?.let { Ok(it) }?:run {
            try {
                repositorio.getById(id)?.let {
                    cache.put(id,it)
                    return Ok(it)
                }?:run {
                    return Err(PersonasError.PersonaNotFoundError(id))
                }
            }catch (e:Exception){
                return Err(PersonasError.PersonaDatabaseError(e.message.toString()))
            }

        }
    }

    /**
     * Guarda a un [Persona]
     * @param item [Persona] a guardar
     * @return [Persona] si se guarda o [PersonasError] si algo falla
     */
    override fun save(item: Persona): Result<Persona, PersonasError> {
        logger.debug { "Guardando en el servicio con ID: ${item.id}" }
        val validado=validator.validator(item)
        return if (validado.isOk){
            try {
                return Ok(repositorio.save(item))
            }catch (e:Exception){
                return Err(PersonasError.PersonaDatabaseError(e.message.toString()))
            }

        } else Err(validado.error)
    }

    /**
     * Elimina a una [Persona] basándonos en un ID
     * @param id [Long] identificador a buscar
     * @return [Persona] eliminada o [PersonasError] si algo falla
     */
    override fun delete(id: Long): Result<Persona, PersonasError> {
        logger.debug { "Borrando en el servicio con ID: $id" }
        return try {
            repositorio.delete(id)?.let {
                cache.getIfPresent(id)?.let { cache.invalidate(id) }
                return Ok(it)
            } ?: Err(PersonasError.PersonaNotFoundError(id))
        }catch (e:Exception){
            return Err(PersonasError.PersonaDatabaseError(e.message.toString()))
        }
    }

    /**
     * Actualiza a una [Persona]
     * @param id [Long] identificador de la [Persona]
     * @param item [Persona] datos actualizados
     * @return [Persona] si se actualiza o [PersonasError] si algo falla
     */
    override fun update(id: Long, item: Persona): Result<Persona, PersonasError> {
        logger.debug { "Actualizando en el servicio con ID: $id" }
        val validado=validator.validator(item)
        if (validado.isOk){
            try {
                repositorio.update(item,id)?.let { it ->

                    cache.getIfPresent(id)?.let {
                        cache.invalidate(id)
                        cache.put(id,it)
                    }
                    return Ok(it)
                }?: return Err(PersonasError.PersonaNotFoundError(id))
            }catch (e:Exception){
                return Err(PersonasError.PersonaDatabaseError(e.message.toString()))
            }

        }
        return Err(validado.error)
    }

}