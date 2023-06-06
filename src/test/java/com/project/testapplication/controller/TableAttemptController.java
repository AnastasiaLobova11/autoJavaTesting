package com.project.testapplication.controller;

import com.project.testapplication.entity.TestAttempt;
import com.project.testapplication.entity.TestCase;
import com.project.testapplication.entity.User;
import com.project.testapplication.service.ViewService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

import static com.project.testapplication.entity.TestAttempt.COMPARE_BY_COUNT;

public class TableAttemptController {
    private final ViewService viewService = new ViewService(300., 400.);
    public static User user;
    public static TestCase testCase;
    private final ObservableList<TestAttempt> testAttempts = FXCollections.observableArrayList();
    @FXML
    private TableView<TestAttempt> tableAttempts;
    @FXML
    private TableColumn<TestAttempt, Integer> idColumn;
    @FXML
    private TableColumn<TestAttempt, Integer> passedColumn;
    @FXML
    private TableColumn<TestAttempt, Integer> failedColumn;

    /**
     * Инициализирует форму данными
     **/
    @FXML
    private void initialize() {

        initData();

        // устанавливаем тип и значение которое должно хранится в колонке
        idColumn.setCellValueFactory(new PropertyValueFactory<>("numberAttempt"));
        passedColumn.setCellValueFactory(new PropertyValueFactory<>("passed"));
        failedColumn.setCellValueFactory(new PropertyValueFactory<>("fail"));

        // заполняем таблицу данными
        tableAttempts.setItems(testAttempts);
    }

    /**
     * Подготавливает данные для таблицы
     **/
    private void initData() {
        List<TestAttempt> result = new ArrayList<>();

        for (TestAttempt testAttempt : user.getTestAttempt()) {
            if (testAttempt.getTestCase().getTitle().equals(testCase.getTitle())) {
                result.add(testAttempt);
            }
        }
        result.sort(COMPARE_BY_COUNT);
        testAttempts.addAll(result);
    }

    @FXML
    protected void goBack(ActionEvent actionEvent){
        viewService.openNewView(actionEvent, "/com/project/testapplication/main-actions-view.fxml");
    }
}
