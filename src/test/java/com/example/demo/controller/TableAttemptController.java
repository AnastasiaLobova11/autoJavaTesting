package com.example.demo.controller;

import com.example.demo.entity.TestAttempt;
import com.example.demo.entity.User;
import com.example.demo.service.ViewService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class TableAttemptController {

    public static User user;
    private ObservableList<TestAttempt> testAttempts = FXCollections.observableArrayList();
    private final ViewService viewService = new ViewService(300., 400.);
    @FXML
    private TableView<TestAttempt> tableAttempts;
    @FXML
    private TableColumn<TestAttempt, Integer> idColumn;
    @FXML
    private TableColumn<TestAttempt, Integer> passedColumn;
    @FXML
    private TableColumn<TestAttempt, Integer> failedColumn;

    // инициализируем форму данными
    @FXML
    private void initialize() {
        initData();
        // устанавливаем тип и значение которое должно хранится в колонке
        idColumn.setCellValueFactory(new PropertyValueFactory<TestAttempt, Integer>("numberAttempt"));
        passedColumn.setCellValueFactory(new PropertyValueFactory<TestAttempt, Integer>("passed"));
        failedColumn.setCellValueFactory(new PropertyValueFactory<TestAttempt, Integer>("fail"));

        // заполняем таблицу данными
        tableAttempts.setItems(testAttempts);
    }

    // подготавливаем данные для таблицы
    // вы можете получать их с базы данных
    private void initData() {

        List<TestAttempt> list =user.getTestAttempt();
        testAttempts.addAll(list);
    }

    @FXML
    protected void goBack(ActionEvent actionEvent) throws Exception {

        viewService.openNewView(actionEvent, "/com/example/demo/main-actions-view.fxml");

    }
}
