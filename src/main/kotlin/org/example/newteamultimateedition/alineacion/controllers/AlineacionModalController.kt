package org.example.newteamultimateedition.alineacion.controllers

import com.github.michaelbull.result.*
import javafx.beans.property.SimpleStringProperty
import javafx.fxml.FXML
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import javafx.stage.Stage
import org.example.newteamultimateedition.alineacion.error.AlineacionError
import org.example.newteamultimateedition.alineacion.viewmodels.AlineacionViewModel
import org.example.newteamultimateedition.personal.models.Entrenador
import org.example.newteamultimateedition.personal.models.Persona
import org.example.newteamultimateedition.personal.models.Jugador
import org.example.newteamultimateedition.personal.models.Posicion
import org.example.newteamultimateedition.routes.RoutesManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.lighthousegames.logging.logging
import java.time.LocalDate
import kotlin.math.E

/**
 * Clase que representa el controlador de la vista modal de alineaciones.
 * @property viewModel viewModel de alineaciones
 * @see [AlineacionViewModel]
 */
class AlineacionModalController: KoinComponent {


    private val viewModel: AlineacionViewModel by inject()


    /** Claves del mapa de jugadores
     * 1, 18: Porteros, siedo el 1 el titular y el 18 el suplente
     * 2, 3: Centrales (defensas)
     * 4, 5: Laterales (centrocampista)
     * 6, 7: Medio Centro (centrocampista)
     * 8, 9: Extremos (delantero)
     * 10, 11: Delanteros (delantero)
     * 12..17: Suplentes (NO Porteros)
     */


    
    //Campo fútbol
    @FXML
    private lateinit var extremoIzquierdoImg: ImageView


    @FXML
    private lateinit var delanteroIzquierdoImg: ImageView


    @FXML
    private lateinit var extremoDerechoImg: ImageView


    @FXML
    private lateinit var delanteroDerechoImg: ImageView


    @FXML
    private lateinit var lateralIzquierdoImg: ImageView


    @FXML
    private lateinit var centralIzquierdoImg: ImageView


    @FXML
    private lateinit var centralDerechoImg: ImageView


    @FXML
    private lateinit var lateralDerechoImg: ImageView


    @FXML
    private lateinit var medioCentroIzquierdoImg: ImageView


    @FXML
    private lateinit var medioCentroDerechoImg: ImageView


    @FXML
    private lateinit var porteroTitularImg: ImageView


    //Suplentes

    @FXML
    private lateinit var suplente1Img: ImageView


    @FXML
    private lateinit var suplente2Img: ImageView


    @FXML
    private lateinit var suplente3Img: ImageView


    @FXML
    private lateinit var suplente4Img: ImageView


    @FXML
    private lateinit var suplente5Img: ImageView


    @FXML
    private lateinit var suplente6Img: ImageView


    @FXML
    private lateinit var porteroSuplenteImg: ImageView


    @FXML
    private lateinit var entrenadorImg: ImageView


    //Tabla
    @FXML
    private lateinit var tablaAlineacion: TableView<Persona>

    @FXML
    private lateinit var colNombre: TableColumn<Persona, String>

    @FXML
    private lateinit var colEspecialidad: TableColumn<Persona, String>

    @FXML
    private lateinit var colRol: TableColumn<Persona, String>

    @FXML
    private lateinit var colGoles: TableColumn<Persona, String>

    @FXML
    private lateinit var colPartidos: TableColumn<Persona, String>

    @FXML
    private lateinit var descriptionText: TextArea

    @FXML
    private lateinit var fechaJuegoDP: DatePicker

    //Botones

    @FXML
    private lateinit var addButton: Button

    @FXML
    private lateinit var saveButton: Button

    @FXML
    private lateinit var deleteButton: Button
    @FXML
    private lateinit var buttonParent: HBox

