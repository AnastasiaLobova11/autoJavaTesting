package com.example.demo.controller;

import com.example.demo.entity.TestAttempt;
import com.example.demo.entity.TestCase;
import com.example.demo.entity.User;
import com.example.demo.service.ViewService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.entity.TestAttempt.COMPARE_BY_COUNT;

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
    protected void goBack(ActionEvent actionEvent) throws Exception {
        viewService.openNewView(actionEvent, "/com/example/demo/main-actions-view.fxml");
    }
}
