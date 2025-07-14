module com.example.foodfave {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.foodfave to javafx.fxml;
    exports com.example.foodfave;
}