    // Logica
    private val jugadores: MutableMap<Int,Jugador> = mutableMapOf()
    private var imagenes: MutableMap<Int, ImageView> =mutableMapOf()
    private val logger= logging()
    private var idAlineacion= 0L
    private var entrenador: Entrenador? =null
    private var pulsado: Int = 0

    /**
     * Método automáticamente llamado por JavaFX cuando se crea el [AlineacionModalController] asociado al correspondiente .fxml
     * @see initEvents
     * @see initDefaultValues
     * @see initBindings
     */
    fun initialize() {
        logger.debug { "Ejecutando initialize" }
        imagenes= mutableMapOf(Pair(1, porteroTitularImg), Pair(2, centralIzquierdoImg), Pair(3, centralDerechoImg),
            Pair(4, lateralIzquierdoImg), Pair(5, lateralDerechoImg), Pair(6, medioCentroIzquierdoImg),
            Pair(7, medioCentroDerechoImg), Pair(8, extremoIzquierdoImg),Pair(9,extremoDerechoImg),
            Pair(10, delanteroIzquierdoImg), Pair(11, delanteroDerechoImg), Pair(12, suplente1Img), Pair(13, suplente2Img),
            Pair(14, suplente3Img),Pair(15, suplente4Img), Pair(16, suplente5Img), Pair(17, suplente6Img), Pair(18, porteroSuplenteImg), Pair(19, entrenadorImg))
        initEvents()
        initBindings()
        initDefaultValues()
    }
    private fun isExternalImage(path: String):Boolean {
        if (path.startsWith("file:/"))
            return true
        else
            return false
    }

