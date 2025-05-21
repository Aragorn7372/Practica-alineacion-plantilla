package org.example.newteamultimateedition.di

import com.github.benmanes.caffeine.cache.Cache
import org.example.newteamultimateedition.common.database.provideDatabaseManager
import org.example.newteamultimateedition.personal.cache.darPersonasCache
import org.example.newteamultimateedition.personal.dao.PersonaDao
import org.example.newteamultimateedition.personal.dao.getPersonasDao
import org.example.practicaenequipocristianvictoraitornico.players.mappers.PersonaMapper
import org.example.practicaenequipocristianvictoraitornico.players.models.Persona
import org.example.newteamultimateedition.personal.repository.PersonasRepositoryImplementation
import org.example.practicaenequipocristianvictoraitornico.players.storage.PersonalStorageZip
import org.example.practicaenequipocristianvictoraitornico.players.storage.PersonalStorageCsv
import org.example.practicaenequipocristianvictoraitornico.players.storage.PersonalStorageBin
import org.example.practicaenequipocristianvictoraitornico.players.storage.PersonalStorageXml
import org.example.practicaenequipocristianvictoraitornico.players.storage.PersonalStorageJson
import org.example.newteamultimateedition.personal.validator.PersonaValidation
import org.example.newteamultimateedition.users.service.UsersServiceImpl
import org.example.newteamultimateedition.users.mapper.UsersMapper
import org.example.newteamultimateedition.users.repository.UsersRepositoryImpl
import org.example.newteamultimateedition.users.dao.provideUsersDao
import org.example.newteamultimateedition.users.dao.UsersDao
import org.example.newteamultimateedition.users.repository.UsersRepository
import org.example.practicaenequipocristianvictoraitornico.view.controller.LoginViewModel
import org.example.practicaenequipocristianvictoraitornico.view.players.PersonasViewModel
import org.jdbi.v3.core.Jdbi
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.koin.core.module.dsl.bind
import org.example.practicaenequipocristianvictoraitornico.players.services.PersonaServiceImpl


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

    // Tu ViewModel que puede depender de UsersService o repositorio
    singleOf(::LoginViewModel) {
        bind<LoginViewModel>()
    }

    // Otros binds que tengas para Personas, cache, validación...
    singleOf(::PersonalStorageZip) {
        bind<PersonalStorageZip>()
    }
    singleOf(::PersonalStorageBin) {
        bind<PersonalStorageBin>()
    }
    singleOf(::PersonalStorageXml) {
        bind<PersonalStorageXml>()
    }
    singleOf(::PersonalStorageJson) {
        bind<PersonalStorageJson>()
    }
    singleOf(::PersonalStorageCsv) {
        bind<PersonalStorageCsv>()
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
    singleOf(::PersonaMapper) {
        bind<PersonaMapper>()
    }
    singleOf(::UsersRepositoryImpl) {
        bind<UsersRepository>()
    }
    singleOf(::PersonasViewModel)
    singleOf(::PersonaServiceImpl){
        bind<PersonaServiceImpl>()
    }
}








