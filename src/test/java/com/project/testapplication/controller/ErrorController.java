package com.project.testapplication.controller;

import com.project.testapplication.service.ViewService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ErrorController {

    private final ViewService viewService = new ViewService(300., 400.);

    @FXML
    public void errorMessage(ActionEvent actionEvent) {
        viewService.hideView(actionEvent);
    }
}
