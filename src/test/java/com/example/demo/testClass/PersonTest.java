package com.example.demo.testClass;

import com.example.demo.service.RunTestsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class PersonTest {

    //changeFirstName, добавляем в историю изменение имени
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void changeFirstNameTest(Method testMethod, Class<?> c) throws Exception {
        String[] s = {"Masha", "Nastya", "Olya"};

        Map<Integer, String> firstNameTest = new TreeMap<>();
        Map<Integer, String> lastNameTest = new TreeMap<>();

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);

        Map<Integer, String> f = (Map<Integer, String>) testMethod.invoke(tr, 2001, "Nastya");
        f = (Map<Integer, String>) testMethod.invoke(tr, 1991, "Masha");
        f = (Map<Integer, String>) testMethod.invoke(tr, 2011, "Olya");
        List<String> res = new ArrayList(f.values());

        assertArrayEquals(s, res.toArray());

    }

    //changeFirstName, добавляем в историю изменение имени и выбрасываем исключение,
    //когда пытаемся два раза за год изменить имя
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void changeFirstNameException(Method testMethod, Class<?> c) throws Exception {
        Map<Integer, String> firstNameTest = new TreeMap<>();
        Map<Integer, String> lastNameTest = new TreeMap<>();

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);

        Assertions.assertThrows(Exception.class, () -> {
            testMethod.invoke(tr, 2001, "Nastya");
            testMethod.invoke(tr, 1994, "Olya");
            testMethod.invoke(tr, 2001, "Kate");
        });

    }

    //changeLastName, добавляем в историю изменение фамилии
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void changeLastNameException(Method testMethod, Class<?> c) throws Exception {
        Map<Integer, String> firstNameTest = new TreeMap<>();
        Map<Integer, String> lastNameTest = new TreeMap<>();

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);

        Assertions.assertThrows(Exception.class, () -> {
            testMethod.invoke(tr, 2001, "Lobova");
            testMethod.invoke(tr, 2001, "Olonech");
            testMethod.invoke(tr, 2011, "Kotova");
            testMethod.invoke(tr, 1991, "Li");

        });


    }

    //    //changeLastName, добавляем в историю изменение фамилии, и выбрасываем исключение,
//    //когда пытаемся два раза за год изменить фамилию
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void changeLastName(Method testMethod, Class<?> c) throws Exception {
        String[] s = {"Li", "Olonech", "Lobova", "Kotova"};

        Map<Integer, String> firstNameTest = new TreeMap<>();
        Map<Integer, String> lastNameTest = new TreeMap<>();

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);
        Map<Integer, String> f = (Map<Integer, String>) testMethod.invoke(tr, 2001, "Lobova");
        f = (Map<Integer, String>) testMethod.invoke(tr, 1998, "Olonech");
        f = (Map<Integer, String>) testMethod.invoke(tr, 2011, "Kotova");
        f = (Map<Integer, String>) testMethod.invoke(tr, 1991, "Li");

        List<String> res = new ArrayList(f.values());

        assertArrayEquals(s, res.toArray());
    }
