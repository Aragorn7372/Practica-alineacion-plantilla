package org.example.newteamultimateedition.personal.repository

import org.example.newteamultimateedition.personal.dao.PersonaDao
import org.example.newteamultimateedition.personal.extensions.copy
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Persona

import org.example.newteamultimateedition.users.models.User
import org.lighthousegames.logging.logging

class PersonasRepositoryImplementation(
    private val dao: PersonaDao,
    private val mapper: PersonaMapper,
): PersonalRepository {
    private val logger = logging()

    /**
     * @return devuelve la lista de personas
     */
    override fun getAll(): List<Persona> {
        logger.debug { "Getting all personas" }
        return dao.getAll().map { mapper.toDatabaseModel(it) }
    }

    /**
     * busca un usuario por id
     * @param id parametro que referencia al objeto
     * @return persona si se encuentra, nulo si no encuentra nada
     */
    override fun getById(id: Long): Persona? {
        logger.debug { "Getting persona by id $id" }
        return dao.getById(id.toInt())?.let { mapper.toDatabaseModel(it) }
    }

    /**
     * actualiza una persona en base a los datos y una id
     * @param objeto nuevo objeto a guardar
     * @param id parametro que referencia la objeto
     * @return persona si se encuentra, nulo si no se encuentra
     */
    override fun update(objeto: Persona, id: Long): Persona? {
        logger.debug { "Updating persona by id $id" }
    val updated = dao.update(mapper.toEntity(objeto),id.toInt())
        return if (updated==1){ if(objeto is Jugador){
            (objeto.copy(newId = id)
        } else if (objeto is Entrenador) {
            objeto.copy(newId = id)
        }else null
        }
        else null
    }

    /**
     * elimina en base a una id
     * @param id
     * @return devuelve persona si lo encuentra y elimina o nulo si no lo encuentra
     */
    override fun delete(id: Long): Persona? {
        logger.debug { "Deleting persona by id $id" }
        dao.getById(id.toInt())?.let {
            if (dao.deleteById(id.toInt())==1) return mapper.toDatabaseModel(it)
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
        val id = dao.save(mapper.toEntity(objeto))
        val persona: Persona
        if (objeto is Jugador){
            persona = objeto.copy(newId = id.toLong())
        }else{
            persona = (objeto as Entrenador).copy(newId = id.toLong())
        }
        return persona

    }

    override fun getByName(username: String): User? {
        TODO("Not yet implemented")
    }




}