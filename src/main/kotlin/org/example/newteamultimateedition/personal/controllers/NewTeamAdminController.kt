
package org.example.newteamultimateedition.personal.controllers

import com.github.benmanes.caffeine.cache.Cache
import com.github.michaelbull.result.*
import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.Cursor.DEFAULT
import javafx.scene.Cursor.WAIT
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.FileChooser
import javafx.stage.Stage
import org.example.newteamultimateedition.personal.error.PersonasError
import org.example.newteamultimateedition.personal.mapper.toEntrenadorModel
import org.example.newteamultimateedition.personal.mapper.toJugadorModel
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Especialidad
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Persona

import org.example.newteamultimateedition.routes.RoutesManager
import org.example.newteamultimateedition.users.models.User
import org.example.newteamultimateedition.viewmodels.EquipoViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.LocalDate

/**
 * Clase que representa el controlador de la vista de administrador
 * @property viewModel viewModel del programa
 * @see [EquipoViewModel]
 */
class NewTeamAdminController(): KoinComponent {

    private val logger = logging()
    private val viewModel: EquipoViewModel by inject()
    private val cache: Cache<Long, User> by inject()

    /* Menu */
    @FXML
    lateinit var exitButton: MenuItem
    @FXML
    lateinit var exportButton: MenuItem
    @FXML
    lateinit var importButton: MenuItem
    @FXML
    lateinit var aboutButton: MenuItem
    @FXML
    lateinit var logoutButton: MenuItem
    @FXML
    lateinit var menuAlineacionesAdmin: MenuItem

    /* Master */
    @FXML
    lateinit var saveEntrenadorButton: Button
    @FXML
    lateinit var saveJugadorButton: Button
    @FXML
    lateinit var deleteAndCancelButton: Button
    @FXML
    lateinit var editAndSaveButton: Button


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

    /* Lógica */
    private var isEditButton: Boolean = true

    private var alreadyFiltered: Boolean = false

    /**
     * Método automáticamente llamado por JavaFX cuando se crea el [NewTeamAdminController] asociado al correspondiente .fxml
     * @see initEvents
     * @see initDefaultValues
     * @see initBindings
     */
    fun initialize() {
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
        nombreCuenta.text = "${cache.getIfPresent(1L).name}"
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
        //Comunes
        // De interfaz a ViewModel

        paisField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.persona.pais) viewModel.state.value = viewModel.state.value.copy(persona = EquipoViewModel.PersonalState(pais = newvalue))
        }
        salarioField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.persona.salario.toString()) viewModel.state.value = viewModel.state.value.copy(persona = EquipoViewModel.PersonalState(salario = newvalue.toDoubleOrNull() ?: 0.0))
        }
        incorporacionDP.valueProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.persona.fechaIncorporacion) viewModel.state.value = viewModel.state.value.copy(persona = EquipoViewModel.PersonalState(fechaIncorporacion = newvalue))
        }
        nacimientoDP.valueProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.persona.fechaNacimiento) viewModel.state.value = viewModel.state.value.copy(persona = EquipoViewModel.PersonalState(fechaNacimiento = newvalue))
        }
        apellidosField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.persona.apellidos) viewModel.state.value = viewModel.state.value.copy(persona = EquipoViewModel.PersonalState(apellidos = newvalue))
        }
        nombreField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.persona.nombre) viewModel.state.value = viewModel.state.value.copy(persona = EquipoViewModel.PersonalState(nombre = newvalue))
        }

        //Jugador
        minutosField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.persona.minutosJugados.toString()) viewModel.state.value = viewModel.state.value.copy(persona = EquipoViewModel.PersonalState(minutosJugados = newvalue.toIntOrNull() ?: 0))
        }
        partidosField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.persona.partidosJugados.toString()) viewModel.state.value = viewModel.state.value.copy(persona = EquipoViewModel.PersonalState(partidosJugados = newvalue.toIntOrNull() ?: 0))
        }
        golesField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.persona.goles.toString()) viewModel.state.value = viewModel.state.value.copy(persona = EquipoViewModel.PersonalState(goles = newvalue.toIntOrNull() ?: 0))
        }
        minutosField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.persona.minutosJugados.toString()) viewModel.state.value = viewModel.state.value.copy(persona = EquipoViewModel.PersonalState(minutosJugados = newvalue.toIntOrNull() ?: 0))
        }
        alturaField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.persona.altura.toString()) viewModel.state.value = viewModel.state.value.copy(persona = EquipoViewModel.PersonalState(altura = newvalue.toDoubleOrNull() ?: 0.0))
        }
        dorsalField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.persona.dorsal.toString()) viewModel.state.value = viewModel.state.value.copy(persona = EquipoViewModel.PersonalState(dorsal = newvalue.toIntOrNull() ?: 0))
        }

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

