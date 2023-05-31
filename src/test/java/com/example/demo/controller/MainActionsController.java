package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.JarFileService;
import com.example.demo.service.RunTestsService;
import com.example.demo.service.ViewService;
import com.example.demo.service.old.ResultService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ComboBox<TestCase> comboBox;

    private final ViewService viewService = new ViewService(300., 400.);
    private final RunTestsService runTestsService = new RunTestsService();
    private final JarFileService jarFileService = new JarFileService();
    private final ResultService resultService = new ResultService();
    private final Map<String, Boolean> tesTResults = new HashMap<>();
    private final UserDao<User> userDao = new UserDaoImpl();
    private final TestCaseDao testCaseDao = new TestCaseDaoImpl();

    @FXML
    protected void runTestsFromJarFile(ActionEvent actionEvent) throws Exception {

        if (comboBox.getSelectionModel().isEmpty()) {
            viewService.newView(310., 195., "/com/example/demo/error-view.fxml", "You don't choose task!");
        } else {
            resultService.setUser(user);
            TestCase selectedTask = comboBox.getSelectionModel().getSelectedItem();
            Map<String, Boolean> result = null;
            List<Class<?>> classes = jarFileService.openJarFile(stage);
            if (classes == null) {
                viewService.newView(410., 195., "/com/example/demo/error-view.fxml",
                        "Failed to extract data\n from Jar file!");
                return;
            }
            for (Class<?> c : classes) {
                if (c.getSimpleName().equals(selectedTask.getClassName())) {
                    try {
                        result = runTestsService.runAllTestClassesFromJar(c, selectedTask);
                        tesTResults.putAll(result);
                        createResultTests(tesTResults);
                        TableTestController.tesTResults = tesTResults;
                        viewService.openNewView(actionEvent, "/com/example/demo/table-test-view.fxml");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            if (result == null) {
                viewService.newView(410., 195., "/com/example/demo/error-view.fxml",
                        "Selected task\n don't match with tested class");
            }
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

        if (comboBox.getSelectionModel().isEmpty()) {
            viewService.newView(310., 195., "/com/example/demo/error-view.fxml", "You don't choose task!");

        } else {
            TableAttemptController.testCase = comboBox.getSelectionModel().getSelectedItem();
            viewService.openNewView(actionEvent, "/com/example/demo/table-attempts-view.fxml");
        }
    }

    public void initialize() {
        ObservableList<TestCase> students = FXCollections.observableArrayList(testCaseDao.getAll());
        comboBox.setItems(students);
    }

    public void createResultTests(Map<String, Boolean> resultTests) {

        int pos = 0, neg = 0;
        for (Map.Entry<String, Boolean> entry : resultTests.entrySet()) {
            if (entry.getValue())
                pos++;
            else
                neg++;

        }
        userDao.update(user, comboBox.getSelectionModel().getSelectedItem(), pos, neg);
    }

}
