package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.entity.UserDaoImpl;
import com.example.demo.service.JarFileService;
import com.example.demo.service.RunTestsService;
import com.example.demo.service.ViewService;
import com.example.demo.service.old.ResultService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActionsController {
    public static User user;
    @FXML
    public Button selectButton;
    @FXML
    public Button infoButton;
    @FXML
    public Button attempt;
    @FXML
    private Stage stage;
    @FXML
    private TextArea textArea;
    @FXML
    private ComboBox<String> comboBox;

    private final ViewService viewService = new ViewService(300., 400.);
    private final RunTestsService runTestsService = new RunTestsService();
    private final JarFileService jarFileService = new JarFileService();
    private final ResultService resultService = new ResultService();
    private Map<String, Boolean> tesTResults = new HashMap<>();

    private final UserDaoImpl userDao = new UserDaoImpl();

    @FXML
    protected void runTestsFromJarFile(ActionEvent actionEvent) throws Exception {

        if (comboBox.getSelectionModel().isEmpty())
            viewService.newView(310.,195.,"/com/example/demo/error-view.fxml");
        else {
            resultService.setUser(user);
            comboBox.getSelectionModel().getSelectedItem();

            List<Class<?>> classes = jarFileService.openJarFile(stage);
            for (Class<?> c : classes) {
                if (c.getName().endsWith("Impl")) {
                    tesTResults.putAll(runTestsService.runAllTestClassesFromJar(c));
                }
            }
            createResultTests(tesTResults);
            TableTestController.tesTResults = tesTResults;
            viewService.openNewView(actionEvent, "/com/example/demo/table-test-view.fxml");
        }
    }

    @FXML
    protected void goBack(ActionEvent actionEvent) throws Exception {
        viewService.openNewView(actionEvent, "/com/example/demo/auth-view.fxml");
    }

    @FXML
    protected void getInfoAboutTests() {
        textArea.clear();
        resultService.getAllInfoAboutTests(textArea, tesTResults);
    }

    public void getInfoAboutAttempts(ActionEvent actionEvent) throws IOException {
        TableAttemptController.user = user;
        if (comboBox.getSelectionModel().isEmpty())
            viewService.newView(310., 195.,"/com/example/demo/error-view.fxml");
        else
            viewService.openNewView(actionEvent, "/com/example/demo/table-attempts-view.fxml");
    }

    public void initialize() {
        comboBox.getItems().add("Символы строки");
        comboBox.getItems().add("Маршруты автобусов");
        comboBox.getItems().add("Личные данные");
    }

    public void createResultTests(Map<String, Boolean> resultTests) {

        int pos = 0, neg = 0;
        for (Map.Entry<String, Boolean> entry : resultTests.entrySet()) {
            if (entry.getValue())
                pos++;
            else
                neg++;

        }

        userDao.update(user, pos, neg);
    }
}
