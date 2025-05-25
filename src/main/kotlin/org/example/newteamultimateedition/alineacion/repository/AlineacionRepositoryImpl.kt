package org.example.newteamultimateedition.alineacion.repository

import org.example.newteamultimateedition.alineacion.dao.AlineacionDao
import org.example.newteamultimateedition.alineacion.dao.CodigoDao
import org.example.newteamultimateedition.alineacion.mapper.AlineacionMapper
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.lighthousegames.logging.logging
import java.time.LocalDate


class AlineacionRepositoryImpl(
    private val alineacionDao: AlineacionDao,
    private val codigoDao: CodigoDao,
    private val mapper: AlineacionMapper
):AlineacioRepository {
    private val logger = logging()
    override fun getByDate(date: LocalDate): Alineacion? {
        logger.debug { "getByDate: $date" }
        val lista= alineacionDao.getByFechaJuego(date)?.let {
            val posiciones=codigoDao.getByAlineacionId(it.id)
            if(posiciones.isNotEmpty()){
                mapper.toModel(it,posiciones.map { mapper.toModel(it) })
            } else null
        }
        return lista
    }

    override fun getAll(): List<Alineacion> {
        logger.debug { "Obteniendo todas las alineaciones ... " }
        val alineacionesEntity = alineacionDao.getAll()
        if ( alineacionesEntity.isNullOrEmpty() ) return listOf()
        //Si no esta vacia devolvemos la lista de alineacinoes filtrada por las que estan vacias
        return alineacionesEntity.map {
            val codigoAlineaciones = codigoDao.getByAlineacionId(it.id).map { codigo -> mapper.toModel(codigo) }
            mapper.toModel(it, codigoAlineaciones)
        }.filter {  it.personalList.isNotEmpty()}
    }

    override fun getById(id: Long): Alineacion? {
        logger.debug { "getById: $id" }
        val lista= alineacionDao.getById(id)?.let {
            val posiciones=codigoDao.getByAlineacionId(it.id)
            if(posiciones.isNotEmpty()){
                mapper.toModel(it,posiciones.map { mapper.toModel(it) })
            } else null
        }
        return lista
    }

    override fun update(objeto: Alineacion, id: Long): Alineacion? {
        logger.debug { "update: $objeto" }
            alineacionDao.getById(id)?.let{ entity ->
                val newAlineacion = mapper.toEntity(objeto.copy(id = id))
                val codigoAlineaciones = objeto.personalList
                if(alineacionDao.updateById(newAlineacion, entity.id)==1){
                    codigoDao.deleteByAlinecionId(id)
                    codigoAlineaciones.forEach {
                        codigoDao.save(mapper.toEntity(it))
                    }
                }else return null
                return mapper.toModel(newAlineacion, codigoAlineaciones)
            }
        return null
    }

    override fun delete(id: Long): Alineacion? {
        logger.debug { "delete: $id" }
        return getById(id)?.let {
            if (alineacionDao.deleteById(it.id)==1) {
                if(codigoDao.deleteByAlinecionId(it.id)==18) {
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
            .forEach { codigoDao.save(mapper.toEntity(it)) }
        return alineacion
    }

}