//
//    //getFullName, запрашиваем имя и фамилию, на год, когда до него было информации
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    void getFullNameIncognito(Method testMethod, Class<?> c) throws Exception {
////        Assertions.assertEquals("Incognito", b.getFullName(1990));
//        assertEquals(1, 1);
//    }
//
//    //getFullName, запрашиваем имя и фамилию, на год, когда была информация только о фамилии
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    void getFullNameIncognitoFirstName(Method testMethod, Class<?> c) {
////        Assertions.assertEquals("Li with unknown first name", b.getFullName(1992));
//        assertEquals(1, 1);
//    }
//
//    //getFullName, запрашиваем имя и фамилию, на год, когда была информация о смене фамилии несколько раз
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    void getFullNameIncognitoFirstName1(Method testMethod, Class<?> c) {
////        Assertions.assertEquals("Maslenik with unknown first name", b.getFullName(1994));
//        assertEquals(0, 1);
//    }
//
//    //getFullName, запрашиваем имя и фамилию, на год, когда была информация только об имени
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    void getFullNameIncognitoLastName(Method testMethod, Class<?> c) throws Exception {
////        a.changeFirstName(2000,"Nastya");
////        a.changeFirstName(2010,"Olya");
////        a.changeLastName(2001,"Lobova");
////        Assertions.assertEquals("Nastya with unknown last name", a.getFullName(2000));
//        assertEquals(1, 1);
//    }
//
//    //getFullName, запрашиваем имя и фамилию, на год, когда была информация о смене имени несколько раз
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    void getFullNameIncognitoLastName1(Method testMethod, Class<?> c) throws Exception {
////        a.changeFirstName(2020,"Masha");
////        a.changeFirstName(2000,"Nastya");
////        a.changeFirstName(2010,"Olya");
////        a.changeLastName(2012,"Lobova");
////        Assertions.assertEquals("Olya with unknown last name", a.getFullName(2011));
//        assertEquals(1, 1);
//    }
//
//    //getFullName, запрашиваем имя и фамилию, на год, когда была информация о смене имени несколько раз
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    void getFullName(Method testMethod, Class<?> c) throws Exception {
////        Assertions.assertEquals("Marina Loboda", b.getFullName(1996));
//        assertEquals(1, 1);
//    }
//
//    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год, до которого не было информации
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    void getFullNameWithHistoryIncognito(Method testMethod, Class<?> c){
////        Assertions.assertEquals("Incognito", b.getFullNameWithHistory(1990));
//        assertEquals(1, 1);
//    }
//
//    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
//    // до которого была только 1 смена фамилии
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    void getFullNameWithHistoryLastNames(Method testMethod, Class<?> c){
////        Assertions.assertEquals("Li with unknown first name", b.getFullNameWithHistory(1992));
//        assertEquals(1, 1);
//    }
//
//    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
//    // до которого была только 2 изменения фамилии
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    void getFullNameWithHistoryLastNames1(Method testMethod, Class<?> c){
////        Assertions.assertEquals("Maslenik(Li) with unknown first name", b.getFullNameWithHistory(1993));
//        assertEquals(1, 1);
//    }
//
//    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
//    // до которого была несколько изменений фамилии
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    void getFullNameWithHistoryLastNames2(Method testMethod, Class<?> c){
////        Assertions.assertEquals("Loboda(Maslenik, Li) with unknown first name", b.getFullNameWithHistory(1995));
//        assertEquals(1, 1);
//    }

    //    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
//    // до которого была только 1 смена имени
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryFirstNames(Method testMethod, Class<?> c) throws Exception {
        String[] s = {"Li", "Olonech", "Lobova", "Kotova"};

        Map<Integer, String> firstNameTest = new TreeMap<>();
        firstNameTest.put(2020, "Masha");
        firstNameTest.put(2000, "Nastya");
        firstNameTest.put(2010, "Olya");
        firstNameTest.put(2012, "Lobova");
        Map<Integer, String> lastNameTest = new TreeMap<>();

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);
        String res = (String) testMethod.invoke(tr, 2002);
        assertEquals("Nastya with unknown last name", res);

    }
//
//    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
//    // до которого была только 2 смены имени
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    void getFullNameWithHistoryFirstNames1(Method testMethod, Class<?> c) throws Exception {
////        a.changeFirstName(2020,"Masha");
////        a.changeFirstName(2000,"Nastya");
////        a.changeFirstName(2010,"Olya");
////        a.changeLastName(2012,"Lobova");
////        Assertions.assertEquals("Olya(Nastya) with unknown last name", a.getFullNameWithHistory(2011));
//        assertEquals(1, 1);
//    }
//
//    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
//    // до которого была несколько изменений фамилии
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    void getFullNameWithHistoryFirstNames2(Method testMethod, Class<?> c) throws Exception {
////        a.changeFirstName(2020,"Masha");
////        a.changeFirstName(2000,"Nastya");
////        a.changeFirstName(2010,"Olya");
////        a.changeLastName(2032,"Lobova");
////        Assertions.assertEquals("Masha(Olya, Nastya) with unknown last name", a.getFullNameWithHistory(2022));
//        assertEquals(1, 1);
//    }
//
//    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
//    // до которого была только 1 смена имени и несколько изменений фамилии
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    void getFullNameWithHistoryNames(Method testMethod, Class<?> c){
////        Assertions.assertEquals("Marina Loboda(Maslenik, Li)", b.getFullNameWithHistory(1997));
//        assertEquals(1, 1);
//    }
//
//    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
//    // до которого была несколько изменений имени и несколько изменений фамилии
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    void getFullNameWithHistoryNames1(Method testMethod, Class<?> c){
////        String s ="Nastya(Marina) Lobova(Olonech, Loboda, Maslenik, Li)";
////        Assertions.assertEquals(s, b.getFullNameWithHistory(2002));
//        assertEquals(1, 1);
//    }
//
//    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
//    // до которого была несколько изменений имени и несколько изменений фамилии
//    //причем два раза подряд меняли имя на одно и тоже
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    void getFullNameWithHistoryNames2(Method testMethod, Class<?> c){
////        String s ="Nastya(Marina) Lobova(Olonech, Loboda, Maslenik, Li)";
////        Assertions.assertEquals(s, b.getFullNameWithHistory(2007));
//        assertEquals(1, 1);
//    }
//    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
//    // до которого была несколько изменений имени и несколько изменений фамилии
//    //причем два раза подряд меняли имя и фамилию на одно и тоже
//    @ParameterizedTest
//    @ArgumentsSource(RunTestsService.class)
//    void getFullNameWithHistoryNames4(Method testMethod, Class<?> c){
////        String s ="Olya(Nastya, Marina) Lobova(Olonech, Loboda, Maslenik, Li)";
////        Assertions.assertEquals(s, b.getFullNameWithHistory(2030));
//        assertEquals(1, 1);
//    }
}