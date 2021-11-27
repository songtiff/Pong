module edu.csueastbay.cs401.pong {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    //requires javafx.graphics;

    opens edu.csueastbay.cs401.pong to javafx.fxml;
    opens edu.csueastbay.cs401.classic to javafx.fxml;
    opens edu.csueastbay.cs401.srishti to javafx.fxml;
    exports edu.csueastbay.cs401.srishti;
    exports edu.csueastbay.cs401.pong;
}