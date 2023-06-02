package com.example.demo.utils;

import com.example.demo.entity.TestCase;

import java.util.List;

public interface TestCaseDao {
     void save(TestCase testCase);
     void delete(TestCase testCase);
     List<TestCase> getAll();
}