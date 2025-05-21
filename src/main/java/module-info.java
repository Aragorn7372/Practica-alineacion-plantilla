module org.example.newteamultimateedition {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens org.example.newteamultimateedition to javafx.fxml;
    exports org.example.newteamultimateedition;
}