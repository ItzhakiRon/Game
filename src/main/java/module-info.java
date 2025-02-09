module com.example.projectpentago {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.projectpentago to javafx.fxml;
    exports com.example.projectpentago;
}