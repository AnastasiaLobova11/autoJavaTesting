package com.example.demo.entity;

import java.util.List;

public interface TestCaseDao {
     void save(TestCase testCase);

     void delete(TestCase testCase);
     List<TestCase> getAll();
}