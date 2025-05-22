package org.example.newteamultimateedition.common.controller

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Label
import javafx.scene.control.ProgressBar
import javafx.stage.Stage
import org.example.newteamultimateedition.routes.RoutesManager
import kotlin.concurrent.thread

/**
 * Clase que representa el controlador de la vista de carga (splash) del programa.
 * @property progressBar [ProgressBar] la barra de progreso
 * @property isFinished [Boolean] variable que indica si la barra de progreso ha llegado a su fin
 */
class SplashController {
    @FXML
    lateinit var progressBar: ProgressBar
    @FXML
    lateinit var mensajeCarga: Label

    var isFinished: Boolean = false

    /**
     * Método automáticamente llamado por JavaFX cuando se crea el [SplashController] asociado al correspondiente .fxml
     * @see initEvents
     */
    fun initialize() {
        mensajeCarga.text = "Cargando... 0%"
        thread { isFinished = functionsInit() }
        initEvents()
    }

    /**
     * Establece la función que tendrá la barra de progreso.
     */
    private fun initEvents(){
        progressBar.progressProperty().addListener { _, _, newValue ->
            if(newValue == 1.0)
                mensajeCarga.text = "¡Listo!"
                RoutesManager.initLoginStage(progressBar.scene.window as Stage)
        }
    }

    /**
     * Rellena la barra de progreso
     * @see [progressBar]
     * @see [mensajeCarga]
     */
    private fun functionsInit(): Boolean {
        progressBar.progress = 0.0
        for (i in 0..100){
            val progress = i / 100.0
            Platform.runLater {
                progressBar.progress
                mensajeCarga.text = "Cargando... $i"

            }
            Thread.sleep(50)
        }
        return true
    }
}