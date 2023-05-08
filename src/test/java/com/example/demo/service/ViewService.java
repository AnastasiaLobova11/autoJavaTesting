package com.example.demo.service;

import com.example.demo.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class ViewService {
    private Double height = -1.0;
    private Double weight= -1.0;
    public ViewService(){}
    public ViewService(Double height, Double weight) {
        this.height = height;
        this.weight = weight;
    }
    public void openNewView(ActionEvent actionEvent, String viewPath) throws IOException {
        newView(height, weight, viewPath);
        hideView(actionEvent);
    }

    public void newView(Double height, Double weight, String viewPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(viewPath));
        Parent root1 = (Parent) fxmlLoader.load();
        //Исправить это безобразие
        String s= HelloApplication.class.getResource("assets/testing.png").toExternalForm();
        Image image = new Image(s);
        Stage stage = new Stage();
        stage.setScene(new Scene(root1, height, weight));
        stage.getIcons().add(image);
        stage.show();
    }
    public void hideView(ActionEvent actionEvent){
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
