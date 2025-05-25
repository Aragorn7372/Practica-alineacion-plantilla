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