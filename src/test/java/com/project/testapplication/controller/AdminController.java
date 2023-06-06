package com.project.testapplication.controller;

import com.project.testapplication.entity.TestCase;
import com.project.testapplication.dao.TestCaseDao;
import com.project.testapplication.dao.TestCaseDaoImpl;
import com.project.testapplication.service.ViewService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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
    protected void addTestCase(ActionEvent actionEvent){
        viewService.newView(350., 195., "/com/project/testapplication/add-test-case-view.fxml", "");
    }

    @FXML
    protected void deleteTestCase() {
        List<TestCase> caseList = testCaseTable.getSelectionModel().getSelectedItems();
        caseList.forEach((it) -> testCaseDao.delete(it));
        testCaseTable.getItems().removeAll(caseList);
    }

    @FXML
    protected void goBack(ActionEvent actionEvent){
        viewService.openNewView(actionEvent, "/com/project/testapplication/auth-view.fxml");
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
