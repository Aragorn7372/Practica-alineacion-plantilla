package org.example.newteamultimateedition.alineacion.repository

import org.example.newteamultimateedition.alineacion.dao.AlineacionDao
import org.example.newteamultimateedition.alineacion.dao.CodigoDao
import org.example.newteamultimateedition.alineacion.mapper.AlineacionMapper
import org.example.newteamultimateedition.alineacion.model.Alineacion
import java.time.LocalDate

class AlineacionRepositoryImpl(
    private val alineacionDao: AlineacionDao,
    private val codigoDao: CodigoDao,
    private val mapper: AlineacionMapper
):AlineacioRepository {
    override fun getByDate(date: LocalDate): Alineacion? {
        val lista= alineacionDao.getByFechaJuego(date)?.let {
            val posiciones=codigoDao.getByAlineacionId(it.id)
            if(posiciones.isNotEmpty()){
                mapper.toDatabaseModel(it,posiciones.map { mapper.toModel(it) })
            } else null
        }
        return lista
    }

    override fun getAll(): List<Alineacion> {

    }

    override fun getById(id: Long): Alineacion? {
        val lista= alineacionDao.getById(id)?.let {
            val posiciones=codigoDao.getByAlineacionId(it.id)
            if(posiciones.isNotEmpty()){
                mapper.toDatabaseModel(it,posiciones.map { mapper.toModel(it) })
            } else null
        }
        return lista
    }

    override fun update(objeto: Alineacion, id: Long): Alineacion? {
        return if (alineacionDao.updateById(mapper.toEntity(objeto),id)==1) objeto else null
    }

    override fun delete(id: Long): Alineacion? {
        return getById(id)?.let {
            if (alineacionDao.deleteById(it.id)==1) {
                if(codigoDao.deleteByAlinecionId(it.id)==18) {
                it
                }else null
            } else null
        }
    }

    override fun save(objeto: Alineacion): Alineacion {
        val identificator= alineacionDao.save(mapper.toEntity(objeto)).toLong()
        val alineacion=objeto.copy(id=identificator)
        alineacion.personalList.map { it.copy(idAlineacion = alineacion.id) }
            .forEach { codigoDao.save(mapper.toEntity(it)) }
        return alineacion
    }
}