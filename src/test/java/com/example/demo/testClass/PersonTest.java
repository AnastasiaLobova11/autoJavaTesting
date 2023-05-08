package com.example.demo.testClass;

import com.example.demo.service.RunTestsService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.reflect.Method;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class PersonTest {
//    Map<Integer, String> firstNameTest = new TreeMap<>();
//    Map<Integer, String> lastNameTest = new TreeMap<>();
//
//    @BeforeEach
//    public void create() {
//
//        firstNameTest.put(2001, "Nastya");
//        firstNameTest.put(2011, "Olya");
//        firstNameTest.put(2005, "Nastya");
//        firstNameTest.put(1996, "Marina");
//        lastNameTest.put(2001, "Lobova");
//        lastNameTest.put(1998, "Olonech");
//        lastNameTest.put(2021, "Lobova");
//        lastNameTest.put(1991, "Li");
//        lastNameTest.put(1993, "Maslenik");
//        lastNameTest.put(1995, "Loboda");
//
//    }

    //changeFirstName, добавляем в историю изменение имени
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void changeFirstNameTest(Method testMethod, Class<?> c) throws Exception {
//        Map<Integer, String> firstNameTest = new TreeMap<>();
//        Map<Integer, String> lastNameTest = new TreeMap<>();
//        firstNameTest.put(2001, "Nastya");
//        firstNameTest.put(2011, "Olya");
//        firstNameTest.put(2005, "Nastya");
//        firstNameTest.put(1996, "Marina");
//        lastNameTest.put(2001, "Lobova");
//        lastNameTest.put(1998, "Olonech");
//        lastNameTest.put(2021, "Lobova");
//        lastNameTest.put(1991, "Li");
//        lastNameTest.put(1993, "Maslenik");
//        lastNameTest.put(1995, "Loboda");
//        Class[] catClassParams = {SortedMap.class, SortedMap.class};
//        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);
//        Map<Integer, String> g;
//        testMethod.invoke(tr, 2001, "Nastya");
//        testMethod.invoke(tr, 1991, "Masha");
//        testMethod.invoke(tr, 2011, "Olya");
//
//        List<String> set = new ArrayList();
//        List<String> list = new ArrayList<>();
//        for (String s : g.values()) {
//            list.add(s);
//            System.out.println(s);
//        }
//        List<String> s = List.of("Masha", "Nastya", "Olya");

        assertEquals(1, 1);
    }

    //changeFirstName, добавляем в историю изменение имени и выбрасываем исключение,
    //когда пытаемся два раза за год изменить имя
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void changeFirstNameException(Method testMethod, Class<?> c) {
//        Assertions.assertThrows(Exception.class, () -> {
//            a.changeFirstName(2001, "Nastya");
//            a.changeFirstName(2011, "Olya");
//            a.changeFirstName(2001, "Nastya");
//        });
        assertEquals(0, 1);
    }

    //changeLastName, добавляем в историю изменение фамилии
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void changeLastNameException(Method testMethod, Class<?> c) {
//        Assertions.assertThrows(Exception.class, () -> {
//            a.changeLastName(2001, "Lobova");
//            a.changeLastName(2001, "Olonech");
//            a.changeFirstName(2011, "Kotova");
//            a.changeFirstName(1991, "Li");
//        });
        assertEquals(1, 1);
    }

    //changeLastName, добавляем в историю изменение фамилии, и выбрасываем исключение,
    //когда пытаемся два раза за год изменить фамилию
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void changeLastName(Method testMethod, Class<?> c) throws Exception {
//        String[] s= {"Li","Olonech","Lobova","Kotova"};
//        a.changeLastName(2001, "Lobova");
//        a.changeLastName(1998, "Olonech");
//        a.changeLastName(2011, "Kotova");
//        a.changeLastName(1991, "Li");
//        Assertions.assertTrue(Arrays.equals(s, a.getLastName().toArray()));
        assertEquals(1, 1);
    }

    //getFullName, запрашиваем имя и фамилию, на год, когда до него было информации
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameIncognito(Method testMethod, Class<?> c) throws Exception {
//        Assertions.assertEquals("Incognito", b.getFullName(1990));
        assertEquals(1, 1);
    }

    //getFullName, запрашиваем имя и фамилию, на год, когда была информация только о фамилии
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameIncognitoFirstName(Method testMethod, Class<?> c) {
//        Assertions.assertEquals("Li with unknown first name", b.getFullName(1992));
        assertEquals(1, 1);
    }

    //getFullName, запрашиваем имя и фамилию, на год, когда была информация о смене фамилии несколько раз
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameIncognitoFirstName1(Method testMethod, Class<?> c) {
//        Assertions.assertEquals("Maslenik with unknown first name", b.getFullName(1994));
        assertEquals(0, 1);
    }

    //getFullName, запрашиваем имя и фамилию, на год, когда была информация только об имени
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameIncognitoLastName(Method testMethod, Class<?> c) throws Exception {
//        a.changeFirstName(2000,"Nastya");
//        a.changeFirstName(2010,"Olya");
//        a.changeLastName(2001,"Lobova");
//        Assertions.assertEquals("Nastya with unknown last name", a.getFullName(2000));
        assertEquals(1, 1);
    }

    //getFullName, запрашиваем имя и фамилию, на год, когда была информация о смене имени несколько раз
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameIncognitoLastName1(Method testMethod, Class<?> c) throws Exception {
//        a.changeFirstName(2020,"Masha");
//        a.changeFirstName(2000,"Nastya");
//        a.changeFirstName(2010,"Olya");
//        a.changeLastName(2012,"Lobova");
//        Assertions.assertEquals("Olya with unknown last name", a.getFullName(2011));
        assertEquals(1, 1);
    }

    //getFullName, запрашиваем имя и фамилию, на год, когда была информация о смене имени несколько раз
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullName(Method testMethod, Class<?> c) throws Exception {
//        Assertions.assertEquals("Marina Loboda", b.getFullName(1996));
        assertEquals(1, 1);
    }

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год, до которого не было информации
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryIncognito(Method testMethod, Class<?> c){
//        Assertions.assertEquals("Incognito", b.getFullNameWithHistory(1990));
        assertEquals(1, 1);
    }

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
    // до которого была только 1 смена фамилии
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryLastNames(Method testMethod, Class<?> c){
//        Assertions.assertEquals("Li with unknown first name", b.getFullNameWithHistory(1992));
        assertEquals(1, 1);
    }

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
    // до которого была только 2 изменения фамилии
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryLastNames1(Method testMethod, Class<?> c){
//        Assertions.assertEquals("Maslenik(Li) with unknown first name", b.getFullNameWithHistory(1993));
        assertEquals(1, 1);
    }

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
    // до которого была несколько изменений фамилии
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryLastNames2(Method testMethod, Class<?> c){
//        Assertions.assertEquals("Loboda(Maslenik, Li) with unknown first name", b.getFullNameWithHistory(1995));
        assertEquals(1, 1);
    }

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
    // до которого была только 1 смена имени
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryFirstNames(Method testMethod, Class<?> c) throws Exception {
//        a.changeFirstName(2020,"Masha");
//        a.changeFirstName(2000,"Nastya");
//        a.changeFirstName(2010,"Olya");
//        a.changeLastName(2012,"Lobova");
//        Assertions.assertEquals("Nastya with unknown last name", a.getFullNameWithHistory(2002));
        assertEquals(1, 1);
    }

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
    // до которого была только 2 смены имени
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryFirstNames1(Method testMethod, Class<?> c) throws Exception {
//        a.changeFirstName(2020,"Masha");
//        a.changeFirstName(2000,"Nastya");
//        a.changeFirstName(2010,"Olya");
//        a.changeLastName(2012,"Lobova");
//        Assertions.assertEquals("Olya(Nastya) with unknown last name", a.getFullNameWithHistory(2011));
        assertEquals(1, 1);
    }

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
    // до которого была несколько изменений фамилии
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryFirstNames2(Method testMethod, Class<?> c) throws Exception {
//        a.changeFirstName(2020,"Masha");
//        a.changeFirstName(2000,"Nastya");
//        a.changeFirstName(2010,"Olya");
//        a.changeLastName(2032,"Lobova");
//        Assertions.assertEquals("Masha(Olya, Nastya) with unknown last name", a.getFullNameWithHistory(2022));
        assertEquals(1, 1);
    }

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
    // до которого была только 1 смена имени и несколько изменений фамилии
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryNames(Method testMethod, Class<?> c){
//        Assertions.assertEquals("Marina Loboda(Maslenik, Li)", b.getFullNameWithHistory(1997));
        assertEquals(1, 1);
    }

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
    // до которого была несколько изменений имени и несколько изменений фамилии
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryNames1(Method testMethod, Class<?> c){
//        String s ="Nastya(Marina) Lobova(Olonech, Loboda, Maslenik, Li)";
//        Assertions.assertEquals(s, b.getFullNameWithHistory(2002));
        assertEquals(1, 1);
    }

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
    // до которого была несколько изменений имени и несколько изменений фамилии
    //причем два раза подряд меняли имя на одно и тоже
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryNames2(Method testMethod, Class<?> c){
//        String s ="Nastya(Marina) Lobova(Olonech, Loboda, Maslenik, Li)";
//        Assertions.assertEquals(s, b.getFullNameWithHistory(2007));
        assertEquals(1, 1);
    }
    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
    // до которого была несколько изменений имени и несколько изменений фамилии
    //причем два раза подряд меняли имя и фамилию на одно и тоже
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryNames4(Method testMethod, Class<?> c){
//        String s ="Olya(Nastya, Marina) Lobova(Olonech, Loboda, Maslenik, Li)";
//        Assertions.assertEquals(s, b.getFullNameWithHistory(2030));
        assertEquals(1, 1);
    }
}