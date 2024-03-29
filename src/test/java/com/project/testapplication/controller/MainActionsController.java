package com.project.testapplication.controller;

import com.project.testapplication.entity.TestCase;
import com.project.testapplication.entity.User;
import com.project.testapplication.service.JarFileService;
import com.project.testapplication.service.RunTestsService;
import com.project.testapplication.service.ViewService;
import com.project.testapplication.dao.TestCaseDao;
import com.project.testapplication.dao.TestCaseDaoImpl;
import com.project.testapplication.dao.UserDao;
import com.project.testapplication.dao.UserDaoImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActionsController {
    private final ViewService viewService = new ViewService(300., 400.);
    private final RunTestsService runTestsService = new RunTestsService();
    private final JarFileService jarFileService = new JarFileService();
    private final UserDao<User> userDao = new UserDaoImpl();
    private final TestCaseDao testCaseDao = new TestCaseDaoImpl();
    private final Map<String, Boolean> tesTResults = new HashMap<>();
    public static User user;
    @FXML
    private Stage stage;
    @FXML
    private ComboBox<TestCase> comboBox;

    /**
     * Начинает запуск тестирования.
     * Распаковывает JAR файл.
     **/
    @FXML
    protected void runTestsFromJarFile(ActionEvent actionEvent) {

        if (comboBox.getSelectionModel().isEmpty()) {
            viewService.newView(310., 195., "/com/project/testapplication/error-view.fxml", "Задание не выбранно!");
        } else {
            Map<String, Boolean> result = null;
            List<Class<?>> classes;

            TestCase selectedTask = comboBox.getSelectionModel().getSelectedItem();

            try {
               classes = jarFileService.openJarFile(stage);
            }catch (Exception e){
                viewService.newView(410., 195., "/com/project/testapplication/error-view.fxml",
                        "Произошла ошибка \nпри открытии Jar файла!");
                return;
            }

            if (classes == null) {
                viewService.newView(410., 195., "/com/project/testapplication/error-view.fxml",
                        "Ошибка извлечения данных\nиз Jar файла!");
                return;
            }
            for (Class<?> c : classes) {
                if (c.getSimpleName().equals(selectedTask.getClassName())) {
                    try {
                        result = runTestsService.runAllTestClassesFromJar(c, selectedTask);

                        createResultTests(result);
                        TableTestController.tesTResults = result;
                        viewService.openNewView(actionEvent, "/com/project/testapplication/table-test-view.fxml");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            //Если не нашлось класс с именем соответствующем выбранному заданию
            if (result == null) {
                viewService.newView(410., 195., "/com/project/testapplication/error-view.fxml",
                        "В архиве нет файла\n с необходимым названием!");
            }
        }
    }

    @FXML
    protected void goBack(ActionEvent actionEvent) {
        viewService.openNewView(actionEvent, "/com/project/testapplication/auth-view.fxml");
    }

    public void getInfoAboutAttempts(ActionEvent actionEvent){
        TableAttemptController.user = user;

        if (comboBox.getSelectionModel().isEmpty()) {
            viewService.newView(310., 195., "/com/project/testapplication/error-view.fxml", "Задание не выбрано!");

        } else {
            TableAttemptController.testCase = comboBox.getSelectionModel().getSelectedItem();
            viewService.openNewView(actionEvent, "/com/project/testapplication/table-attempts-view.fxml");
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
        TableTestController.pos=pos;
        TableTestController.neg=neg;

    }
}
