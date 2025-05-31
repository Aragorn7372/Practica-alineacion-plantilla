package org.example.newteamultimateedition.di

import com.github.benmanes.caffeine.cache.Cache
import org.example.newteamultimateedition.alineacion.dao.AlineacionDao
import org.example.newteamultimateedition.alineacion.dao.LineaAlineacionDao
import org.example.newteamultimateedition.alineacion.dao.provideAlineacionDao
import org.example.newteamultimateedition.alineacion.dao.provideLineaAlineacionDao
import org.example.newteamultimateedition.alineacion.mapper.AlineacionMapper
import org.example.newteamultimateedition.alineacion.repository.AlineacionRepositoryImpl
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
import org.example.newteamultimateedition.personal.viewmodels.EquipoViewModel
import org.example.newteamultimateedition.personal.services.PersonaServiceImpl
import org.example.newteamultimateedition.personal.storage.*
import org.example.newteamultimateedition.alineacion.service.AlineacionServiceImpl
import org.example.newteamultimateedition.alineacion.viewmodels.AlineacionViewModel
import org.example.newteamultimateedition.alineacion.storage.AlineacionStorageImpl
import org.example.newteamultimateedition.alineacion.storage.AlineacionStorageHTML
import org.example.newteamultimateedition.alineacion.storage.AlineacionStoragePDF

/**
 * Módulo de Koin para la aplicación
 */
val appModule = module {

    // Common
    singleOf(::provideDatabaseManager) {
        bind<Jdbi>()
    }
    //Alineacion
    singleOf(::AlineacionServiceImpl){
        bind<AlineacionServiceImpl>()
    }
    singleOf(::AlineacionRepositoryImpl){
        bind<AlineacionRepositoryImpl>()
    }
    singleOf(::AlineacionMapper){
        bind<AlineacionMapper>()
    }

    // DAO: depende de Jdbi


    singleOf(::provideLineaAlineacionDao) {
        bind<LineaAlineacionDao>()
    }

    singleOf(::provideAlineacionDao) {
        bind<AlineacionDao>()
    }

    singleOf(::AlineacionValidate) {
        bind<AlineacionValidate>()
    }
    singleOf(::provideUsersDao) {
        bind<UsersDao>()
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
    singleOf(::PersonalStorageZip) {
        bind<PersonalStorageZip>()
    }
        singleOf(::EquipoStorageXML) {
        bind<EquipoStorageXML>()
    }
    singleOf(::AlineacionStorageImpl) {
        bind<AlineacionStorageImpl>()
    }
    singleOf(::AlineacionStorageHTML) {
        bind<AlineacionStorageHTML>()
    }
    singleOf(::AlineacionStoragePDF) {
        bind<AlineacionStoragePDF>()
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
    singleOf(::AlineacionViewModel)
}








