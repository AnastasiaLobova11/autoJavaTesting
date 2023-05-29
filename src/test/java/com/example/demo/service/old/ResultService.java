package com.example.demo.service.old;

import com.example.demo.entity.TestAttempt;
import com.example.demo.entity.User;
import com.example.demo.entity.UserDaoImpl;
import javafx.scene.control.TextArea;

import java.util.List;
import java.util.Map;

public class ResultService {

    private User currUser;
    private final UserDaoImpl userDao = new UserDaoImpl();

    public void setUser(User user) {
        currUser = user;
    }

    public void createResultTests(TextArea textArea, Map<String, Boolean> resultTests) {

        int pos = 0, neg = 0;
        for (Map.Entry<String, Boolean> entry : resultTests.entrySet()) {
            if (entry.getValue())
                pos++;
            else
                neg++;

        }
        textArea.appendText(" WAS SUCCESSFUL " + pos + "\n");
        textArea.appendText(" WAS FAILED " + neg + "\n");
        textArea.appendText("--------------------------------------------------- \n");

        //userDao.update(currUser, pos, neg);
    }

    public void getAllInfoAboutTests(TextArea textArea, Map<String, Boolean> resultTests) {
        for (Map.Entry<String, Boolean> entry : resultTests.entrySet())
            textArea.appendText(entry.getKey() + ": " + entry.getValue() + "\n");
    }

    @Deprecated
    public void getInfoAboutAttempts(TextArea textArea, User user) {
        List<TestAttempt> testAttempts = user.getTestAttempt();
        textArea.clear();
        textArea.appendText(String.format("%10s%10s%10s\n", "Num", "Passed", "Fail"));
        textArea.appendText("________________________________\n");
        for (TestAttempt attempt : testAttempts) {
            textArea.appendText(String.format("%10s%12s%15s\n",
                    attempt.getNumberAttempt(), attempt.getPassed(), attempt.getFail()));
        }
    }
}
