package org.example.newteamultimateedition.di

import com.github.benmanes.caffeine.cache.Cache
import org.example.newteamultimateedition.alineacion.dao.AlineacionDao
import org.example.newteamultimateedition.alineacion.dao.CodigoDao
import org.example.newteamultimateedition.alineacion.dao.provideAlineacionDao
import org.example.newteamultimateedition.alineacion.dao.provideCodigoDao
import org.example.newteamultimateedition.alineacion.validador.AlineacionValidate
import org.example.newteamultimateedition.common.database.provideDatabaseManager
import org.example.newteamultimateedition.personal.cache.darPersonasCache
import org.example.newteamultimateedition.personal.dao.PersonaDao
import org.example.newteamultimateedition.personal.dao.getPersonasDao
import org.example.newteamultimateedition.personal.models.Persona
import org.example.newteamultimateedition.users.mapper.UsersMapper
import org.example.newteamultimateedition.personal.repository.PersonasRepositoryImplementation
import org.example.newteamultimateedition.personal.validator.PersonaValidation
import org.example.newteamultimateedition.users.cache.darUsersCache
import org.example.newteamultimateedition.users.service.UsersServiceImpl
import org.example.newteamultimateedition.users.repository.UsersRepositoryImpl
import org.example.newteamultimateedition.users.dao.provideUsersDao
import org.example.newteamultimateedition.users.dao.UsersDao
import org.example.newteamultimateedition.users.repository.UsersRepository
import org.jdbi.v3.core.Jdbi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.core.module.dsl.bind
import org.example.newteamultimateedition.users.models.User
import org.example.newteamultimateedition.viewmodels.EquipoViewModel
import org.example.newteamultimateedition.personal.services.PersonaServiceImpl
import org.example.newteamultimateedition.personal.storage.*

/**
 * Koin module for the application.
 */
val appModule = module {

    // Base: Jdbi
    singleOf(::provideDatabaseManager) {
        bind<Jdbi>()
    }

    // DAO: depende de Jdbi
    singleOf(::provideUsersDao) {
        bind<UsersDao>()
    }
    singleOf(::provideCodigoDao) {
        bind<CodigoDao>()
    }
    singleOf(::provideAlineacionDao) {
        bind<AlineacionDao>()
    }
    singleOf(::AlineacionValidate) {
        bind<AlineacionValidate>()
    }


    // Repository: depende de DAO
    singleOf(::UsersRepositoryImpl) {
        bind<UsersRepositoryImpl>()
    }

    // Service: depende de Repository
    singleOf(::UsersServiceImpl) {
        bind<UsersServiceImpl>()
    }

    // Otros servicios y utilidades (ejemplo)
    singleOf(::UsersMapper) {
        bind<UsersMapper>()
    }

    singleOf(::PersonaServiceImpl) {
        bind<PersonaServiceImpl>()
    }

    //storages
    singleOf(::EquipoStorageImpl) {
        bind<EquipoStorageImpl>()
    }
    singleOf(::EquipoStorageCSV) {
        bind<EquipoStorageCSV>()
    }
    singleOf(::EquipoStorageJSON) {
        bind<EquipoStorageJSON>()
    }
    singleOf(::EquipoStorageBIN) {
        bind<EquipoStorageBIN>()
    }
    singleOf(::EquipoStorageXML) {
        bind<EquipoStorageXML>()
    }



    singleOf(::PersonasRepositoryImplementation) {
        bind<PersonasRepositoryImplementation>()
    }
    singleOf(::darPersonasCache) {
        bind<Cache<Long, Persona>>()
    }
    singleOf(::PersonaValidation) {
        bind<PersonaValidation>()
    }
    singleOf(::getPersonasDao) {
        bind<PersonaDao>()
    }
    singleOf(::UsersRepositoryImpl) {
        bind<UsersRepository>()
    }
    singleOf(::darUsersCache) {
        bind<Cache<Long, User>>()
    }
    singleOf(::EquipoViewModel)
}








