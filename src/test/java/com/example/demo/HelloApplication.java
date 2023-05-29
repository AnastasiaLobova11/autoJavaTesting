package com.example.demo;

import com.example.demo.controller.AuthController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.flywaydb.core.Flyway;
import java.io.IOException;
import java.util.Objects;



public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent loader = FXMLLoader.load(getClass().getResource("auth-view.fxml"));
        //scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());
        primaryStage.setScene(new Scene(loader, 300, 400));
        String s = getClass().getResource("assets/testing.png").toExternalForm();
        Image image = new Image(s);
        primaryStage.getIcons().add(image);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        Application.launch(args);

    }

}