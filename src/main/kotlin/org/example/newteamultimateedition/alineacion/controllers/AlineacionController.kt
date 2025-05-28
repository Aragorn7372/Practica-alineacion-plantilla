package org.example.newteamultimateedition.alineacion.controllers

import com.github.benmanes.caffeine.cache.Cache
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.Cursor.DEFAULT
import javafx.scene.Cursor.WAIT
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.layout.HBox
import javafx.stage.FileChooser
import javafx.stage.Stage
import org.example.newteamultimateedition.alineacion.error.AlineacionError
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.viewmodels.AlineacionViewModel
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Persona
import org.example.newteamultimateedition.routes.RoutesManager
import org.example.newteamultimateedition.users.models.User
import org.example.newteamultimateedition.personal.viewmodels.EquipoViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

class AlineacionController(): KoinComponent {
    private val cache: Cache<Long, User> by inject()
    private val viewModel: AlineacionViewModel by inject()
    private val logger= logging()
    private val alineacionList = viewModel.loadAllAlineciones()

    //Botones HBox de abajo
    @FXML
    lateinit var buttonContainer: HBox // Contenedor de los botones
    @FXML
    lateinit var editButton: Button
    @FXML
    lateinit var viewButton: Button
    @FXML
    lateinit var createButton: Button
    @FXML
    lateinit var backButton: Button

    // MenuBar

    @FXML
    lateinit var exportButton: MenuItem
    @FXML
    lateinit var exitButton: MenuItem
    @FXML
    lateinit var aboutButton:MenuItem
    @FXML
    lateinit var logoutButton:MenuItem

    // Hbox de arriba
    @FXML
    lateinit var searchBar: TextField

    //TableView
    @FXML
    lateinit var tablaAlineacion: TableView<Persona>
    @FXML
    lateinit var colId: TableColumn<String, String>
    @FXML
    lateinit var colFechaJuego: TableColumn<String, String>
    @FXML
    lateinit var colUpdatedAt: TableColumn<String, String>

    fun initialize() {
        initEvents()
        initBindings()
        initDefaultValues()
    }

    private fun initDefaultValues() {
        handleSesionView()
        viewModel.loadAllAlineciones()
    }

    private fun handleSesionView() {

    }

    private fun initBindings() {
        if(!cache.getIfPresent(1L).isAdmin) {
            buttonContainer.children.remove(editButton)
            buttonContainer.children.remove(createButton)
        }
        //Barra de búqueda
        searchBar.textProperty().addListener { _, oldValue, newValue ->
            if(oldValue != newValue) println("Arreglao")
        }
    }

    private fun initEvents() {
        aboutButton.setOnAction {
            RoutesManager.initAboutStage()
        }
        exitButton.setOnAction {
            RoutesManager.onAppExit()
        }

        logoutButton.setOnAction {
            showLogoutAlert()
        }
        backButton.setOnAction {
            if (cache.getIfPresent(1L).isAdmin) {
                RoutesManager.initAdminStage(searchBar.scene.window as Stage)
            }else{
                RoutesManager.initUserStage(searchBar.scene.window as Stage)
            }
        }

        exportButton.setOnAction { onExportarAction() }
    }
    private fun onExportarAction() {

    }
    private fun showAlertOperation(
        alerta: AlertType = AlertType.CONFIRMATION,
        title: String = "",
        mensaje: String = ""
    ) {
        Alert(alerta).apply {
            this.title = title
            this.contentText = mensaje
        }.showAndWait()
    }

    /**
     * Muestra una ventana de confirmación para cerrar sesión
     */
    private fun showLogoutAlert(){
        val alert = Alert(AlertType.CONFIRMATION).apply {
            this.title = "Cerrar sesión"
            this.contentText = "Si cierras la sesión perderás todos los datos no guardados. ¿Estás seguro de querer continuar?"
        }.showAndWait().ifPresent { opcion ->
            if (opcion == ButtonType.OK) {
                cache.cleanUp()
                RoutesManager.initLoginStage(searchBar.scene.window as Stage)
            }
        }
    }

}