    /**
     * Asigna la función de cada elemento de la vista al hacer click sobre el mismo.
     * @see onAddPersona
     * @see onEliminarPersona
     * @see onGuardarAlineacion
     * @see deleteImagesStyle
     */
    private fun initEvents() {
        logger.debug { "Inicializando eventos" }
        addButton.setOnAction {
          onAddPersona()
        }
        deleteButton.setOnAction {
            onEliminarPersona()
        }
        saveButton.setOnAction {
            onGuardarAlineacion()
        }

        porteroTitularImg.setOnMouseClicked {
            pulsado=1
            logger.debug { "Portero Titular pulsado | pulsado: $pulsado" }
            deleteImagesStyle()
            porteroTitularImg.style="-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 1), 35, 0, 0, 0);"

        }

        porteroSuplenteImg.setOnMouseClicked {
            pulsado = 18
            logger.debug { "Portero suplente pulsado | pulsado: $pulsado" }
            deleteImagesStyle()
            porteroSuplenteImg.style = "-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 1), 35, 0, 0, 0);"
        }
        centralIzquierdoImg.setOnMouseClicked {
            pulsado=2
            logger.debug { "central pulsado | pulsado: $pulsado" }
            deleteImagesStyle()
            centralIzquierdoImg.style="-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 1), 35, 0, 0, 0);"

        }

        centralDerechoImg.setOnMouseClicked {
            pulsado = 3
            logger.debug { "Portero suplente pulsado | pulsado: $pulsado" }
            deleteImagesStyle()
            centralDerechoImg.style = "-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 1), 35, 0, 0, 0);"
        }
        lateralIzquierdoImg.setOnMouseClicked {
            pulsado=4
            logger.debug { "lateral pulsado | pulsado: $pulsado" }
            deleteImagesStyle()
            lateralIzquierdoImg.style="-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 1), 35, 0, 0, 0);"
        }

        lateralDerechoImg.setOnMouseClicked {
            pulsado = 5
            logger.debug { "lateral pulsado | pulsado: $pulsado" }
            deleteImagesStyle()
            lateralDerechoImg.style = "-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 1), 35, 0, 0, 0);"
        }
        medioCentroDerechoImg.setOnMouseClicked {
            pulsado=7
            logger.debug { "lateral pulsado | pulsado: $pulsado" }
            deleteImagesStyle()
            medioCentroDerechoImg.style="-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 1), 35, 0, 0, 0);"
        }

        medioCentroIzquierdoImg.setOnMouseClicked {
            pulsado = 6
            logger.debug { "lateral pulsado | pulsado: $pulsado" }
            deleteImagesStyle()
            medioCentroIzquierdoImg.style = "-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 1), 35, 0, 0, 0);"
        }
        extremoIzquierdoImg.setOnMouseClicked {
            pulsado=8
            logger.debug { "lateral pulsado | pulsado: $pulsado" }
            deleteImagesStyle()
            extremoIzquierdoImg.style="-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 1), 35, 0, 0, 0);"
        }

        extremoDerechoImg.setOnMouseClicked {
            pulsado = 9
            logger.debug { "lateral pulsado | pulsado: $pulsado" }
            deleteImagesStyle()
            extremoDerechoImg.style = "-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 1), 35, 0, 0, 0);"
        }
        delanteroIzquierdoImg.setOnMouseClicked {
            pulsado=10
            logger.debug { "lateral pulsado | pulsado: $pulsado" }
            deleteImagesStyle()
            delanteroIzquierdoImg.style="-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 1), 35, 0, 0, 0);"
        }

        delanteroDerechoImg.setOnMouseClicked {
            pulsado = 11
            logger.debug { "lateral pulsado | pulsado: $pulsado" }
            deleteImagesStyle()
            delanteroDerechoImg.style = "-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 1), 35, 0, 0, 0);"
        }
        suplente1Img.setOnMouseClicked {
            pulsado = 12
            logger.debug { "lateral pulsado | pulsado: $pulsado" }
            deleteImagesStyle()
            suplente1Img.style = "-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 1), 35, 0, 0, 0);"
        }
        suplente2Img.setOnMouseClicked {
            pulsado=13
            logger.debug { "lateral pulsado | pulsado: $pulsado" }
            deleteImagesStyle()
            suplente2Img.style="-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 1), 35, 0, 0, 0);"
        }

        suplente3Img.setOnMouseClicked {
            pulsado = 14
            logger.debug { "lateral pulsado | pulsado: $pulsado" }
            deleteImagesStyle()
            suplente3Img.style = "-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 1), 35, 0, 0, 0);"
        }
        suplente4Img.setOnMouseClicked {
            pulsado=15
            logger.debug { "lateral pulsado | pulsado: $pulsado" }
            deleteImagesStyle()
            suplente4Img.style="-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 1), 35, 0, 0, 0);"
        }

        suplente5Img.setOnMouseClicked {
            pulsado = 16
            logger.debug { "lateral pulsado | pulsado: $pulsado" }
            deleteImagesStyle()
            suplente5Img.style = "-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 1), 35, 0, 0, 0);"
        }
        suplente6Img.setOnMouseClicked {
            pulsado=17
            logger.debug { "lateral pulsado | pulsado: $pulsado" }
            deleteImagesStyle()
            suplente6Img.style="-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 1), 35, 0, 0, 0);"
        }
        entrenadorImg.setOnMouseClicked {
            pulsado=19
            logger.debug { "entrenador pulsado | pulsado: $pulsado" }
            deleteImagesStyle()
            entrenadorImg.style="-fx-effect: dropshadow(gaussian, rgba(255, 0, 0, 1), 35, 0, 0, 0);"
        }


    }

    /**
     * Acción asignada al botón [saveButton], guarda la alineación construída por el usuario.
     * @see comprobarDatos
     * @see [AlineacionViewModel.saveAlineacion]
     * @see showAlertOperation
     * @see [AlineacionViewModel.loadAllAlineciones]
     */
    private fun onGuardarAlineacion() {
        logger.debug { "Guardando alineación" }
        comprobarDatos().onSuccess {
            viewModel.saveAlineacion(entrenador!!, jugadores.toMap(), descriptionText.text.toString(),fechaJuegoDP.value,idAlineacion).onSuccess {
                showAlertOperation(AlertType.CONFIRMATION, "Guardado", "Alineación guardada con éxito")
                viewModel.loadAllAlineciones()
                val stage= descriptionText.scene.window as Stage
                stage.close()
            }.onFailure {
                showAlertOperation(AlertType.ERROR,"Error", it.message)
            }
        }.onFailure {
            showAlertOperation(AlertType.ERROR,"Error", it.message)
        }
    }

