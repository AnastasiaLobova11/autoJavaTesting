package com.example.demo.entity;

import javax.persistence.*;

@Entity(name="TestAttempt")
@Table(name="test_attempt")
public class TestAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column
    private Integer numberAttempt;
    @Column
    private Integer passed;
    @Column
    private Integer fail;

    public TestAttempt(){
    }

    public TestAttempt(Integer numberAttempt, Integer passed, Integer fail) {
        this.numberAttempt = numberAttempt;
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

}
