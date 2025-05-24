package org.example.newteamultimateedition.alineacion.repository

import org.example.newteamultimateedition.alineacion.dao.AlineacionDao
import org.example.newteamultimateedition.alineacion.mapper.AlineacionMapper
import org.example.newteamultimateedition.alineacion.model.Alineacion
import java.time.LocalDate

class AlineacionRepositoryImpl(
    private val dao: AlineacionDao,
    private val mapper: AlineacionMapper
):AlineacioRepository {
    override fun getByDate(date: LocalDate): Alineacion? {
        return dao.getbyFechaJuego(date)?.let { mapper.toDatabaseModel(it) }
    }

    override fun getAll(): List<Alineacion> {
        return dao.getAll().map { mapper.toDatabaseModel(it) }
    }

    override fun getById(id: Long): Alineacion? {
        return dao.getById(id)?.let { mapper.toDatabaseModel(it) }
    }

    override fun update(objeto: Alineacion, id: Long): Alineacion? {
        return if (dao.updateById(mapper.toEntity(objeto),id)==1) objeto else null
    }

    override fun delete(id: Long): Alineacion? {
        return dao.getById(id)?.let {
            if (dao.deleteById(id)==1) mapper.toDatabaseModel(it) else null
        }
    }

    override fun save(objeto: Alineacion): Alineacion {
        val identificator = dao.save(mapper.toEntity(objeto))
        return objeto.copy(id=identificator.toLong())
    }
}