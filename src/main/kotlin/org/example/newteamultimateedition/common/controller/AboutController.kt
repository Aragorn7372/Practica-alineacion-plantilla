package org.example.newteamultimateedition.common.controller

import com.vaadin.open.Open
import javafx.fxml.FXML
import javafx.scene.Cursor
import javafx.scene.control.Hyperlink
import org.lighthousegames.logging.logging

/**
 * Clase que representa el controlador de la vista About.
 * @property githubLinkCarlos Link al GitHub de Carlos.
 * @property githubLinkSamuel Link al GitHub de Samuel.
 * @property githubLinkCristian Link al GitHub de Cristian.
 * @property githubLinkVictor Link al GitHub de Victor.
 * @property repositorioGitHub Link al repositorio.
 */
class AboutController {
    private val logger = logging()

    @FXML
    lateinit var githubLinkCarlos: Hyperlink

    @FXML
    lateinit var githubLinkSamuel: Hyperlink

    @FXML
    lateinit var githubLinkCristian: Hyperlink

    @FXML
    lateinit var githubLinkVictor: Hyperlink

    @FXML
    lateinit var repositorioGitHub: Hyperlink

    /**
     * Método automáticamente llamado por JavaFX cuando se crea el [AboutController] asociado al correspondiente .fxml
     * @see initLinks
     * @see initEffects
     */
    fun initialize() {
        initLinks()
        initEffects()
    }

    /**
     * Establece las funciones que tendrán los links al pulsar en ellos.
     */
    private fun initLinks(){
        githubLinkCarlos.setOnAction {
            logger.debug{ "Abriendo Github de Carlos" }
            Open.open("https://github.com/charlieecy")
        }
        githubLinkSamuel.setOnAction {
            logger.debug{ "Abriendo Github de Samuel" }
            Open.open("https://github.com/Sggz221")
        }
        githubLinkCristian.setOnAction {
            logger.debug{ "Abriendo Github de Samuel" }
            Open.open("https://github.com/Cristianortegaa")
        }
        githubLinkVictor.setOnAction {
            logger.debug{ "Abriendo Github de Samuel" }
            Open.open("https://github.com/Aragorn7372")
        }
        repositorioGitHub.setOnAction {
            logger.debug{ "Abriendo Github de Samuel" }
            Open.open("https://github.com/Aragorn7372/Practica-alineacion-plantilla")
        }
    }

    /**
     * Establece los cambios en el estado del ratón al posicionarlo encima de los enlaces.
     */
    private fun initEffects() {
        githubLinkCarlos.setOnMouseClicked {
            githubLinkCarlos.scene.cursor = Cursor.HAND
        }
        githubLinkSamuel.setOnMouseClicked {
            githubLinkSamuel.scene.cursor = Cursor.HAND
        }
        githubLinkCristian.setOnMouseClicked {
            githubLinkCristian.scene.cursor = Cursor.HAND
        }
        githubLinkVictor.setOnMouseClicked {
            githubLinkVictor.scene.cursor = Cursor.HAND
        }
        repositorioGitHub.setOnMouseClicked {
            githubLinkCristian.scene.cursor = Cursor.HAND
        }
    }
}