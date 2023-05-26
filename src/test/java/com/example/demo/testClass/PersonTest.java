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
    void changeFirstNameTestException(Method testMethod, Class<?> c) throws Exception {
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
    void changeLastNameTestException(Method testMethod, Class<?> c) throws Exception {
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

    //changeLastName, добавляем в историю изменение фамилии, и выбрасываем исключение,
    //когда пытаемся два раза за год изменить фамилию
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void changeLastNameTest(Method testMethod, Class<?> c) throws Exception {
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

    //getFullName, запрашиваем имя и фамилию, на год, когда до него было информации
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameTestIncognito(Method testMethod, Class<?> c) throws Exception {

        Map<Integer, String> firstNameTest = new TreeMap<>();
        firstNameTest.put(2020, "Masha");
        firstNameTest.put(2010, "Olya");
        firstNameTest.put(2012, "Kate");
        Map<Integer, String> lastNameTest = new TreeMap<>();
        lastNameTest.put(2023, "Lobova");
        lastNameTest.put(2001, "Ivanova");

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);

        String res = testMethod.invoke(tr, 2000).toString();
        assertEquals("Incognito", res);
    }


    //getFullName, запрашиваем имя и фамилию, на год, когда была информация только о фамилии
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameTestIncognitoFirstName(Method testMethod, Class<?> c) throws Exception {

        Map<Integer, String> firstNameTest = new TreeMap<>();
        firstNameTest.put(2020, "Masha");
        firstNameTest.put(2010, "Olya");
        firstNameTest.put(2012, "Kate");
        Map<Integer, String> lastNameTest = new TreeMap<>();
        lastNameTest.put(2023, "Lobova");
        lastNameTest.put(2001, "Ivanova");

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);

        String res = testMethod.invoke(tr, 2002).toString();
        assertEquals("Ivanova with unknown first name", res);
    }

    //getFullName, запрашиваем имя и фамилию, на год, когда была информация о смене фамилии несколько раз
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameTestIncognitoFirstName1(Method testMethod, Class<?> c) throws Exception {
        Map<Integer, String> firstNameTest = new TreeMap<>();
        firstNameTest.put(2020, "Masha");
        firstNameTest.put(2011, "Olya");
        firstNameTest.put(2012, "Kate");
        Map<Integer, String> lastNameTest = new TreeMap<>();
        lastNameTest.put(2023, "Lobova");
        lastNameTest.put(2001, "Ivanova");
        lastNameTest.put(2010, "Maslenik");

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);

        String res = testMethod.invoke(tr, 2010).toString();
        assertEquals("Maslenik with unknown first name", res);
    }

    //getFullName, запрашиваем имя и фамилию, на год, когда была информация только об имени
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameTestIncognitoLastName(Method testMethod, Class<?> c) throws Exception {
        Map<Integer, String> firstNameTest = new TreeMap<>();
        firstNameTest.put(2020, "Masha");
        firstNameTest.put(1999, "Olya");
        firstNameTest.put(2012, "Kate");
        Map<Integer, String> lastNameTest = new TreeMap<>();
        lastNameTest.put(2023, "Lobova");
        lastNameTest.put(2001, "Ivanova");
        lastNameTest.put(2010, "Maslenik");

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);

        String res = testMethod.invoke(tr, 2000).toString();
        assertEquals("Olya with unknown last name", res);
    }

    //getFullName, запрашиваем имя и фамилию, на год, когда была информация о смене имени несколько раз
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameTestIncognitoLastName1(Method testMethod, Class<?> c) throws Exception {
        Map<Integer, String> firstNameTest = new TreeMap<>();
        firstNameTest.put(2020, "Masha");
        firstNameTest.put(1999, "Olya");
        firstNameTest.put(2012, "Kate");
        firstNameTest.put(1997, "Nastya");
        Map<Integer, String> lastNameTest = new TreeMap<>();
        lastNameTest.put(2023, "Lobova");
        lastNameTest.put(2001, "Ivanova");
        lastNameTest.put(2010, "Maslenik");

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);

        String res = testMethod.invoke(tr, 2000).toString();
        assertEquals("Olya with unknown last name", res);
    }

    //getFullName, запрашиваем имя и фамилию, на год, когда была информация о смене имени несколько раз
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameTest(Method testMethod, Class<?> c) throws Exception {
        Map<Integer, String> firstNameTest = new TreeMap<>();
        firstNameTest.put(2020, "Masha");
        firstNameTest.put(1999, "Olya");
        firstNameTest.put(2012, "Kate");
        firstNameTest.put(1997, "Nastya");
        Map<Integer, String> lastNameTest = new TreeMap<>();
        lastNameTest.put(2023, "Lobova");
        lastNameTest.put(2001, "Ivanova");
        lastNameTest.put(2010, "Maslenik");

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);

        String res = testMethod.invoke(tr, 2013).toString();
        assertEquals("Kate Maslenik", res);
    }

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год, до которого не было информации
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryTestIncognito(Method testMethod, Class<?> c) throws Exception {
        Map<Integer, String> firstNameTest = new TreeMap<>();
        firstNameTest.put(2020, "Masha");
        firstNameTest.put(1999, "Olya");
        firstNameTest.put(2012, "Kate");
        firstNameTest.put(1997, "Nastya");
        Map<Integer, String> lastNameTest = new TreeMap<>();
        lastNameTest.put(2023, "Lobova");
        lastNameTest.put(2001, "Ivanova");
        lastNameTest.put(2010, "Maslenik");

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);

        String res = testMethod.invoke(tr, 1995).toString();
        assertEquals("Incognito", res);
    }

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
    // до которого была только 1 смена фамилии
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryTestLastNames(Method testMethod, Class<?> c) throws Exception {
        Map<Integer, String> firstNameTest = new TreeMap<>();
        firstNameTest.put(2020, "Masha");
        firstNameTest.put(1999, "Olya");
        firstNameTest.put(2012, "Kate");
        firstNameTest.put(1997, "Nastya");
        Map<Integer, String> lastNameTest = new TreeMap<>();
        lastNameTest.put(2023, "Lobova");
        lastNameTest.put(1990, "Li");
        lastNameTest.put(2010, "Maslenik");

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);

        String res = testMethod.invoke(tr, 1992).toString();
        assertEquals("Li with unknown first name", res);
    }

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
    // до которого была только 2 изменения фамилии
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryTestLastNames1(Method testMethod, Class<?> c) throws Exception {
        Map<Integer, String> firstNameTest = new TreeMap<>();
        firstNameTest.put(2020, "Masha");
        firstNameTest.put(2012, "Kate");
        firstNameTest.put(2023, "Nastya");
        Map<Integer, String> lastNameTest = new TreeMap<>();
        lastNameTest.put(2023, "Lobova");
        lastNameTest.put(1990, "Li");
        lastNameTest.put(2010, "Maslenik");

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);

        String res = testMethod.invoke(tr, 2010).toString();
        assertEquals("Maslenik(Li) with unknown first name", res);
    }

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
    // до которого была несколько изменений фамилии
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryTestLastNames2(Method testMethod, Class<?> c) throws Exception {
        Map<Integer, String> firstNameTest = new TreeMap<>();
        firstNameTest.put(2020, "Masha");
        firstNameTest.put(2022, "Nastya");
        Map<Integer, String> lastNameTest = new TreeMap<>();
        lastNameTest.put(2015, "Loboda");
        lastNameTest.put(1990, "Li");
        lastNameTest.put(2010, "Maslenik");

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);

        String res = testMethod.invoke(tr, 2016).toString();
        assertEquals("Loboda(Maslenik, Li) with unknown first name", res);
    }

    //    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
