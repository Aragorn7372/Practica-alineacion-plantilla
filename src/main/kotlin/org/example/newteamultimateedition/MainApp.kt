package org.example.newteamultimateedition

import com.github.benmanes.caffeine.cache.Cache
import com.github.michaelbull.result.onSuccess
import javafx.application.Application
import javafx.stage.Stage
import org.example.newteamultimateedition.routes.RoutesManager
import org.example.newteamultimateedition.di.appModule
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Especialidad
import org.example.newteamultimateedition.personal.models.Persona
import org.example.newteamultimateedition.personal.services.PersonaServiceImpl

import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import java.io.File
import java.time.LocalDate
import java.time.LocalDateTime

class MainApp : Application(), KoinComponent {
    init {

        var persona = Entrenador(
            id = 1L,
            nombre = "Carlos",
            apellidos = "Ramírez Gómez",
            fechaNacimiento = LocalDate.of(1980, 5, 15),
            fechaIncorporacion = LocalDate.of(2020, 8, 1),
            salario = 45000.0,
            pais = "España",
            createdAt = LocalDateTime.of(2020, 8, 1, 9, 0),
            updatedAt = LocalDateTime.now(),
            imagen = "carlos_ramirez.png",
            especialidad = Especialidad.ENTRENADOR_PORTEROS
        )
        val service = PersonaServiceImpl()

        service.save(persona).onSuccess { println(it) }

        println(LocalDateTime.now().toString())
        // creamos Koin
        startKoin {
            printLogger() // Logger de Koin
            modules(appModule) // Módulos de Koin
        }
    }
    override fun start(primaryStage: Stage) {
        RoutesManager.apply {
            app= this@MainApp
        }.run { initSplashStage(primaryStage) }
    }
}

fun main() {
    Application.launch(MainApp::class.java)

}