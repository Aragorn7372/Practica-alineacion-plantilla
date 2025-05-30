package org.example.newteamultimateedition.alineacion.controllers

import com.github.benmanes.caffeine.cache.Cache
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.application.Platform
import javafx.beans.property.SimpleStringProperty
import javafx.fxml.FXML
import javafx.scene.Cursor.DEFAULT
import javafx.scene.Cursor.WAIT
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.HBox
import javafx.stage.FileChooser
import javafx.stage.Stage
import org.example.newteamultimateedition.alineacion.error.AlineacionError
import org.example.newteamultimateedition.alineacion.model.Alineacion
import org.example.newteamultimateedition.alineacion.viewmodels.AlineacionViewModel
import org.example.newteamultimateedition.common.locale.toLocalDateTime
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.models.Persona
import org.example.newteamultimateedition.routes.RoutesManager
import org.example.newteamultimateedition.users.models.User
import org.example.newteamultimateedition.personal.viewmodels.EquipoViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.LocalDate
import java.time.LocalDateTime


class AlineacionController(): KoinComponent {
    private val cache: Cache<Long, User> by inject()
    private val viewModel: AlineacionViewModel by inject()
    private val logger= logging()

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
    lateinit var tablaAlineacion: TableView<Alineacion>
    @FXML
    lateinit var colId: TableColumn<Alineacion, Long>
    @FXML
    lateinit var colFechaJuego: TableColumn<Alineacion, LocalDate>
    @FXML
    lateinit var colUpdatedAt: TableColumn<Alineacion, String>

    fun initialize() {
        initEvents()
        initBindings()
        initDefaultValues()
    }

    private fun initDefaultValues() {
        if(!cache.getIfPresent(0L).isAdmin) {
            buttonContainer.children.remove(editButton)
            buttonContainer.children.remove(createButton)
        }
        handleSesionView()
        viewModel.loadAllAlineciones()

        // Tabla
        viewModel.loadAllPersonas()
        tablaAlineacion.items = viewModel.state.value.alineaciones

        colId.cellValueFactory = PropertyValueFactory("id")
        colUpdatedAt.setCellValueFactory{ cellData ->
            val alineacion= cellData.value
            SimpleStringProperty(alineacion.updatedAt.toLocalDateTime())
        }
        colFechaJuego.cellValueFactory = PropertyValueFactory("juegoDate")
    }

    private fun handleSesionView() {

    }

    private fun initBindings() {
        viewModel.state.addListener { _, oldValue, newValue ->
            if (newValue != oldValue ) {
                tablaAlineacion.items = viewModel.state.value.alineaciones
            }
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
            if (cache.getIfPresent(0L).isAdmin) {
                RoutesManager.initAdminStage(searchBar.scene.window as Stage)
            }else{
                RoutesManager.initUserStage(searchBar.scene.window as Stage)
            }
        }

        exportButton.setOnAction { onExportarAction() }

        createButton.setOnAction {
            viewModel.modoAlineacion = AlineacionViewModel.ModoAlineacion.CREAR
            onCreateAlineacionAction()
        }

        editButton.setOnAction {
            viewModel.modoAlineacion = AlineacionViewModel.ModoAlineacion.EDITAR
            viewModel.alineacion = tablaAlineacion.selectionModel.selectedItem
            onCreateAlineacionAction()
        }

        viewButton.setOnAction {
            viewModel.alineacion = tablaAlineacion.selectionModel.selectedItem
            viewModel.modoAlineacion = AlineacionViewModel.ModoAlineacion.VISUALIZAR
            onCreateAlineacionAction()
        }
    }

    private fun onCreateAlineacionAction() {
        RoutesManager.initAlineacionesModalStage()
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