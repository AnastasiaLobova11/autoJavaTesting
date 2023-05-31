package com.example.demo.controller;

import com.example.demo.entity.TestCase;
import com.example.demo.entity.TestCaseDao;
import com.example.demo.entity.TestCaseDaoImpl;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddTestCaseController {

    TestCaseDao testCaseDao = new TestCaseDaoImpl();
    @FXML
    TextField title;
    @FXML
    TextField testClass;

    public void saveTestCase() {
        if (!title.getText().isEmpty() && !testClass.getText().isEmpty()) {
            TestCase testCase = new TestCase(title.getText(), testClass.getText());
            testCaseDao.save(testCase);
            title.setText("");
            testClass.setText("");
        }
    }
}
