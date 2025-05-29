package org.example.newteamultimateedition.personal.controllers

import com.github.benmanes.caffeine.cache.Cache
import com.github.michaelbull.result.*
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.Stage
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Especialidad
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Persona

import org.example.newteamultimateedition.routes.RoutesManager
import org.example.newteamultimateedition.users.models.User
import org.example.newteamultimateedition.personal.viewmodels.EquipoViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging

/**
 * Clase que representa el controlador de la vista de usuario
 * @property viewModel viewModel del programa
 * @see [EquipoViewModel]
 */
class NewTeamUserController(): KoinComponent {
    private val logger = logging()
    private val viewModel: EquipoViewModel by inject()
    private val cache: Cache<Long,User> by inject()

    /* Menu */
    @FXML
    lateinit var exitButton: MenuItem
    @FXML
    lateinit var aboutButton: MenuItem
    @FXML
    lateinit var logoutButton: MenuItem
    @FXML
    lateinit var menuAlineacionesUser: MenuItem

    /* Detalle */

    // Comunes
    @FXML
    lateinit var profilePicture: ImageView
    @FXML
    lateinit var paisField: TextField
    @FXML
    lateinit var salarioField: TextField
    @FXML
    lateinit var incorporacionDP: DatePicker
    @FXML
    lateinit var nacimientoDP: DatePicker
    @FXML
    lateinit var apellidosField: TextField
    @FXML
    lateinit var nombreField: TextField

    //Jugadores
    @FXML
    lateinit var minutosField: TextField
    @FXML
    lateinit var partidosField: TextField
    @FXML
    lateinit var golesField: TextField
    @FXML
    lateinit var pesoField: TextField
    @FXML
    lateinit var alturaField: TextField
    @FXML
    lateinit var dorsalField: TextField
    @FXML
    lateinit var posicion: ToggleGroup
    @FXML
    lateinit var radioPortero: RadioButton
    @FXML
    lateinit var radioDefensa: RadioButton
    @FXML
    lateinit var radioCentro: RadioButton
    @FXML
    lateinit var radioDelantero: RadioButton

    // Entrenadores
    @FXML
    lateinit var especialidad: ToggleGroup
    @FXML
    lateinit var radioPorteros: RadioButton
    @FXML
    lateinit var radioAsistente: RadioButton
    @FXML
    lateinit var radioPrincipal: RadioButton

    /* Main */
    @FXML
    lateinit var nombreCuenta: Label
    @FXML
    lateinit var colSalario: TableColumn<Persona, Double>
    @FXML
    lateinit var colEspecialidad: TableColumn<Persona, Especialidad>
    @FXML
    lateinit var colRol: TableColumn<Persona, String>
    @FXML
    lateinit var colNombre: TableColumn<Persona, String>
    @FXML
    lateinit var listIntegrantes: TableView<Persona>
    @FXML
    lateinit var filterByEntrenadores: MenuItem
    @FXML
    lateinit var filterByJugadores: MenuItem
    @FXML
    lateinit var filterByNothing: MenuItem
    @FXML
    lateinit var searchBar: TextField

    /* Footer */
    @FXML
    lateinit var totalPlantilla: Label
    @FXML
    lateinit var minutosAvg: Label
    @FXML
    lateinit var golesAvg: Label

    @FXML

    /* Lógica */

    private var alreadyFiltered: Boolean = false

    /**
     * Método automáticamente llamado por JavaFX cuando se crea el [NewTeamAdminController] asociado al correspondiente .fxml
     * @see initEvents
     * @see initDefaultValues
     * @see initBindings
     */
    fun initialize() {
        viewModel.loadAllPersonas()
        initEvents()
        initBindings()
        initDefaultValues()
    }

    /**
     * Inicializa los valores por defecto que tendrán los distintos campos de la vista
     * @see disableAll
     */
    private fun initDefaultValues() {
        disableAll()

        //Tabla
        listIntegrantes.items = viewModel.state.value.personas
        //Columnas, ya se bindean solas en base al contenido de la tabla
        colNombre.cellValueFactory =PropertyValueFactory("nombreCompleto")
        colSalario.cellValueFactory =PropertyValueFactory("salario")
        colRol.cellValueFactory = PropertyValueFactory("rol")
        colEspecialidad.cellValueFactory = PropertyValueFactory("miEspecialidad")
        nombreCuenta.text = "${cache.getIfPresent(0L).name}"
    }

