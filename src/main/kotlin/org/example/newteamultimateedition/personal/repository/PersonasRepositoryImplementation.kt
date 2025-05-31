package org.example.newteamultimateedition.personal.repository

import org.example.newteamultimateedition.personal.dao.PersonaDao
import org.example.newteamultimateedition.personal.extensions.copy
import org.example.newteamultimateedition.personal.mapper.toEntity
import org.example.newteamultimateedition.personal.mapper.toModel
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Persona

import org.lighthousegames.logging.logging

/**
 * Clase que representa el repositorio de personas
 * @property dao DAO de personas
 */
class PersonasRepositoryImplementation(
    private val dao: PersonaDao,
    ): PersonalRepository {
    private val logger = logging()

    /**
     * @return devuelve la lista de personas
     */
    override fun getAll(): List<Persona> {
        logger.debug { "Obteniendo todas las personas" }
        return dao.getAll().map { it.toModel() }
    }

    /**
     * busca un usuario por id
     * @param id parametro que referencia al objeto
     * @return persona si se encuentra, nulo si no encuentra nada
     */
    override fun getById(id: Long): Persona? {
        logger.debug { "Obteniendo persona por ID: $id" }
        return dao.getById(id)?.toModel()
    }

    /**
     * actualiza una persona en base a los datos y una id
     * @param objeto nuevo objeto a guardar
     * @param id parametro que referencia la objeto
     * @return persona si se encuentra, nulo si no se encuentra
     */
    override fun update(objeto: Persona, id: Long): Persona? {
        logger.debug { "Actualizando persona por ID: $id" }
        val updated = dao.update(objeto.toEntity(),id)
        if (updated==1){
                if (objeto is Jugador){
                    return objeto.copy(newId = id)
                } else {
                    return (objeto as Entrenador).copy(newId = id)
                }
            }
        else return  null
    }

    override fun delete(id: Long): Persona? {
        logger.debug { "Borrado logico de persona por ID: $id" }
        dao.getById(id)?.let {
            if (dao.update(it.copy(isDeleted = true),id)==1) return it.toModel()
            else null
        }
        return null
    }

    /**
     * elimina en base a una id
     * @param id
     * @return devuelve persona si lo encuentra y elimina o nulo si no lo encuentra
     */
    fun deleteFisico(id: Long): Persona? {
        logger.debug { "Borrando persona por ID: $id" }
        dao.getById(id)?.let {
            if (dao.deleteById(id)==1) return it.toModel()
            else null
        }
        return null
    }

    /**
     * guarda una persona
     * @param objeto persona a guardar
     * @return persona guardada
     */
    override fun save(objeto: Persona): Persona {
        val id = dao.save(objeto.toEntity())
        val persona: Persona = if (objeto is Jugador){
            objeto.copy(newId = id.toLong())
        }else{
            (objeto as Entrenador).copy(newId = id.toLong())
        }
        return persona

    }
}