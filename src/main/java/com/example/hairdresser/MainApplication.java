package com.example.hairdresser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class which representing start method login panel
 */
public class MainApplication extends Application {

    private double x = 0;
    private double y = 0;

    /**
     * Launching the main application window
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 600);
        stage.setTitle("Hairdresser!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launch AppliactionController
     * @param args
     */
    public static void main(String[] args) {
        launch();
        // SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
    }
}