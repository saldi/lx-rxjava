module hellofx {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.react.ui to javafx.fxml;
    exports edu.react.ui;
}