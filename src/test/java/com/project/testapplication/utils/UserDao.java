package com.project.testapplication.utils;

import com.project.testapplication.entity.TestCase;

import java.util.List;

public interface UserDao<T> {

    List<T> getByParameters(Integer course, Integer groupe, String surname);

    void save(T t);

    void update(T t, TestCase testCase, Integer pos, Integer neg);

}
