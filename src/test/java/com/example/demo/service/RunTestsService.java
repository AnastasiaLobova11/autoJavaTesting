package com.example.demo.service;

import com.example.demo.entity.TestCase;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class RunTestsService implements ArgumentsProvider {

    static Class<?> testClass;
    static Method currMethodFromClass;

    public Map<String, Boolean> runAllTestClassesFromJar(Class<?> c, TestCase selectedTask) throws Exception {

        Class<?> currTestClass;

        currTestClass = Class.forName("com.example.demo.testClass."
                + selectedTask.getClassName() + "Test");

        return runTestClassFromJar(c, currTestClass);
    }

    public Map<String, Boolean> runTestClassFromJar(Class<?> currClass, Class<?> currTestClass) {
        List<Method> needTestMethods;
        Map<String, Boolean> resultForTestMethod;
        Map<String, Boolean> resultTests = new HashMap<>();

        testClass = currClass;

        Method[] classMethods = currClass.getDeclaredMethods();

        for (Method method : classMethods) {

            //Ищем для текущего метода тестовые случаи
            needTestMethods = getAllTestMethodFromClass(currTestClass.getDeclaredMethods(), method);

            if (!needTestMethods.isEmpty()) {

                currMethodFromClass = method;

                //Вызов тестов для текущего метода
                resultForTestMethod = RunTestsService.runOneMethodTests(currTestClass, needTestMethods);

                resultTests.putAll(resultForTestMethod);
                needTestMethods.clear();
            }
        }
        return resultTests;
    }

    //получаем все тестовые методы для текущего метода из класса
    public List<Method> getAllTestMethodFromClass(Method[] allTestMethods, Method classMethod) {
        List<Method> needTestMethods = new ArrayList<>();

        for (Method test : allTestMethods) {

            String nameTestMethodShort = test.getName().substring(0, test.getName().indexOf("Test"));
            if (nameTestMethodShort.equals(classMethod.getName())) {
                needTestMethods.add(test);
            }
        }
        return needTestMethods;
    }

    public static Map<String, Boolean> runOneMethodTests(Class<?> testClass, List<Method> testMethodList) {
        Map<String, Boolean> resultTests = new HashMap<>();

        for (Method testMethod : testMethodList) {
            RunJUnit5TestsFromJava runJUnit5TestsFromJava = new RunJUnit5TestsFromJava(testMethod.getName(), testClass.getName());

            runJUnit5TestsFromJava.runOne();
            TestExecutionSummary summary = runJUnit5TestsFromJava.listener.getSummary();

            resultTests.put(testMethod.getName(), (int) summary.getTestsSucceededCount() == 1);
        }
        return resultTests;
    }

    /**
     * Используется для передачи параметров в тесты
     **/
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(Arguments.of(currMethodFromClass, testClass));
    }

}
