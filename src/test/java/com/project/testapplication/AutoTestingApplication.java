package com.project.testapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AutoTestingApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent loader = FXMLLoader.load(getClass().getResource("auth-view.fxml"));
        primaryStage.setScene(new Scene(loader, 300, 400));
        String s = getClass().getResource("assets/testing.png").toExternalForm();
        Image image = new Image(s);
        primaryStage.getIcons().add(image);
        primaryStage.show();
    }

    public static void main(String[] args) {Application.launch(args);}

}