    /**
     * Acción asignada al botón [deleteButton], elimina una persona de la alineación.
     * @see deleteImagesStyle
     * @see showAlertOperation
     */
    private fun onEliminarPersona(){
        logger.debug { "Eliminando persona" }
        if (pulsado != 0){
            if (pulsado==19){
                viewModel.state.value.personas.add(entrenador)
                entrenador=null
                imagenes[pulsado]?.image = Image(RoutesManager.getResourceAsStream("media/profile_picture.png"))
                deleteImagesStyle()
                pulsado = 0
            }else {
                viewModel.state.value.personas.add(jugadores[pulsado])
                jugadores.remove(pulsado)
                imagenes[pulsado]?.image = Image(RoutesManager.getResourceAsStream("media/profile_picture.png"))
                deleteImagesStyle()
                pulsado = 0
            }
        }else{
            showAlertOperation(AlertType.ERROR,"Error", "Por favor, seleccione alguno")
        }
    }

    /**
     * Acción asignada al botón [addButton], añade una persona a la alineación.
     * @see deleteImagesStyle
     * @see showAlertOperation
     */
    private fun onAddPersona() {
        logger.debug { "Añadiendo persona" }
        comprobarSeleccionado().onSuccess {
            val persona= tablaAlineacion.selectionModel.selectedItem
            if (jugadores[pulsado]==null) {
                if (persona is Jugador) {
                    jugadores[pulsado] = persona
                    imagenes[pulsado]?.image = if(isExternalImage(persona.imagen)) Image(persona.imagen) else Image(RoutesManager.getResourceAsStream(persona.imagen))

                    viewModel.state.value.personas.remove(jugadores[pulsado])
                    deleteImagesStyle()
                    pulsado = 0
                } else {
                    entrenador = persona as Entrenador
                    imagenes[pulsado]?.image = if(isExternalImage(persona.imagen)) Image(persona.imagen) else Image(RoutesManager.getResourceAsStream(persona.imagen))
                    viewModel.state.value.personas.remove(entrenador)
                    deleteImagesStyle()
                    pulsado = 0
                }
            }else showAlertOperation(AlertType.ERROR,"Error", "Por favor, elimine el jugador antes de añadirlo")
        }.onFailure {
            showAlertOperation(AlertType.ERROR,"Error", it.message)
        }

    }

    /**
     * Valida si la persona que está seleccionada en la tabla puede colocarse en la posición elegida.
     * @return [Result] de [Unit] si todo va bien, o de [AlineacionError] en caso de haber algún error.
     */
    private fun comprobarSeleccionado(): Result<Unit, AlineacionError> {
        logger.debug { "Comprobando integrante seleccionado" }
        val persona=tablaAlineacion.selectionModel.selectedItem
        if (pulsado==0) return Err(AlineacionError.AlineacionInvalidoError("Debes seleccionar una posición en la que añadir al jugador o entrenador"))
        if (persona == null) return Err(AlineacionError.AlineacionInvalidoError("Selecciona algún jugador o persona"))
        if (persona is Jugador && pulsado == 19) return Err(AlineacionError.AlineacionInvalidoError("No puedes colocar un Jugador en la posición del Entrenador"))
        if (persona is Entrenador && pulsado<19) return Err(AlineacionError.AlineacionInvalidoError("No puedes colocar un Entrenador en las zonas de jugadores"))
        if (persona is Jugador && persona.posicion == Posicion.PORTERO){
            if (pulsado!=1 && pulsado !=18) return Err(AlineacionError.AlineacionInvalidoError("No puedes colocar un portero en las zonas de jugadores"))
        }
        if (persona is Jugador && persona.posicion != Posicion.PORTERO && (pulsado==1 || pulsado==18 )) return Err(AlineacionError.AlineacionInvalidoError("No puedes colocar un jugador en las zonas de porteros"))
        return Ok(Unit)
    }

