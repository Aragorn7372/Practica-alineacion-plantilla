package org.example.newteamultimateedition.alineacion.controllers

import com.github.benmanes.caffeine.cache.Cache
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.layout.HBox
import javafx.stage.Stage
import org.example.newteamultimateedition.routes.RoutesManager
import org.example.newteamultimateedition.users.models.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

class AlineacionController(): KoinComponent {
    private val cache: Cache<Long, User> by inject()
    private val logger= logging()
    //Botones HBox de abajo
    @FXML
    lateinit var buttonContainer: HBox // Contenedor de los botones
    @FXML
    lateinit var btnEditar: Button
    @FXML
    lateinit var btnVisualizar: Button
    @FXML
    lateinit var btnCrearAlineacion: Button
    @FXML
    lateinit var btnEditarPersonas: Button

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
    @FXML
    lateinit var filterComboBox: ComboBox<String>

    //TableView
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
    }

    private fun handleSesionView() {

    }

    private fun initBindings() {
        if(!cache.getIfPresent(1L).isAdmin) {
            buttonContainer.children.remove(btnEditar)
            buttonContainer.children.remove(btnCrearAlineacion)
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