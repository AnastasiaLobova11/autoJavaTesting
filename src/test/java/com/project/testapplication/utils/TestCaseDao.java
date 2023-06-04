package com.project.testapplication.utils;

import com.project.testapplication.entity.TestCase;

import java.util.List;

public interface TestCaseDao {
     void save(TestCase testCase);
     void delete(TestCase testCase);
     List<TestCase> getAll();
}