    /**
     * Inicializa los enlaces de datos entre los campos de la vista y la información contenida en el [EquipoViewModel]
     * @see filterByName
     * @see filterByJugadores
     * @see filterByEntrenadores
     * @see filterByNothing
     * @see isExternalImage
     * @see RoutesManager.getResourceAsStream
     * @see desmarcarPosicionesJugador
     * @see desmarcarEspecialidadesEntrenador
     * @see onTablaSelected
     */
    private fun initBindings(){
        //Barra de búqueda
        searchBar.textProperty().addListener { _, _, newValue ->
            filterByName(newValue)
        }

        //Reflejar cambios del estado en el detalle
        viewModel.state.addListener { _, _, newValue ->

            //Comunes
            if(newValue.persona.imagen != profilePicture.image.url) profilePicture.image =
                if(isExternalImage(newValue.persona.imagen)){
                    Image(newValue.persona.imagen)
                } else {
                    Image(RoutesManager.getResourceAsStream(newValue.persona.imagen))
                }
            if (newValue.persona.nombre != nombreField.text) nombreField.text = newValue.persona.nombre
            if (newValue.persona.apellidos != apellidosField.text) apellidosField.text = newValue.persona.apellidos
            if (newValue.persona.pais != paisField.text) paisField.text = newValue.persona.pais
            if (newValue.persona.salario.toString() != salarioField.text) salarioField.text = newValue.persona.salario.toString()
            if (newValue.persona.fechaIncorporacion != incorporacionDP.value) incorporacionDP.value = newValue.persona.fechaIncorporacion
            if (newValue.persona.fechaNacimiento != nacimientoDP.value) nacimientoDP.value = newValue.persona.fechaNacimiento

            //Jugador
            if (newValue.persona.minutosJugados.toString() != minutosField.text) minutosField.text = newValue.persona.minutosJugados.toString()
            if (newValue.persona.partidosJugados.toString() != partidosField.text) partidosField.text = newValue.persona.partidosJugados.toString()
            if (newValue.persona.goles.toString() != golesField.text) golesField.text = newValue.persona.goles.toString()
            if (newValue.persona.peso.toString() != pesoField.text) pesoField.text = newValue.persona.peso.toString()
            if (newValue.persona.altura.toString() != alturaField.text) alturaField.text = newValue.persona.altura.toString()
            if (newValue.persona.dorsal.toString() != dorsalField.text) dorsalField.text = newValue.persona.dorsal.toString()
            if (newValue.persona.posicion == "CENTROCAMPISTA") {
                radioCentro.isSelected = true
                desmarcarEspecialidadesEntrenador()
            } else if (newValue.persona.posicion == "DELANTERO") {
                radioDelantero.isSelected = true
                desmarcarEspecialidadesEntrenador()
            } else if (newValue.persona.posicion == "DEFENSA") {
                radioDefensa.isSelected = true
                desmarcarEspecialidadesEntrenador()
            } else if (newValue.persona.posicion == "PORTERO") {
                radioPortero.isSelected = true
                desmarcarEspecialidadesEntrenador()
            }

            //Entrenador
            if (newValue.persona.especialidad == "ENTRENADOR_ASISTENTE") {
                radioAsistente.isSelected = true
                desmarcarPosicionesJugador()
            } else if (newValue.persona.especialidad == "ENTRENADOR_PORTEROS") {
                radioPorteros.isSelected = true
                desmarcarPosicionesJugador()
            } else if (newValue.persona.especialidad == "ENTRENADOR_PRINCIPAL") {
                radioPrincipal.isSelected = true
                desmarcarPosicionesJugador()
            }
        }


        listIntegrantes.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let { onTablaSelected(newValue) }
        }

