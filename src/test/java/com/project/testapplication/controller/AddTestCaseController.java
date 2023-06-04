package com.project.testapplication.controller;

import com.project.testapplication.entity.TestCase;
import com.project.testapplication.utils.TestCaseDao;
import com.project.testapplication.utils.TestCaseDaoImpl;
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
