package com.project.testapplication.entity;

import javax.persistence.*;
import java.util.Comparator;

@Entity(name = "TestAttempt")
@Table(name = "test_attempt")
public class TestAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private Integer numberAttempt;
    @ManyToOne
    @JoinColumn(name = "test_case")
    private TestCase testCase;
    @Column
    private Integer passed;
    @Column
    private Integer fail;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public TestAttempt() {
    }

    public TestAttempt(Integer numberAttempt, TestCase testCase, Integer passed, Integer fail) {
        this.numberAttempt = numberAttempt;
        this.testCase = testCase;
        this.passed = passed;
        this.fail = fail;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getNumberAttempt() {
        return numberAttempt;
    }

    public void setNumberAttempt(Integer numberAttempt) {
        this.numberAttempt = numberAttempt;
    }

    public TestCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestCase testCase) {
        this.testCase = testCase;
    }

    public Integer getPassed() {
        return passed;
    }

    public void setPassed(Integer passed) {
        this.passed = passed;
    }

    public Integer getFail() {
        return fail;
    }

    public void setFail(Integer fail) {
        this.fail = fail;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static final Comparator<TestAttempt> COMPARE_BY_COUNT = Comparator.comparingInt(lhs -> lhs.numberAttempt);
}
