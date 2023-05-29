package com.example.demo.service;

import com.example.demo.testClass.BusesTest;
import com.example.demo.testClass.LettersTest;
import com.example.demo.testClass.PersonTest;
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

    public Map<String, Boolean> runAllTestClassesFromJar(Class<?> c) throws Exception {
        Class<?> currTestClass;

        //потом так же добавить тут все тестовые классы
        switch (c.getSimpleName()) {
            //case "TriangleImpl": {
            case "PersonImpl": {
                currTestClass = PersonTest.class;
                break;
            }
            case "LettersImpl": {
                currTestClass = LettersTest.class;
                break;
            }
            case "BusesImpl": {
                currTestClass = BusesTest.class;
                break;
            }
            default: {
                throw new Exception("This class " + c.getSimpleName() + " doesn't include in tested classes!");
            }
        }

        Map<String, Boolean> resultTests = runTestClassFromJar(c, currTestClass);

        return resultTests;
    }

    public Map<String, Boolean> runTestClassFromJar(Class<?> currClass, Class<?> currTestClass) throws Exception {
        Map<String, Boolean> resultTests = new HashMap<>();

        //ВАЖНО! сказать чтобы все было в связке с реализацией определенных интерфейсов
        //и чтобы названия классов, имплементирующих интерфейс, заканчивались на Impl
        //так я буду определять что это те классы которые мне надо дергать

        testClass = currClass;

        //надо получать методы класса и передавать в статическую переменную
        Method[] classMethods = currClass.getDeclaredMethods();
        List<Method> needTestMethods;
        Map<String, Boolean> resultForTestMethod;

        for (Method method : classMethods) {
            //ищем для текущего метода тесты
            needTestMethods = getAllTestMethodFromClass(currTestClass.getDeclaredMethods(), method);

            if (!needTestMethods.isEmpty()) {

                currMethodFromClass = method;
                //currClass.getMethod(method.getName(), Integer.class, String.class);


                //currMethodFromClass = currClass.getMethod(method.getName());
                //вызов тестов для текущего метода
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


    //для передачи параметров в тесты
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
        return Stream.of(Arguments.of(currMethodFromClass, testClass));
    }

}