    /**
     * Comprueba que los datos de los campos que forman la alineación sean correctos.
     * @return [Result] de [Unit] si todo es correcto, de [AlineacionError] en caso contrario.
     */
    private fun comprobarDatos():Result<Unit, AlineacionError>{
        logger.debug { "Comprobando datos de la alineación" }
        if (descriptionText.text.toString().isEmpty()) return Err(AlineacionError.AlineacionInvalidoError("La descripción no debe estar vacía"))
        if (entrenador == null) return Err(AlineacionError.AlineacionInvalidoError("Tiene que tener un entrenador"))
        if (jugadores.size != 18) return Err(AlineacionError.AlineacionInvalidoError("Tiene que tener 18 jugadores"))
        if (jugadores.count{it.value.posicion == Posicion.PORTERO}>2) return Err(AlineacionError.AlineacionInvalidoError("Tiene que tener máximo dos porteros"))
        if(fechaJuegoDP.value==null) return Err(AlineacionError.AlineacionInvalidoError("Pon una Fecha!!!"))
        if (fechaJuegoDP.value.isBefore(LocalDate.now())) return Err(AlineacionError.AlineacionInvalidoError("No puedes programar una alineación para un día ya pasado"))

        return Ok(Unit)
    }


    /**
     * Elimina los efectos visuales asignados a las imágenes de los integrantes de la alineación.
     */
    private fun deleteImagesStyle(){
        logger.debug { "Eliminando los efectos visuales de las imágenes de la plantilla" }
        porteroTitularImg.style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 0);"
        porteroSuplenteImg.style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 0);"
        centralIzquierdoImg.style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 0);"
        centralDerechoImg.style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 0);"
        lateralIzquierdoImg.style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 0);"
        lateralDerechoImg.style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 0);"
        medioCentroIzquierdoImg.style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 0);"
        medioCentroDerechoImg.style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 0);"
        extremoIzquierdoImg.style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 0);"
        extremoDerechoImg.style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 0);"
        delanteroIzquierdoImg.style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 0);"
        delanteroDerechoImg.style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 0);"
        suplente1Img.style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 0);"
        suplente2Img.style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 0);"
        suplente3Img.style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 0);"
        suplente4Img.style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 0);"
        suplente5Img.style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 0);"
        suplente6Img.style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 0);"
        entrenadorImg.style = "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 15, 0, 0, 0);"
    }

    /**
     * Inicializa los valores por defecto que tendrán los distintos campos de la vista.
     * @see handleSesionView
     * @see [AlineacionViewModel.modoAlineacion]
     * @see initDefaultForCreate
     * @see initDefaultForEdit
     * @see initDefaultForView
     */
    private fun initDefaultValues() {
        logger.debug { "Iniciando valores por defecto" }

        addButton.isDisable = true

        //Tabla
        tablaAlineacion.items = viewModel.state.value.personas

        colNombre.cellValueFactory = PropertyValueFactory("nombreCompleto")
        colEspecialidad.cellValueFactory = PropertyValueFactory("miEspecialidad")
        colRol.cellValueFactory = PropertyValueFactory("rol")
        colGoles.setCellValueFactory { cellData ->
            val persona = cellData.value
            if (persona is Jugador) {
                SimpleStringProperty(persona.goles.toString())
            } else {
                SimpleStringProperty("")
            }
        }

        colPartidos.setCellValueFactory { cellData ->
            val persona = cellData.value
            if (persona is Jugador) {
                SimpleStringProperty(persona.partidosJugados.toString())
            } else {
                SimpleStringProperty("")
            }

        }

        when (viewModel.modoAlineacion) {
            AlineacionViewModel.ModoAlineacion.CREAR -> initDefaultForCreate()
            AlineacionViewModel.ModoAlineacion.EDITAR -> initDefaultForEdit()
            AlineacionViewModel.ModoAlineacion.VISUALIZAR -> initDefaultForView()
        }

    }

    /**
     * Inicializa los enlaces de datos entre los campos de la vista y la información contenida en el [AlineacionViewModel].
     */
    private fun initBindings() {
        logger.debug { "Iniciando enlaces de datos" }

        tablaAlineacion.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            addButton.isDisable = newValue == null // Si no se esta seleccionando nada en la tabla, se desactiva el boton
        }

        viewModel.state.addListener { _, oldValue, newValue  ->
            if(newValue.personas != oldValue.personas) tablaAlineacion.items = viewModel.state.value.personas
        }
    }

    /**
     * Carga todos los integrantes del equipo en la ventana modal de alineaciones.
     * @see [AlineacionViewModel.loadAllAlineciones]
     */
    private fun initDefaultForCreate(){
        logger.debug { "Iniciando valores por defecto para el modo crear" }
        viewModel.loadAllPersonas()
    }

    /**
     * Carga todos los integrantes del equipo en la ventana modal de alineaciones, excepto los que están en la alineación.
     * @see [AlineacionViewModel.loadAllPersonas]
     * @see mapAlineacionToVista
     * @see asignarImagenes
     */
    private fun initDefaultForEdit(){
        logger.debug { "Iniciando valores por defecto para el modo editar" }
        viewModel.loadAllPersonas()
        mapAlineacionToVista()
        asignarImagenes()

        jugadores.forEach{
            viewModel.state.value.personas.remove(it.value)
        }
        viewModel.state.value.personas.remove(entrenador)



    }

    /**
     * Asigna la imagen correspondiente a cada integrante del equipo que forma la alineación.
     * @see [RoutesManager.getResourceAsStream]
     */
    private fun asignarImagenes(){
        jugadores.forEach {
            imagenes[it.key]?.image= Image(RoutesManager.getResourceAsStream(it.value.imagen))

        }
        imagenes[19]?.image= Image(RoutesManager.getResourceAsStream(entrenador!!.imagen))
    }

    /**
     * Prepara la vista para visualizar sin la posibilidad de modificar la alineación
     * @see mapAlineacionToVista
     * @see asignarImagenes
     */
    private fun initDefaultForView(){
        viewModel.state.value.personas.clear()
        buttonParent.children.remove(saveButton)
        buttonParent.children.remove(deleteButton)
        buttonParent.children.remove(addButton)
        mapAlineacionToVista()
        asignarImagenes()
        descriptionText.isDisable = true
        fechaJuegoDP.isDisable= true
        jugadores.forEach{
            viewModel.state.value.personas.add(it.value)
        }

    }

    /**
     * Transforma una alineación para mostrarla en formato de la vista de JavaFX.
     * @see [AlineacionViewModel.loadListJugadores]
     */
    private fun mapAlineacionToVista() {
        val alineacion = viewModel.alineacion
        idAlineacion = alineacion!!.id
        val listJugadores = viewModel.loadListJugadores(alineacion.personalList)
        entrenador = alineacion.entrenador
        listJugadores.onSuccess {
            alineacion.personalList.forEach { linea ->
                var idPersona = linea.idPersona
                var persona = listJugadores.value.filter { it.id == idPersona }
                jugadores[linea.posicion] = persona[0] as Jugador
            }
        }
        descriptionText.text = alineacion.descripcion
        fechaJuegoDP.value=alineacion.juegoDate
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
        Alert(alerta).apply {
            this.title = title
            this.contentText = mensaje
        }.showAndWait()
    }

}