package com.example.demo.entity;

public class TesTResult {
    private Boolean result;
    private String testName;
    private Integer number;

    public TesTResult(Integer number, String testName, Boolean result) {
        this.result = result;
        this.testName = testName;
        this.number = number;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }
}