// Jugador
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
     * Detecta qué integrante de la tabla está seleccionado
     * @param newValue el integrante seleccionado
     * @see [EquipoViewModel.updateIntegranteSelected]
     */
    private fun onTablaSelected(newValue: Persona) {
        logger.debug { " Integrante seleccionado en la tabla: $newValue " }
        disableAll()
        viewModel.updatePersonalSelected(newValue)
        logger.debug { "IntegranteState selected: ${viewModel.state.value.persona}" }
    }

    /**
     * Asigna la función de cada elemento de la vista al hacer click sobre el mismo
     * @see [RoutesManager.initAboutStage]
     * @see [RoutesManager.onAppExit]
     * @see [showLogoutAlert]
     * @see [onImportarAction]
     * @see [onExportarAction]
     * @see [onCheckEditState]
     * @see [onCreateJugadorAction]
     * @see [onCreateEntrenadorAction]
     * @see [onCheckDeleteState]
     * @see [onImageSelectAction]
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

        importButton.setOnAction { onImportarAction() }

        exportButton.setOnAction { onExportarAction() }

        menuAlineacionesAdmin.setOnAction {
            RoutesManager.initAlineacionesAdminStage(apellidosField.scene.window as Stage)
        }


        saveJugadorButton.setOnAction {
            especialidad.toggles.forEach{ it.isSelected = false }
            onCheckEditState()
            onCreateJugadorAction()
        }

        saveEntrenadorButton.setOnAction {
            posicion.toggles.forEach{ it.isSelected = false }
            onCheckEditState()
            onCreateEntrenadorAction()
        }

        editAndSaveButton.setOnAction {
            onCheckEditState()
        }
        deleteAndCancelButton.setOnAction {
            onCheckDeleteState()
        }

        profilePicture.setOnMouseClicked {
            onImageSelectAction()
        }


        filterByJugadores.setOnAction { onFilterByJugadoresAction() }

        filterByEntrenadores.setOnAction { onFilterByEntrenadoresAction() }

        filterByNothing.setOnAction { onFilterByNothingAction() }

    }

    /**
     * Permite seleccionar una imagen para cargar como foto de perfil de un integrante
     * @see [EquipoViewModel.updateImageIntegrante]
     */
    private fun onImageSelectAction() {
        FileChooser().run {
            title = "Selecciona una imagen para el integrante"
            extensionFilters.addAll(FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"))
            showOpenDialog(RoutesManager.activeStage)
        }?.let {
            viewModel.updateImageIntegrante(it)
        }
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

    /**
     * Comprueba si el boton de eliminar debe cumplir la función de cancelar o eliminar
     * @see deleteFunction
     * @see cancelFunction
     */
    private fun onCheckDeleteState() {
        if (isEditButton) deleteFunction()
        else cancelFunction()
    }

    /**
     * Elimina el integrante seleccionado
     * @see onDeleteIntegranteAction
     */
    private fun deleteFunction() {
        onDeleteIntegranteAction()
    }

    /**
     * Elimina el integrante seleccionado
     * @see [EquipoViewModel.deleteIntegrante]
     */
    private fun onDeleteIntegranteAction() {
        val id = viewModel.state.value.persona.id
        viewModel.deletePersonas(id)
    }

    /**
     * Comprueba si el boton de editar debe cumplir la función de editar o la función de guardar
     * @see editFunction
     * @see saveFunction
     */
    private fun onCheckEditState() {
        if (isEditButton) editFunction()
        else saveFunction(viewModel.state.value.persona.especialidad == "") // Si no tiene especialidad, es Jugador
    }

    /**
     * Cancela la edición de un integrante, devuelve los botones a su aspecto original y desactiva todos los campos del detalle para que no puedan editarse
     * @see styleToEditButton
     * @see styleToDeleteButton
     * @see disableAll
     */
    private fun cancelFunction() {
        styleToEditButton()
        styleToDeleteButton()
        disableAll()
        isEditButton = true
    }

    /**
     * Guarda un integrante y cambia los botones adecuadamente
     * @see styleToEditButton
     * @see styleToDeleteButton
     * @see onSaveIntegranteAction
     */
    private fun saveFunction(esJugador: Boolean) {
        styleToEditButton()
        styleToDeleteButton()
        onSaveIntegranteAction(esJugador)
        isEditButton = true
    }

    /**
     * Edita los datos de un integrante y cambia los botones adecuadamente
     * @see styleToSaveButton
     * @see styleToCancelButton
     */
    private fun editFunction(){
        styleToSaveButton()
        styleToCancelButton()
        if(viewModel.state.value.persona.especialidad == "") enableJugador()
        else enableEntrenador()
        isEditButton = false
    }

    /**
     * Cambia el estilo del boton a el de un boton de editar
     */
    private fun styleToEditButton() {
        editAndSaveButton.style = "-fx-background-color: #be9407"
        editAndSaveButton.text = "Editar"
        // CAMBIAR FOTO
    }

    /**
     * Cambia el estilo del botón al de un botón de guardar
     */
    private fun styleToSaveButton() {
        editAndSaveButton.style = "-fx-background-color: #33b3ff"
        editAndSaveButton.text = "Guardar"
        // CAMBIAR FOTO
    }

    /**
     * Cambia el estilo del botón al de un botón de eliminar
     */
    private fun styleToDeleteButton() {
        deleteAndCancelButton.style = "-fx-background-color: #FF2C2C"
        deleteAndCancelButton.text = "Eliminar"
        // CAMBIAR FOTO
    }

    /**
     * Cambia el estilo del botón al de un botón de cancelar
     */
    private fun styleToCancelButton() {
        deleteAndCancelButton.style = "-fx-background-color: #e59111"
        deleteAndCancelButton.text = "Cancelar"
        // CAMBIAR FOTO
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
        profilePicture.isDisable = true
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
     * Habilita los campos comunes a [Jugador] y [Entrenador]
     */
    private fun enableComunes(){
        profilePicture.isDisable = false
        paisField.isDisable = false
        salarioField.isDisable = false
        incorporacionDP.isDisable = false
        nacimientoDP.isDisable = false
        apellidosField.isDisable = false
        nombreField.isDisable = false
    }

    /**
     * Habilita los campos que son propios de [Jugador]
     */
    private fun enableJugador(){
        enableComunes()
        minutosField.isDisable = false
        partidosField.isDisable = false
        golesField.isDisable = false // P@ssw0rd
        pesoField.isDisable = false
        alturaField.isDisable = false
        dorsalField.isDisable = false
        posicion.toggles.forEach { (it as RadioButton).isDisable = false }
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
     * Habilita los campos que son propios de [Entrenador]
     */
    private fun enableEntrenador(){
        enableComunes()
        especialidad.toggles.forEach { (it as RadioButton).isDisable = false }
    }

    /**
     * Crea un entrenador vacío
     * @see enableEntrenador
     * @see disableJugador
     * @see [EquipoViewModel.createEmptyIntegrante]
     */
    private fun onCreateEntrenadorAction(){
        logger.debug { "Creando Entrenador" }
        enableEntrenador()
        disableJugador()
        val emptyEntrenador = EquipoViewModel.PersonalState(especialidad = "ENTRENADOR_PORTEROS")
        viewModel.createEmptyPersona(emptyEntrenador)
    }

    /**
     * Crea un jugador vacío
     * @see enableJugador
     * @see disableEntrenador
     * @see [EquipoViewModel.createEmptyIntegrante]
     */
    private fun onCreateJugadorAction() {
        logger.debug { "Creando Jugador" }
        enableJugador()
        disableEntrenador()
        val emptyJugador = EquipoViewModel.PersonalState()
        viewModel.createEmptyPersona(emptyJugador)
    }

    /**
     * Guarda un integrante tras validar sus datos
     * @see validarJugador
     * @see validarEntrenador
     * @see [EquipoViewModel.saveIntegrante]
     */
    private fun onSaveIntegranteAction(esJugador: Boolean) {
        logger.debug { "Guardando nuevo jugador" }
        validarJugador()
        parseViewToIntegrante(esJugador) // Actualizamos el integrante seleccionado con los datos de la vista
        val newIntegrante: Persona
        logger.debug { "Mapeando integrante" }
        if (esJugador) newIntegrante = viewModel.state.value.persona.toJugadorModel()
        else newIntegrante = viewModel.state.value.persona.toEntrenadorModel()

        viewModel.saveIntegrante(newIntegrante)

    }

    /**
     * Crea un integrante en el [EquipoViewModel] recogiendo los datos que contienen los distintos campos de la interfaz
     * @param esJugador indica si es un jugador o un entrenador
     * @see getPosicionFromView
     * @see getEspecialidadFromView
     */
    private fun parseViewToIntegrante(esJugador: Boolean) {
        if (esJugador) {
            viewModel.state.value = viewModel.state.value.copy(
                persona = EquipoViewModel.PersonalState(
                    nombre = nombreField.text,
                    apellidos = apellidosField.text,
                    fechaNacimiento = nacimientoDP.value,
                    fechaIncorporacion = incorporacionDP.value,
                    salario = salarioField.text.toDoubleOrNull() ?: 0.0,
                    pais = paisField.text,
                    imagen = profilePicture.image.url,
                    posicion = getPosicionFromView(),
                    dorsal = dorsalField.text.toIntOrNull() ?: 0,
                    altura = alturaField.text.toDoubleOrNull() ?: 0.0,
                    peso = pesoField.text.toDoubleOrNull() ?: 0.0,
                    goles = golesField.text.toIntOrNull() ?: 0,
                    partidosJugados = partidosField.text.toIntOrNull() ?: 0,
                    minutosJugados = minutosField.text.toIntOrNull() ?: 0,
                )
            )
        }
        else {
            viewModel.state.value = viewModel.state.value.copy(
                persona = EquipoViewModel.PersonalState(
                    nombre = nombreField.text,
                    apellidos = apellidosField.text,
                    fechaNacimiento = nacimientoDP.value,
                    fechaIncorporacion = incorporacionDP.value,
                    salario = salarioField.text.toDoubleOrNull() ?: 0.0,
                    pais = paisField.text,
                    imagen = profilePicture.image.url,
                    especialidad = getEspecialidadFromView()
                )
            )
        }
        logger.debug { "Integrante parseado al estado: ${viewModel.state.value.persona}" }
    }

    /**
     * Obtiene el valor del campo posición de un jugador en función de la opción seleccionada en la vista
     * @return la posición
     */
    private fun getPosicionFromView(): String {
        if(radioDelantero.isSelected) return "DELANTERO"
        else if (radioCentro.isSelected) return "CENTROCAMPISTA"
        else if (radioDefensa.isSelected) return "DEFENSA"
        else if (radioPortero.isSelected) return "PORTERO"
        else return "FALLO"
    }

    /**
     * Obtiene el valor del campo especialidad de un entrenador en función de la opción seleccionada en la vista
     * @return la especialidad
     */
    private fun getEspecialidadFromView(): String {
        if(radioPrincipal.isSelected) return "ENTRENADOR_PRINCIPAL"
        else if (radioAsistente.isSelected) return "ENTRENADOR_ASISTENTE"
        else if (radioPorteros.isSelected) return "ENTRENADOR_PORTEROS"
        else return "FALLO"
    }

    /**
     * Valida los datos de un [Jugador]
     * @return un [Result] de [Unit] en caso de ser correctos o de [GestionErrors.InvalidoError] en el caso contrario
     */
    private fun validarJugador (): Result<Unit, PersonasError.PersonasInvalidoError> {
        logger.debug { "Validando jugador" }

        if (nombreField.text.isBlank()){
            return Err(PersonasError.PersonasInvalidoError("El nombre no puede estar vacío"))
        }

        if (apellidosField.text.isBlank()){
            return Err(PersonasError.PersonasInvalidoError("Los apellidos no pueden estar vacíos"))
        }

        if (nacimientoDP.value > LocalDate.now()){
            return Err(PersonasError.PersonasInvalidoError("La fecha de nacimiento no puede ser posterior a la fecha actual"))
        }

        if (incorporacionDP.value > LocalDate.now()){
            return Err(PersonasError.PersonasInvalidoError("La fecha de incorporación no puede ser posterior a la fecha actual"))
        }

        if (incorporacionDP.value < nacimientoDP.value) {
            return Err(PersonasError.PersonasInvalidoError("La fecha de incorporación no puede ser anterior a la fecha de nacimiento"))
        }

        if (salarioField.text.toDouble() < 0.0){
            return Err(PersonasError.PersonasInvalidoError("El salario no puede ser negativo"))
        }

        if (paisField.text.isBlank()){
            return Err(PersonasError.PersonasInvalidoError("El país de origen no puede estar en blanco"))
        }

        if (dorsalField.text.toInt() !in 1..99) {
            return Err(PersonasError.PersonasInvalidoError("El dorsal no puede ser menor a 1 ni mayor a 99"))
        }

        if (alturaField.text.toDouble() !in 0.0..3.0){
            return Err(PersonasError.PersonasInvalidoError("La altura no puede ser negativa ni superar los 3 metros"))
        }

        if (pesoField.text.toDouble() < 0.0) {
            return Err(PersonasError.PersonasInvalidoError("El peso no puede ser negativo"))
        }

        if (golesField.text.toInt() < 0) {
            return Err(PersonasError.PersonasInvalidoError("El número de goles no puede ser negativo"))
        }

        if (partidosField.text.toInt() < 0){
            return Err(PersonasError.PersonasInvalidoError("El número de partidos jugados no puede ser negativo"))
        }

        return Ok(Unit)
    }

    /**
     * Valida los datos de un [Entrenador]
     * @return un [Result] de [Unit] en caso de ser correctos o de [GestionErrors.InvalidoError] en el caso contrario
     */
    private fun validarEntrenador (): Result<Unit, PersonasError> {
        logger.debug { "Validando entrenador" }
        if (nombreField.text.isBlank()){
            return Err(PersonasError.PersonasInvalidoError("El nombre no puede estar vacío"))
        }

        if (apellidosField.text.isBlank()){
            return Err(PersonasError.PersonasInvalidoError("Los apellidos no pueden estar vacíos"))
        }

        if (nacimientoDP.value > LocalDate.now()){
            return Err(PersonasError.PersonasInvalidoError("La fecha de nacimiento no puede ser posterior a la fecha actual"))
        }

        if (incorporacionDP.value > LocalDate.now()){
            return Err(PersonasError.PersonasInvalidoError("La fecha de incorporación no puede ser posterior a la fecha actual"))
        }

        if (incorporacionDP.value < nacimientoDP.value) {
            return Err(PersonasError.PersonasInvalidoError("La fecha de incorporación no puede ser anterior a la fecha de nacimiento"))
        }

        if (salarioField.text.toDouble() < 0.0){
            return Err(PersonasError.PersonasInvalidoError("El salario no puede ser negativo"))
        }

        if (paisField.text.isBlank()){
            return Err(PersonasError.PersonasInvalidoError("El país de origen no puede estar en blanco"))
        }

        return Ok(Unit)

    }

    /**
     * Hace una copia de seguridad de los integrantes en un fichero
     * @see[EquipoViewModel.exportIntegrantestoFile]
     */
    private fun onExportarAction(){
        logger.debug{ "Iniciando FileChooser" }

        FileChooser().run {
            title = "Exportar integrantes"
            extensionFilters.add(FileChooser.ExtensionFilter("CSV", "*.csv"))
            extensionFilters.add(FileChooser.ExtensionFilter("JSON", "*.json"))
            extensionFilters.add(FileChooser.ExtensionFilter("XML", "*.xml"))
            extensionFilters.add(FileChooser.ExtensionFilter("BIN", "*.bin"))
            showSaveDialog(RoutesManager.activeStage)
        }?.let {
            // Cambiar el cursor a espera
            RoutesManager.activeStage.scene.cursor = WAIT
            Platform.runLater {
                viewModel.exportIntegrantestoFile(it)
                    .onSuccess {
                        showAlertOperation(
                            title = "Datos exportados",
                            mensaje = "Se han exportado los Integrantes."
                        )
                    }.onFailure { error: PersonasError->
                        showAlertOperation(alerta = AlertType.ERROR, title = "Error al exportar", mensaje = error.message)
                    }
                RoutesManager.activeStage.scene.cursor = DEFAULT
            }
        }
    }

    /**
     * Importa los integrantes de un fichero seleccionado por el usuario
     * @see [EquipoViewModel.loadIntegrantesFromFile]
     */
    private fun onImportarAction() {
        logger.debug{ "Iniciando FileChooser" }
        FileChooser().run {
            title = "Importar integrantes"
            extensionFilters.add(FileChooser.ExtensionFilter("CSV", "*.csv"))
            extensionFilters.add(FileChooser.ExtensionFilter("JSON", "*.json"))
            extensionFilters.add(FileChooser.ExtensionFilter("XML", "*.xml"))
            extensionFilters.add(FileChooser.ExtensionFilter("BIN", "*.bin"))
            showOpenDialog(RoutesManager.activeStage)
        }?.let {
            // Cambiar el cursor a espera
            RoutesManager.activeStage.scene.cursor = WAIT
            Platform.runLater {
                viewModel.loadIntegrantesFromFile(it)
                    .onSuccess {
                        showAlertOperation(
                            title = "Datos importados",
                            mensaje = "Se han importado los Integrantes."
                        )
                    }.onFailure { error: PersonasError->
                        showAlertOperation(alerta = AlertType.ERROR, title = "Error al importar", mensaje = error.message)
                    }
                RoutesManager.activeStage.scene.cursor = DEFAULT
            }
        }
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
