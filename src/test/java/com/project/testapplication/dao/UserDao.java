package com.project.testapplication.dao;

import com.project.testapplication.entity.TestCase;

import java.util.List;
import java.util.Optional;

public interface UserDao<T> {

    Optional<T> getByParameters(Integer course, Integer groupe, String surname);

    void save(T t);

    void update(T t, TestCase testCase, Integer pos, Integer neg);

}
