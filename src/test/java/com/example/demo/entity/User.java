package com.example.demo.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
@Table(name = "my_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(nullable = false)
    private Integer course;

    @Column(nullable = false)
    private Integer groupa;

    @Column(nullable = false)
    private String surname;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<TestAttempt> testAttempt = new ArrayList<>();

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public Integer getGroupa() {
        return groupa;
    }

    public void setGroupa(Integer groupa) {
        this.groupa = groupa;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }


    public List<TestAttempt> getTestAttempt() {
        return testAttempt;
    }

    public void setTestAttempt(List<TestAttempt> testAttempt) {
        this.testAttempt = testAttempt;
    }

}
