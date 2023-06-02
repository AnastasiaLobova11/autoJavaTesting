package com.example.demo.controller;

import com.example.demo.entity.TesTResult;
import com.example.demo.service.ViewService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Map;

public class TableTestController {
    private final ObservableList<TesTResult> testResult = FXCollections.observableArrayList();

    public static Map<String, Boolean> tesTResults;
    private final ViewService viewService = new ViewService(300., 400.);
    @FXML
    private TableView<TesTResult> tableResult;
    @FXML
    private TableColumn<TesTResult, Integer> idColumn;
    @FXML
    private TableColumn<TesTResult, String> nameColumn;
    @FXML
    private TableColumn<TesTResult, Boolean> resultColumn;

    /**
     * Инициализирует форму данными
     **/
    @FXML
    private void initialize() {
        initData();
        // устанавливаем тип и значение которое должно хранится в колонке
        idColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("testName"));
        resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));

        // заполняем таблицу данными
        tableResult.setItems(testResult);
    }

    /**
     * Подготавливает данные для таблицы
     **/
    private void initData() {
        int i = 0;
        for (Map.Entry<String, Boolean> entry : tesTResults.entrySet()) {
            testResult.add(new TesTResult(++i, entry.getKey(), entry.getValue()));
        }
    }

    @FXML
    protected void goBack(ActionEvent actionEvent) throws Exception {
        viewService.openNewView(actionEvent, "/com/example/demo/main-actions-view.fxml");
    }
}
