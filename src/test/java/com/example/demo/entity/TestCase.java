package com.example.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test_case")
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column(unique = true)
    private String title;
    @Column
    private String className;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "testCase", fetch = FetchType.EAGER)
    private List<TestAttempt> testAttempts = new ArrayList<>();

    public TestCase() {
    }

    public TestCase(String title, String className) {
        this.title = title;
        this.className = className;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<TestAttempt> getTestAttempts() {
        return testAttempts;
    }

    public void setTestAttempts(List<TestAttempt> testAttempts) {
        this.testAttempts = testAttempts;
    }

    @Override
    public String toString() {
        return (this.title);
    }
}
