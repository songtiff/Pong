module edu.csueastbay.cs401.pong {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens edu.csueastbay.cs401.pong to javafx.fxml;
    opens edu.csueastbay.cs401.classic to javafx.fxml;
    opens edu.csueastbay.cs401.StarWarsPong to javafx.fxml;
    opens edu.csueastbay.cs401.mattsPong to javafx.fxml;
    exports edu.csueastbay.cs401.pong;
    //exports edu.csueastbay.cs401.mattsPong;

}