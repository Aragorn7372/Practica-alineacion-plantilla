package org.example.newteamultimateedition.users.controller

import com.github.benmanes.caffeine.cache.Cache
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Result
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.stage.Stage
import org.example.newteamultimateedition.routes.RoutesManager
import org.example.newteamultimateedition.users.exception.UsersException
import org.example.newteamultimateedition.users.models.User
import org.example.newteamultimateedition.users.service.UsersServiceImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.mindrot.jbcrypt.BCrypt

/**
 * Controlador de Login
 * @property dao DAO que interviene con la base de datos
 * @property acercaDeButton [Button] Boton 'Acerca de'
 * @property errorMessage [Label] Label que saca un mensaje de error
 * @property passwordText [PasswordField] Campo que recoge la contraseña
 * @property userText [TextField] Campo que recoge el usuario
 * @property loginButton [Button] boton que procesa el inicio de sesion
 */

class LoginController: KoinComponent {
    private val dao: UsersServiceImpl by inject()
    private val cache: Cache<Long,User> by inject()

    @FXML
    lateinit var acercaDeButton: Button
    @FXML
    lateinit var errorMessage: Label
    @FXML
    lateinit var passwordText: PasswordField
    @FXML
    lateinit var userText: TextField
    @FXML
    lateinit var loginButton: Button

    @FXML
    fun initialize() {
        initEvents()
    }

    private fun initEvents() {
        acercaDeButton.setOnAction {
            RoutesManager.initAboutStage()
        }

        loginButton.setOnAction {
            handleLogin()
        }

        userText.textProperty().addListener { _, oldValue, newValue ->
            if (oldValue != newValue) {
                errorMessage.text = ""
                userText.style = "-fx-border-color: rgba(0,0,0,0);"
                passwordText.style = "-fx-border-color: rgba(0,0,0,0);"
            }
        }
        passwordText.textProperty().addListener { _, oldValue, newValue ->
            if (oldValue != newValue) {
                errorMessage.text = ""
                userText.style = "-fx-border-color: rgba(0,0,0,0);" +
                        "-fx-border-width: 1px;"
                passwordText.style = "-fx-border-color: rgba(0,0,0,0);"
            }
        }
    }

    private fun handleLogin() {
        val result = validateForm(userText.text, passwordText.text)
        if(result.isErr) showUserError(result.error.message)
        else{
            cache.put(1L,result.value)
            if (result.value.isAdmin){
                RoutesManager.initAdminStage(userText.scene.window as Stage)
            }else RoutesManager.initUserStage(userText.scene.window as Stage)
        }
    }

    private fun showUserError(error: String) {
        errorMessage.style = "-fx-text-fill: #FF2C2C;"
        userText.style = "-fx-border-color: #FF2C2C;" +
                "-fx-border-width: 2px;" +
                "-fx-border-radius: 3"
        passwordText.style = "-fx-border-color: #FF2C2C;" +
                "-fx-border-width: 2px;" +
                "-fx-border-radius: 3"
        errorMessage.text = error
    }

    private fun validateForm(username: String, password: String): Result<User, UsersException> {
        //val encrypt= BCrypt.hashpw(password, BCrypt.gensalt(12))
        if (username.isEmpty() || password.isEmpty()) {
            return Err(UsersException.ContraseniaEquivocadaException("No puede estar en blanco"))
        }
        return dao.goodLogin(username,password)
    }
}

