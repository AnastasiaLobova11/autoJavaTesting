package com.project.testapplication.service;

import com.project.testapplication.entity.TestCase;
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

    /**
     * Получает тестовый класс и запускает процесс тестирования
     **/
    public Map<String, Boolean> runAllTestClassesFromJar(Class<?> c, TestCase selectedTask) throws Exception {

        Class<?> currTestClass;

        currTestClass = Class.forName("com.project.testapplication.testClass."
                + selectedTask.getClassName() + "Test");

        return runTestForAllMethods(c, currTestClass);
    }

    /**
     * Получаем для каждого метода тестирующие случаи.
     * Запускаем их.
     **/
    public Map<String, Boolean> runTestForAllMethods(Class<?> currClass, Class<?> currTestClass) {
        List<Method> needTestMethods;
        Map<String, Boolean> resultTests = new HashMap<>();
        testClass = currClass;
        Method[] classMethods = currClass.getDeclaredMethods();
        for (Method method : classMethods) {
            //Ищем для текущего метода тестовые случаи
            needTestMethods = getAllTestCaseForMethod(currTestClass.getDeclaredMethods(), method);
            if (!needTestMethods.isEmpty()) {
                currMethodFromClass = method;
                //Вызов тестов для текущего метода
                resultTests.putAll(runOneMethodTests(currTestClass, needTestMethods));
                needTestMethods.clear();
            }
        }
        return resultTests;
    }

    /**
     * Получаем все тестовые методы для текущего метода из класса
     **/
    public List<Method> getAllTestCaseForMethod(Method[] allTestMethods, Method classMethod) {
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
        RunJUnit5TestsFromJava runJUnit5TestsFromJava = new RunJUnit5TestsFromJava(testClass.getName());
        for (Method testMethod : testMethodList) {
            runJUnit5TestsFromJava.setMethodName(testMethod.getName());

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
