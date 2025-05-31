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
import org.example.newteamultimateedition.common.error.Errors
import org.example.newteamultimateedition.common.locale.toLocalDateTime
import org.example.newteamultimateedition.personal.controllers.NewTeamAdminController
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

/**
 * Clase que representa el controlador de la vista de alineaciones.
 * @property cache memoria caché.
 * @property viewModel viewModel de alineaciones.
 * @see [AlineacionViewModel]
 */
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

    /**
     * Método automáticamente llamado por JavaFX cuando se crea el [AlineacionController] asociado al correspondiente .fxml
     * @see initEvents
     * @see initDefaultValues
     * @see initBindings
     */
    fun initialize() {
        logger.debug { "Ejecutando initialize" }
        initEvents()
        initBindings()
        initDefaultValues()
    }

    /**
     * Inicializa los valores por defecto que tendrán los distintos campos de la vista.
     * @see handleSesionView
     * @see [AlineacionViewModel.loadAllAlineciones]
     * @see [AlineacionViewModel.loadAllPersonas]
     */
    private fun initDefaultValues() {
        logger.debug { "Iniciando valores por defecto" }
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

    /**
     * Inicializa los enlaces de datos entre los campos de la vista y la información contenida en el [AlineacionViewModel].
     */
    private fun initBindings() {
        logger.debug { "Iniciando enlaces de datos" }
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

    /**
     * Asigna la función de cada elemento de la vista al hacer click sobre el mismo.
     * @see [RoutesManager.initAboutStage]
     * @see [RoutesManager.initAdminStage]
     * @see [RoutesManager.initUserStage]
     * @see [RoutesManager.onAppExit]
     * @see showLogoutAlert
     * @see onExportarAction
     * @see [AlineacionViewModel.ModoAlineacion]
     * @see onCreateAlineacionAction
     */
    private fun initEvents() {
        logger.debug { "Iniciando eventos" }
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
                if(true) RoutesManager.initUserStage(searchBar.scene.window as Stage)
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

    /**
     * Acción asignada al botón [createButton], inicia la ventana modal de alineaciones.
     * @see [RoutesManager.initAlineacionesModalStage]
     */
    private fun onCreateAlineacionAction() {
        logger.debug { "Ejecutando acción del botón crear" }
        RoutesManager.initAlineacionesModalStage()
    }

    /**
     * Acción asignada al botón [exportButton]. El usuario elige en qué formato (html o pdf) y ruta desea exportar los datos.
     * @see [FileChooser]
     * @see [RoutesManager.showAlertOperation]
     */
    private fun onExportarAction() {
        logger.debug { "Ejecutando acción del botón exportar" }
        FileChooser().run {
            title = "Exportar integrantes"
            extensionFilters.add(FileChooser.ExtensionFilter("HTML", "*.html"))
            extensionFilters.add(FileChooser.ExtensionFilter("PDF", "*.pdf"))
            showSaveDialog(RoutesManager.activeStage)
        }?.let {
            // Cambiar el cursor a espera
            RoutesManager.activeStage.scene.cursor = WAIT
            Platform.runLater {
                viewModel.exportarData(tablaAlineacion.selectionModel.selectedItem,it)
                    .onSuccess {
                        showAlertOperation(
                            title = "Datos exportados",
                            mensaje = "Se ha exportado la alineacion."
                        )
                    }.onFailure { error: Errors->
                        showAlertOperation(alerta = AlertType.ERROR, title = "Error al exportar", mensaje = error.message)
                    }
                RoutesManager.activeStage.scene.cursor = DEFAULT
            }
        }
    }

    /**
     * Inicia una ventana de confirmación.
     * @param alerta tipo de alerta.
     * @param title título de la ventana.
     * @param mensaje mensaje de la ventana.
     */
    private fun showAlertOperation(
        alerta: AlertType = AlertType.CONFIRMATION,
        title: String = "",
        mensaje: String = ""
    ) {
        logger.debug { "Iniciando ventana de alerta" }
        Alert(alerta).apply {
            this.title = title
            this.contentText = mensaje
        }.showAndWait()
    }

    /**
     * Muestra una ventana de confirmación para cerrar sesión. En caso de confirmar, se limpia la caché.
     * @see [RoutesManager.initLoginStage]
     *
     */
    private fun showLogoutAlert(){
        logger.debug { "Iniciando ventana de confirmación de cierre de sesión" }
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