//    // до которого была только 1 смена имени
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryTestFirstNames(Method testMethod, Class<?> c) throws Exception {

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

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
    // до которого была только 2 смены имени
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryTestFirstNames1(Method testMethod, Class<?> c) throws Exception {
        Map<Integer, String> firstNameTest = new TreeMap<>();
        firstNameTest.put(2020, "Masha");
        firstNameTest.put(2000, "Nastya");
        firstNameTest.put(2010, "Olya");

        Map<Integer, String> lastNameTest = new TreeMap<>();
        lastNameTest.put(2012, "Lobova");

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);
        String res = (String) testMethod.invoke(tr, 2011);
        assertEquals("Olya(Nastya) with unknown last name", res);
    }

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
    // до которого была несколько изменений фамилии
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryTestFirstNames2(Method testMethod, Class<?> c) throws Exception {
        Map<Integer, String> firstNameTest = new TreeMap<>();
        firstNameTest.put(2012, "Masha");
        firstNameTest.put(2000, "Nastya");
        firstNameTest.put(2010, "Olya");
        firstNameTest.put(2020, "Kate");

        Map<Integer, String> lastNameTest = new TreeMap<>();
        lastNameTest.put(2014, "Lobova");
        lastNameTest.put(2015, "Ivanova");

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);
        String res = (String) testMethod.invoke(tr, 2013);
        assertEquals("Masha(Olya, Nastya) with unknown last name", res);
    }

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
    // до которого была только 1 смена имени и несколько изменений фамилии
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryTestNames(Method testMethod, Class<?> c) throws Exception {
        Map<Integer, String> firstNameTest = new TreeMap<>();
        firstNameTest.put(2012, "Masha");
        firstNameTest.put(2000, "Marina");
        firstNameTest.put(2010, "Olya");
        firstNameTest.put(2020, "Kate");

        Map<Integer, String> lastNameTest = new TreeMap<>();
        lastNameTest.put(2000, "Maslenik");
        lastNameTest.put(1990, "Li");
        lastNameTest.put(2001, "Loboda");

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);
        String res = (String) testMethod.invoke(tr, 2001);
        assertEquals("Marina Loboda(Maslenik, Li)", res);
    }

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
    // до которого была несколько изменений имени и несколько изменений фамилии
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryTestNames1(Method testMethod, Class<?> c) throws Exception {
        Map<Integer, String> firstNameTest = new TreeMap<>();
        firstNameTest.put(2012, "Nastya");
        firstNameTest.put(2000, "Marina");
        firstNameTest.put(2030, "Olya");

        Map<Integer, String> lastNameTest = new TreeMap<>();
        lastNameTest.put(2000, "Maslenik");
        lastNameTest.put(1990, "Li");
        lastNameTest.put(2015, "Lobova");
        lastNameTest.put(2005, "Loboda");
        lastNameTest.put(2010, "Olonech");

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);
        String res = (String) testMethod.invoke(tr, 2015);
        assertEquals("Nastya(Marina) Lobova(Olonech, Loboda, Maslenik, Li)", res);
    }

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
    // до которого была несколько изменений имени и несколько изменений фамилии
    //причем два раза подряд меняли имя на одно и тоже
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryTestNames2(Method testMethod, Class<?> c) throws Exception {
        Map<Integer, String> firstNameTest = new TreeMap<>();
        firstNameTest.put(2012, "Nastya");
        firstNameTest.put(2014, "Nastya");
        firstNameTest.put(2000, "Marina");
        firstNameTest.put(2030, "Olya");

        Map<Integer, String> lastNameTest = new TreeMap<>();
        lastNameTest.put(2000, "Maslenik");
        lastNameTest.put(1990, "Li");
        lastNameTest.put(2015, "Lobova");
        lastNameTest.put(2005, "Loboda");
        lastNameTest.put(2010, "Olonech");

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);
        String res = (String) testMethod.invoke(tr, 2015);
        assertEquals("Nastya(Marina) Lobova(Olonech, Loboda, Maslenik, Li)", res);
    }

    //getFullNameWithHistory, запрашиваем полную информацию об имени и фамилии на год,
    // до которого была несколько изменений имени и несколько изменений фамилии
    //причем два раза подряд меняли имя и фамилию на одно и тоже
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void getFullNameWithHistoryTestNames4(Method testMethod, Class<?> c) throws Exception{
        Map<Integer, String> firstNameTest = new TreeMap<>();
        firstNameTest.put(2012, "Nastya");
        firstNameTest.put(2014, "Nastya");
        firstNameTest.put(2000, "Marina");
        firstNameTest.put(2030, "Olya");

        Map<Integer, String> lastNameTest = new TreeMap<>();
        lastNameTest.put(2000, "Maslenik");
        lastNameTest.put(1990, "Li");
        lastNameTest.put(2015, "Lobova");
        lastNameTest.put(2005, "Loboda");
        lastNameTest.put(2010, "Lobova");

        Class[] catClassParams = {Map.class, Map.class};
        Object tr = c.getConstructor(catClassParams).newInstance(firstNameTest, lastNameTest);
        String res = (String) testMethod.invoke(tr, 2015);
        assertEquals("Nastya(Marina) Lobova(Loboda, Maslenik, Li)", res);
    }
}