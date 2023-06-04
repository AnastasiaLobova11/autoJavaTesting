package com.project.testapplication.controller;

import com.project.testapplication.entity.User;
import com.project.testapplication.service.ViewService;
import com.project.testapplication.utils.UserDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.util.List;

import static java.lang.Integer.parseInt;

public class AuthController {
    private final UserDaoImpl userDao = new UserDaoImpl();
    private final ViewService viewService = new ViewService(300., 400.);
    @FXML
    TextField course;
    @FXML
    TextField group;
    @FXML
    TextField surname;

    @FXML
    public void onHelloButtonClick(ActionEvent actionEvent)  {
        User currUser;
        if (course.getText().isEmpty() || group.getText().isEmpty() || surname.getText().isEmpty()) {
            viewService.newView(310., 195., "/com/project/testapplication/view/error-view.fxml",
                    "Fill in all the fields!");
        } else {
            try {
                List<User> result = userDao.getByParameters(
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

                MainActionsController.user = currUser;

                viewService.openNewView(actionEvent, "/com/project/testapplication/view/main-actions-view.fxml");
            }catch (NumberFormatException e){
                viewService.newView(310., 195., "/com/project/testapplication/view/error-view.fxml",
                        "Course and Group\n must be Integer!");
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void admin(ActionEvent actionEvent) {
        viewService.openNewView(actionEvent, "/com/project/testapplication/view/test-case-admin-view.fxml");
    }
}

