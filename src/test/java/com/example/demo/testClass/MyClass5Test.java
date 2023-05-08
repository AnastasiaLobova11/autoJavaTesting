package com.example.demo.testClass;

import com.example.demo.service.RunTestsService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyClass5Test {

    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void isExistTrueTest(Method testMethod, Class<?> c) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Class[] catClassParams = {int.class, int.class, int.class};
        Object tr = c.getConstructor(catClassParams).newInstance(6,6,6);
        Boolean f = (Boolean) testMethod.invoke(tr);
        assertEquals(false,f);
    }

    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void isExistFalseTest(Method testMethod, Class<?> c) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Class[] catClassParams = {int.class, int.class, int.class};
        Object tr = c.getConstructor(catClassParams).newInstance(76,6,6);
        Boolean f = (Boolean) testMethod.invoke(tr);
        assertEquals(false,f);
    }

//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    public void isRBTrueTest(Method testMethod, Class<?> c) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
//        Class[] catClassParams = {int.class, int.class, int.class};
//        Object tr = c.getConstructor(catClassParams).newInstance(5,6,6);
//        Boolean f = (Boolean) testMethod.invoke(tr);
//        assertEquals(true,f);
//    }
//
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    public void isRBFalseTest(Method testMethod, Class<?> c) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
//        Class[] catClassParams = {int.class, int.class, int.class};
//        Object tr = c.getConstructor(catClassParams).newInstance(5,4,6);
//        Boolean f = (Boolean) testMethod.invoke(tr);
//        assertEquals(false,f);
//    }
//
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    public void isRSTrueTest(Method testMethod, Class<?> c) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
//        Class[] catClassParams = {int.class, int.class, int.class};
//        Object tr = c.getConstructor(catClassParams).newInstance(6,6,6);
//        Boolean f = (Boolean) testMethod.invoke(tr);
//        assertEquals(true,f);
//    }
//
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    public void isRSFalseTest(Method testMethod, Class<?> c) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
//        Class[] catClassParams = {int.class, int.class, int.class};
//        Object tr = c.getConstructor(catClassParams).newInstance(5,4,6);
//        Boolean f = (Boolean) testMethod.invoke(tr);
//        assertEquals(false,f);
//    }
//
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    public void squareTrueTest(Method testMethod, Class<?> c) throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
//        Class[] catClassParams = {int.class, int.class, int.class};
//        Object tr = c.getConstructor(catClassParams).newInstance(3,4,5);
//        Double f = (Double) testMethod.invoke(tr);
//        assertTrue(Math.abs(f - 2.5) < 0.1);
//    }

}