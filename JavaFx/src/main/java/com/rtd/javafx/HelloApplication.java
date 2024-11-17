package com.rtd.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Admin Portal");
        stage.setScene(scene);
        Image image = new Image(getClass().getResourceAsStream("/images/icon.png"));

        // Check if the image was loaded successfully
        if (image != null) {
            stage.getIcons().add(image);
        } else {
            System.out.println("Icon image not found!");
        }


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}