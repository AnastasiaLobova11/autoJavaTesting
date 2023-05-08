package com.example.demo.controller;

import com.example.demo.controller.old.HelloController;
import com.example.demo.entity.User;
import com.example.demo.entity.UserDaoImpl;
import com.example.demo.service.ViewService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.List;

import static java.lang.Integer.parseInt;

public class AuthController {
    @FXML
    TextField course;
    @FXML
    TextField group;
    @FXML
    TextField surname;
    private final UserDaoImpl userDao = new UserDaoImpl();

    private final ViewService viewService = new ViewService(300.,400.);

    @FXML
    public void onHelloButtonClick(ActionEvent actionEvent) throws IOException {
        User currUser;
        List<User> result = userDao.getAllByParameters(
                parseInt(course.getText()),
                parseInt(group.getText()),
                surname.getText());

        if (result == null || result.isEmpty()) {
            currUser = new User();
            currUser.setCourse(parseInt(course.getText()));
            currUser.setGroupa(parseInt(group.getText()));
            currUser.setSurname(surname.getText());
            userDao.save(currUser);
        } else
            currUser = result.get(0);

        HelloController.user = currUser;
        MainActionsController.user = currUser;

        viewService.openNewView(actionEvent, "/com/example/demo/main-actions-view.fxml");
    }

}