        //Footer
        golesAvg.textProperty().bind(viewModel.state.map { it.goalAvg })
        minutosAvg.textProperty().bind(viewModel.state.map { it.minutesAvg })
        totalPlantilla.textProperty().bind(viewModel.state.map { it.totalCost })
    }

    /**
     * Comprueba si una imagen está o no en la carpeta resources del proyecto
     * @param path ruta de la imagen
     * @return true en caso de no estar en resources, false en caso de estar en resources
     *
     */
    private fun isExternalImage(path: String):Boolean {
        if (path.startsWith("file:/"))
            return true
        else
            return false
    }

    /**
     * Deshabilita todos los campos del detalle para que el usuario no pueda interaccionar con estos
     * @see disableComunes
     * @see disableJugador
     * @see disableEntrenador
     */
    private fun disableAll() {
        disableComunes()
        disableJugador()
        disableEntrenador()
    }

    /**
     * Deshabilita los campos comunes a [Jugador] y [Entrenador]
     */
    private fun disableComunes(){
        paisField.isDisable = true
        salarioField.isDisable = true
        incorporacionDP.isDisable = true
        nacimientoDP.isDisable = true
        apellidosField.isDisable = true
        nombreField.isDisable = true
    }

    /**
     * Deshabilita los campos que son propios de [Jugador]
     */
    private fun disableJugador(){
        minutosField.isDisable = true
        partidosField.isDisable = true
        golesField.isDisable = true
        pesoField.isDisable = true
        alturaField.isDisable = true
        dorsalField.isDisable = true
        posicion.toggles.forEach { (it as RadioButton).isDisable = true }
    }

    /**
     * Deshabilita los campos que son propios de [Entrenador]
     */
    private fun disableEntrenador(){
        especialidad.toggles.forEach { (it as RadioButton).isDisable = true }
    }

    /**
     * Deselecciona las especialidades de [Entrenador]
     */
    private fun desmarcarEspecialidadesEntrenador() {
        radioAsistente.isSelected = false
        radioPorteros.isSelected = false
        radioPrincipal.isSelected = false
    }

    /**
     * Deselecciona las posiciones de [Jugador]
     */
    private fun desmarcarPosicionesJugador() {
        radioCentro.isSelected = false
        radioDelantero.isSelected = false
        radioDefensa.isSelected = false
        radioPortero.isSelected = false
    }

    /**
     * Detecta qué integrante de la tabla está seleccionado
     * @param newValue el integrante seleccionado
     * @see [EquipoViewModel.updateIntegranteSelected]
     */
    private fun onTablaSelected(newValue: Persona) {
        logger.debug { " Integrante seleccionado en la tabla: $newValue " }
        viewModel.updatePersonalSelected(newValue)
    }

    /**
     * Asigna la función de cada elemento de la vista al hacer click sobre el mismo
     * @see [RoutesManager.initAboutStage]
     * @see [RoutesManager.onAppExit]
     * @see [showLogoutAlert]
     * @see [onSortByNombreAction]
     * @see [onSortBySalarioAction]
     * @see [onSortByNothingAction]
     * @see [onFilterByNothingAction]
     * @see [onFilterByEntrenadoresAction]
     * @see [onFilterByJugadoresAction]
     */
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
        menuAlineacionesUser.setOnAction {
           RoutesManager.initAlineacionesAdminStage(apellidosField.scene.window as Stage)
        }


        filterByJugadores.setOnAction { onFilterByJugadoresAction() }

        filterByEntrenadores.setOnAction { onFilterByEntrenadoresAction() }

        filterByNothing.setOnAction { onFilterByNothingAction() }

    }

    /**
     * Filtra la lista de integrantes por nombre
     * @param cadena la cadena a filtrar
     * @see [EquipoViewModel.quitarFiltros]
     * @see [EquipoViewModel.filterIntegrantes]
     */
    private fun filterByName (cadena: String){
        logger.debug { "Filtrando integrantes por nombre" }

        viewModel.quitarFiltros()

        val integrantesFiltradosPorNombre = viewModel.state.value.personas.filter { it.nombreCompleto.lowercase().contains(cadena.lowercase()) }
        viewModel.filterPersonas(integrantesFiltradosPorNombre)
    }

    /**
     * Elimina los filtrados por jugador y entrenador
     * @see [EquipoViewModel.quitarFiltros]
     */
    private fun onFilterByNothingAction() {
        logger.debug { "Quitando filtros de jugador y entrenador" }

        viewModel.quitarFiltros()
        alreadyFiltered = false

    }

    /**
     * Filtra los entrenadores de la lista de integrantes
     * @see [EquipoViewModel.loadAllIntegrantes]
     * @see [EquipoViewModel.filterIntegrantes]
     */
    private fun onFilterByEntrenadoresAction() {
        logger.debug { "Filtrando los jugadores" }

        if (alreadyFiltered) viewModel.loadAllPersonas()

        val entrenadoresFiltrados: List<Persona> = viewModel.state.value.personas.filterIsInstance<Entrenador>()
        viewModel.filterPersonas(entrenadoresFiltrados)
        alreadyFiltered = true
    }

    /**
     * Filtra los jugadores de la lista de integrantes
     * @see [EquipoViewModel.loadAllIntegrantes]
     * @see [EquipoViewModel.filterIntegrantes]
     */
    private fun onFilterByJugadoresAction() {
        logger.debug { "Filtrando los jugadores" }

        if (alreadyFiltered) viewModel.loadAllPersonas()

        val jugadoresFiltrados: List<Persona> = viewModel.state.value.personas.filterIsInstance<Jugador>()
        viewModel.filterPersonas(jugadoresFiltrados)
        alreadyFiltered = true
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
