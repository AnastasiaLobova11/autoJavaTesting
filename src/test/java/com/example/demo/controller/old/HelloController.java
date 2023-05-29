package com.example.demo.controller.old;

import com.example.demo.entity.User;
import com.example.demo.service.JarFileService;
import com.example.demo.service.old.ResultService;
import com.example.demo.service.RunTestsService;
import com.example.demo.service.ViewService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelloController {

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

    private final ViewService viewService = new ViewService(300., 400.);
    private final RunTestsService runTestsService = new RunTestsService();
    private final JarFileService jarFileService = new JarFileService();
    private final ResultService resultService = new ResultService();
    private Map<String, Boolean> tesTResults = new HashMap<>();

    @FXML
    protected void runTestsFromJarFile() throws Exception {

        resultService.setUser(user);
        textArea.setVisible(true);

        List<Class<?>> classes = jarFileService.openJarFile(stage);
        for (Class<?> c : classes) {
            if (c.getName().endsWith("Impl")) {
                textArea.appendText(c.getSimpleName() + " Tests\n");
//                tesTResults.putAll(runTestsService.runAllTestClassesFromJar(c,""));
            }
        }
        resultService.createResultTests(textArea,tesTResults);
        infoButton.setVisible(true);
        attempt.setVisible(true);

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

    public void getInfoAboutAttempts() {
       resultService.getInfoAboutAttempts(textArea, user);
    }
}