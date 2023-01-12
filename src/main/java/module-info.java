module com.example.hairdresser {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires itextpdf;
    requires layout;
    requires kernel;

    opens com.example.hairdresser to javafx.fxml;
    exports com.example.hairdresser;
}