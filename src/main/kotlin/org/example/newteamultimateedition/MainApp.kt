package org.example.newteamultimateedition


import javafx.application.Application
import javafx.stage.Stage
import org.example.newteamultimateedition.routes.RoutesManager
import org.example.newteamultimateedition.di.appModule

import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

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