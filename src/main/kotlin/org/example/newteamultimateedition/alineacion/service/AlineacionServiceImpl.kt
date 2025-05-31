package org.example.newteamultimateedition.alineacion.service

import com.github.benmanes.caffeine.cache.Cache
import com.github.michaelbull.result.*
import org.example.newteamultimateedition.alineacion.error.AlineacionError
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.model.LineaAlineacion
import org.example.newteamultimateedition.alineacion.repository.AlineacionRepositoryImpl
import org.example.newteamultimateedition.alineacion.storage.AlineacionStorageImpl
import org.example.newteamultimateedition.alineacion.validador.AlineacionValidate
import org.example.newteamultimateedition.common.error.Errors
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Persona
import org.example.newteamultimateedition.personal.services.PersonaServiceImpl
import org.lighthousegames.logging.logging
import java.io.File
import java.time.LocalDate

/**
 * Clase Servicio que implementa [AlineacionService] y se le inyecta la Cache, almacenamiento, repositorio y validador
 * @param repository [AlineacionRepositoryImpl] Repositiorio de un equipo de futbol
 * @param cache [Cache] Cache que agiliza las consultas en memoria
 * @param validator [AlineacionValidate] Validador de un [Integrante]
 * @param storage [Alin] Storage que unifica todos los tipos  de archivos que maneja la aplicacion
 */
class AlineacionServiceImpl(
    private val validator: AlineacionValidate,
    private val cache: Cache<Long, Alineacion>,
    private val repository: AlineacionRepositoryImpl,
    private val personalService: PersonaServiceImpl,
    private val storage: AlineacionStorageImpl
): AlineacionService {
    private val logger= logging()

    /**
     * Obtiene una alineación por fecha asignada comprobando primero si existe en la caché o en su defecto, en el repositorio. Finalmente devuelve un error en caso de no existir
     * @param fecha fecha de la alineación
     * @return devuelve la [Alineacion] o [A]
     */
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

    /**
     * Obtiene todas las alineaciones en el repositorio. Finalmente devuelve un error en caso de no existir
     * @return obtiene la [List] de [Persona] o [Errors] si falla en algo
     */
    override fun getAllPersonas(): Result<List<Persona>, PersonasError> {
        logger.debug { "getJugadores" }
        return try {
            personalService.getAll()
        }catch (e: Exception) {
            Err(PersonasError.PersonaDatabaseError(e.message.toString()))
        }
    }

    /**
     * Obtiene los datos de los jugadores en base a una lista de [LineaAlineacion]
     * @param lista lista de [LineaAlineacion]
     * @return obtiene la [List] de [Persona] o [Errors] si falla en algo
     */
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

    /**
     * Obtiene todos los jugadores dado un id de alineación comprobando primero si existe en la caché o en su defecto, en el repositorio. Finalmente devuelve un error en caso de no existir
     * @param id identificador de la alineación
     * @return obtiene la [List] de [Persona] o [Errors] si falla en algo
     */
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

    /**
     * Llama al repositiorio y devuelve una lista con todas las alineaciones del equipo en la base de datos
     * @return [List] de [Alineacion]
     */
    override fun getAll(): Result<List<Alineacion>, AlineacionError> {
        logger.debug { "getAll" }
        return try {
            Ok(repository.getAll())
        }catch (e:Exception){
            Err(AlineacionError.AlineacionDatabaseError(e.message.toString()))
        }
    }

    /**
     * Obtiene una alineación por id comprobando primero si existe en la caché o en su defecto, en el repositorio. Finalmente devuelve un error en caso de no existir
     * @param id Identificador de la alienación
     * devuelve la [Alineacion] si se a localizado correctamente o [AlineacionError] si a fallado en algun momento
     */
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

    /**
     * Guarda una alineación en la base de datos tras validarla
     * @param item la [Alineacion] a guardar.
     * devuelve la [Alineacion] si se a guardado correctamente o [AlineacionError] si a fallado en algun momento
     */
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

    /**
     * Elimina una [Alineacion] de la base de datos y de la caché en caso de estar presente. Finalmente devuelve la alineación o en su defecto, un error
     * @param id Identificador de la [Alineacion] a eliminar
     * @return devuelve la [Alineacion] si se a eliminado correctamente o [AlineacionError] si a fallado en algun momento
     */
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

    /**
     * Actualiza la información de una [Alineacion] en la base de datos, en base a su id. Primero valida los datos, luego la actualiza en la base de datos y después, en la caché en caso de estar.
     * @param id Identificador de la alineación
     * @param item La alineación
     * @return devuelve la [Alineacion] si se a actualizado correctamente o [AlineacionError] si a fallado en algun momento
     */
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

    /**
     * Exporta la informacion de un equipo [Alineacion] a formatos html y pdf
     * @param alineacion equipo a exportar
     * @param lista jugadores a exportar
     * @param file ubicacion de archivo donde se va a guardar
     * @return devuelve [Unit] si se exporta correctamente o un [Errors] si algo a fallado en el proceso
     */
    fun exportarData(alineacion: Alineacion,lista: List<Persona>,file: File): Result<Unit, Errors> {
        logger.debug { "exportarData" }
          return  storage.fileWrite(alineacion,lista,file)

    }
}