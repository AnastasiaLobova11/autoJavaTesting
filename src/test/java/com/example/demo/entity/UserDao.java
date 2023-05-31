package com.example.demo.entity;

import java.util.List;

public interface UserDao<T> {

    List<T> getAllByParameters(Integer course, Integer groupe, String surname);

    void save(T t);

    void update(T t, TestCase testCase, Integer pos, Integer neg);

}
