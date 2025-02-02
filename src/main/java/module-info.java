module sdufighters {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;

    opens sdufighters to javafx.fxml;
    exports sdufighters;
}