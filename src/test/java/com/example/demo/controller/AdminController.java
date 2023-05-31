package com.example.demo.controller;

import com.example.demo.entity.TestCase;
import com.example.demo.entity.TestCaseDao;
import com.example.demo.entity.TestCaseDaoImpl;
import com.example.demo.service.ViewService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.util.List;

public class AdminController {
    private final ObservableList<TestCase> testCases = FXCollections.observableArrayList();
    TestCaseDao testCaseDao = new TestCaseDaoImpl();
    ViewService viewService = new ViewService(300., 400.);
    @FXML
    TableView<TestCase> testCaseTable;
    @FXML
    private TableColumn<TestCase, String> title;
    @FXML
    private TableColumn<TestCase, String> testClass;

    @FXML
    protected void addTestCase(ActionEvent actionEvent) throws IOException {
        viewService.newView(350., 195., "/com/example/demo/add-test-case-view.fxml", "");
    }

    @FXML
    protected void deleteTestCase() {
        List<TestCase> caseList = testCaseTable.getSelectionModel().getSelectedItems();
        caseList.forEach((it) -> testCaseDao.delete(it));
        testCaseTable.getItems().removeAll(caseList);
    }

    @FXML
    protected void goBack(ActionEvent actionEvent) throws Exception {
        viewService.openNewView(actionEvent, "/com/example/demo/auth-view.fxml");
    }

    @FXML
    private void initialize() {
        initData();
        // устанавливаем тип и значение которое должно хранится в колонке
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        testClass.setCellValueFactory(new PropertyValueFactory<>("className"));

        // заполняем таблицу данными
        testCaseTable.setItems(testCases);
    }

    // подготавливаем данные для таблицы
    // вы можете получать их с базы данных
    private void initData() {
        //TODO may be null
        List<TestCase> list = testCaseDao.getAll();
        if (list != null && !list.isEmpty())
            testCases.addAll(list);
    }

    public void refresh() {
        testCaseTable.getItems().clear();
        initialize();
    }
}
