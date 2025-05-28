package org.example.newteamultimateedition.alineacion.repository

import org.example.newteamultimateedition.alineacion.dao.AlineacionDao
import org.example.newteamultimateedition.alineacion.dao.LineaAlineacionDao
import org.example.newteamultimateedition.alineacion.mapper.AlineacionMapper
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.repository.PersonalRepository
import org.lighthousegames.logging.logging
import java.time.LocalDate


class AlineacionRepositoryImpl(
    private val alineacionDao: AlineacionDao,
    private val lineaAlineacionDao: LineaAlineacionDao,
    private val mapper: AlineacionMapper,
    private val personaRepository: PersonalRepository
):AlineacioRepository {
    private val logger = logging()

    override fun getByDate(date: LocalDate): Alineacion? {
        logger.debug { "getByDate: $date" }
        val lista= alineacionDao.getByFechaJuego(date)?.let {
            val posiciones=lineaAlineacionDao.getByAlineacionId(it.id)
            personaRepository.getById(it.idEntrenador)?.let { entrenador ->
                if(posiciones.isNotEmpty()){
                    mapper.toModel(it,posiciones.map { mapper.toModel(it) },entrenador as Entrenador)
                } else null
            }?:return null
        }
        return lista
    }

    override fun getAll(): List<Alineacion> {
        logger.debug { "Obteniendo todas las alineaciones ... " }
        val alineacionesEntity = alineacionDao.getAll()
        if ( alineacionesEntity.isEmpty() ) return listOf()
        //Si no esta vacia devolvemos la lista de alineacinoes filtrada por las que estan vacias
        return alineacionesEntity.map {
            val codigoAlineaciones = lineaAlineacionDao.getByAlineacionId(it.id).map { codigo -> mapper.toModel(codigo) }
            personaRepository.getById(it.idEntrenador)?.let { entrenador ->
                val persona= entrenador!!
                mapper.toModel(it, codigoAlineaciones, persona as Entrenador)
            }
        }.filterNotNull().filter { it.personalList.isNotEmpty() }

    }

    override fun getById(id: Long): Alineacion? {
        logger.debug { "getById: $id" }
        val lista= alineacionDao.getById(id)?.let {
            val posiciones=lineaAlineacionDao.getByAlineacionId(it.id)
            personaRepository.getById(it.idEntrenador)?.let { entrenador ->
                if(posiciones.isNotEmpty()){
                    mapper.toModel(it,posiciones.map { mapper.toModel(it) },entrenador as Entrenador)
                } else null
            }?:return null
        }
        return lista
    }

    override fun update(objeto: Alineacion, id: Long): Alineacion? {
        logger.debug { "update: $objeto" }
            alineacionDao.getById(id)?.let{ entity ->
                val newAlineacion = mapper.toEntity(objeto.copy(id = id))
                val codigoAlineaciones = objeto.personalList
                if(alineacionDao.updateById(newAlineacion, entity.id)==1){
                    lineaAlineacionDao.deleteByAlinecionId(id)
                    codigoAlineaciones.forEach {
                        lineaAlineacionDao.save(mapper.toEntity(it))
                    }
                }else return null
                return mapper.toModel(newAlineacion, codigoAlineaciones,objeto.entrenador )
            }
        return null
    }

    override fun delete(id: Long): Alineacion? {
        logger.debug { "delete: $id" }
        return getById(id)?.let {
            if (alineacionDao.deleteById(it.id)==1) {
                if(lineaAlineacionDao.deleteByAlinecionId(it.id)==18) {
                it
                }else null
            } else null
        }
    }

    override fun save(objeto: Alineacion): Alineacion {
        logger.debug { "save: $objeto" }
        val identificator= alineacionDao.save(mapper.toEntity(objeto)).toLong()
        val alineacion=objeto.copy(id=identificator)
        alineacion.personalList.map { it.copy(idAlineacion = alineacion.id) }
            .forEach { lineaAlineacionDao.save(mapper.toEntity(it)) }
        return alineacion
    }

}