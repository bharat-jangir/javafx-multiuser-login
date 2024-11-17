module com.rtd.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.rtd.javafx to javafx.fxml;
    exports com.rtd.javafx;
}