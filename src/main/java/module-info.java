module edu.cnm.deepdive {
    requires javafx.controls;
    requires javafx.fxml;

    opens edu.cnm.deepdive to javafx.fxml;
    exports edu.cnm.deepdive;
}