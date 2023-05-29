package com.example.demo.testClass;

import com.example.demo.service.RunTestsService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class BusesTest {

    private String[] names = {"16", "85", "40", "56"};
    private String[][] stops = {{"Stachki231", "Pleven", "Sokol", "Centr"},
            {"Mega", "RS", "Krasnidarskay", "Relv", "Pl1", "SFEDU", "339", "Lev"},
            {"Stachki231", "OblBolnica", "Pleven", "Vokzal", "Centr"},
            {"Krasnidarskay", "Stachki231", "Vavilon", "Tekuceva", "Gorizont"}
    };

    //тест проверяет что при создании нескольних автобусов, нумерация маршрутов для каждого из них начинается с 1
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void addBusTest(Method testMethod, Class<?> c) throws Exception {
        Object bs1 = c.getConstructor().newInstance();
        assertEquals(1, (Integer) testMethod.invoke(bs1, names[0], stops[0]));
        Object bs2 = c.getConstructor().newInstance();
        assertEquals(1, (Integer) testMethod.invoke(bs2, names[0], stops[0]));
    }

    //тест проверяет, что при добавлении 2х одинаковых маршрутов, отличающихся лишь порядком остановок,
    //не будет вызвано исключение
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void addBusTest2(Method testMethod, Class<?> c) throws Exception {
        String[][] s = {{"Stachki231", "Pleven", "Sokol", "Centr"},
                {"Pleven", "Stachki231", "Sokol", "Centr"}
        };
        Object bs = c.getConstructor().newInstance();
        assertEquals(1, (Integer) testMethod.invoke(bs, names[0], s[0]));
        assertEquals(2, (Integer) testMethod.invoke(bs, names[1], s[1]));
    }

    //addBus, пытаемся добавить автобусы. если автобусы имеют одинаковые маршрутки, обрабатываем исключение
    //если позникает при добавлении какая-то иная ошибка, то говорим о том, что произошла непредвиденное исключение
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    void addBusTest3(Method testMethod, Class<?> c) throws Exception {
        Object bs = c.getConstructor().newInstance();
        int result;
        try {
            result = (Integer) testMethod.invoke(bs, names[0], stops[0]);
            assertEquals(1, result);
            result = (Integer) testMethod.invoke(bs, names[1], stops[1]);
            assertEquals(2, result);
            result = (Integer) testMethod.invoke(bs, names[1], stops[2]);
            assertEquals(3, result);
            result = (Integer) testMethod.invoke(bs, names[3], stops[0]);
            assertEquals(3, result);

            fail("Multiple add");
        } catch (Exception e) {
            String mess = e.getCause().getMessage();
            assertEquals(mess, "Already exists for 1");
        }
        try {
            result = (Integer) testMethod.invoke(bs, names[3], stops[3]);
            assertEquals(4, result);
        } catch (Exception e) {
            fail("Wrong exception");
        }
    }

    //SbusesForStop, рассматриваем случай когда есть несколько маршрутов, и некоторые из них проходят через указанную остановку
    //так же если выкидывается какое-либо исключение, не входящее в условия задачи, выбрасываем сообщение
    // о непредвиденном исключении
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void sbusesForStopTest(Method testMethod, Class<?> c) throws Exception {
        try {
            String bsL[] = {"16", "40", "56"};

            Object bs = c.getConstructor().newInstance();
            Method addBus = c.getMethod("addBus", String.class, String[].class);
            for (int i = 0; i < names.length; ++i) {
                addBus.invoke(bs, names[i], stops[i]);
            }
            List<String> result = (List<String>) testMethod.invoke(bs, "Stachki231");

            assertArrayEquals(bsL, result.toArray());

        } catch (Exception e) {
            fail("The test case is a prototype.");
        }
    }

    //SbusesForStop, рассматривааем случай когда есть 1 маршрут, и он проходит через указанную остановку
    //так же если выкидывается какое-либо исключение, не входящее в условия задачи, выбрасываем сообщение
    // о непредвиденном исключении
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void sbusesForStopTest1(Method testMethod, Class<?> c) throws Exception {
        Object bs = c.getConstructor().newInstance();
        Method addBus = c.getMethod("addBus", String.class, String[].class);
        try {
            for (int i = 0; i < names.length; ++i) {
                addBus.invoke(bs, names[i], stops[i]);
            }
            List<String> result1 = (List<String>) testMethod.invoke(bs, "Pl1");
            String bsL1[] = {"85"};
            assertArrayEquals(bsL1, result1.toArray());
        } catch (Exception e) {
            fail("The test case is a prototype.");
        }
    }

    //SbusesForStop, рассматривается ситуация, когда через указанную остановку не проходит ни один маршрут
    //так же если выкидывается какое-либо исключение, не входящее в условия задачи, выбрасываем сообщение
    // о непредвиденном исключении
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void sbusesForStopTest2(Method testMethod, Class<?> c) throws Exception {
        Object bs = c.getConstructor().newInstance();
        Method addBus = c.getMethod("addBus", String.class, String[].class);
        try {
            String nameStop = "CGB";
            String bsL[] = null;
            for (int i = 0; i < names.length; ++i) {
                addBus.invoke(bs, names[i], stops[i]);
            }
            assertThrows(Exception.class, () -> {
                testMethod.invoke(bs,nameStop);
            });
        } catch (Exception e) {
            fail("The test case is a prototype.");
        }
    }

    //NbusesForStopException, NbusesForStop рассматривааем случай когда есть несколько маршрутов, и некоторые из них проходят через указанную остановку
    //так же если выкидывается какое-либо исключение, не входящее в условия задачи, выбрасываем сообщение
    // о непредвиденном исключении
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void nbusesForStopTest(Method testMethod, Class<?> c) throws Exception {
        Object bs = c.getConstructor().newInstance();
        Method addBus = c.getMethod("addBus", String.class, String[].class);
        try {
            Integer bsL[] = {1,3,4};


            for (int i = 0; i < names.length; ++i) {
                addBus.invoke(bs,names[i], stops[i]);
            }
            Set result = (Set)testMethod.invoke(bs,"Stachki231");

            assertArrayEquals(bsL, result.toArray());

        } catch (Exception e) {
            fail("The test case is a prototype.");
        }
    }

    //NbusesForStopExceptionБ рассматривааем случай когда есть 1 маршрут, и он проходит через указанную остановку
    //так же если выкидывается какое-либо исключение, не входящее в условия задачи, выбрасываем сообщение
    // о непредвиденном исключении
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void nbusesForStopTest1(Method testMethod, Class<?> c) throws Exception {
        Object bs = c.getConstructor().newInstance();
        Method addBus = c.getMethod("addBus", String.class, String[].class);
        try {
            
            for (int i = 0; i < names.length; ++i) {
                addBus.invoke(bs,names[i], stops[i]);
            }
            Set result1 = (Set)testMethod.invoke(bs,"Pl1");
            Integer bsL1[] = {2};
            assertArrayEquals(bsL1, result1.toArray());
        } catch (Exception e) {
            fail("The test case is a prototype.");
        }
    }

    //NbusesForStopException, рассматривается ситуация, когда через указанную остановку не проходит ни один маршрут
    //так же если выкидывается какое-либо исключение, не входящее в условия задачи, выбрасываем сообщение
    // о непредвиденном исключении
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void nbusesForStopTestException(Method testMethod, Class<?> c) throws Exception {
        Object bs = c.getConstructor().newInstance();
        Method addBus = c.getMethod("addBus", String.class, String[].class);
        try {
            String nameStop = "CGB";
            
            for (int i = 0; i < names.length; ++i) {
                addBus.invoke(bs, names[i], stops[i]);
            }
            assertThrows(Exception.class, () -> testMethod.invoke(bs,nameStop));
        } catch (Exception e) {
            fail("The test case is a prototype.");
        }
    }

    //StopsForBus, проверяет верность составленной map для маршрута 16
    //так же если выкидывается какое-либо исключение, не входящее в условия задачи, выбрасываем сообщение
    // о непредвиденном исключении
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void stopsForBusTest(Method testMethod, Class<?> c) throws Exception {
        Object bs = c.getConstructor().newInstance();
        Method addBus = c.getMethod("addBus", String.class, String[].class);
        try {
            String nameBus = "16";
            
            for (int i = 0; i < names.length; ++i) {
               addBus.invoke(bs,names[i], stops[i]);
            }
            Map<String, Set<Integer>> result = (Map)testMethod.invoke(bs,nameBus);
            List<Set<Integer>> exNum = List.of(Set.of(3), Set.of(3, 4), Set.of());
            String[] exStop = {"Stachki231", "Pleven", "Sokol", "Centr"};

            for (Map.Entry<String, Set<Integer>> e : result.entrySet()) {
                String s = e.getKey();
                assertTrue(List.of(exStop).contains(s));

                Set<Integer> v = e.getValue();
                assertTrue(exNum.contains(v));
            }
        } catch (Exception e) {
           fail("The test case is a prototype.");
        }
    }

    //StopsForBus,проверяет верность составленной map для маршрута 85
    //так же если выкидывается какое-либо исключение, не входящее в условия задачи, выбрасываем сообщение
    // о непредвиденном исключении
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void stopsForBusTest1(Method testMethod, Class<?> c) throws Exception {
        Object bs = c.getConstructor().newInstance();
        Method addBus = c.getMethod("addBus", String.class, String[].class);
        try {
            String nameBus = "85";
            for (int i = 0; i < names.length; ++i) {
                addBus.invoke(bs,names[i], stops[i]);
            }
            Map<String, Set<Integer>> result = (Map)testMethod.invoke(bs,nameBus);
            List<Set<Integer>> exNum = List.of(Set.of(), Set.of(4));
            String[] exStop = {"Mega", "RS", "Krasnidarskay", "Relv", "Pl1", "SFEDU", "339", "Lev"};

            for (Map.Entry<String, Set<Integer>> e : result.entrySet()) {
                String s = e.getKey();
                assertTrue(List.of(exStop).contains(s));

                Set<Integer> v = e.getValue();
                assertTrue(exNum.contains(v));
            }
        } catch (Exception e) {
           fail("The test case is a prototype.");
        }
    }

    //StopsForBus,проверяет верность составленной map для маршрута 40
    //так же если выкидывается какое-либо исключение, не входящее в условия задачи, выбрасываем сообщение
    // о непредвиденном исключении
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void stopsForBusTest2(Method testMethod, Class<?> c) throws Exception {
        Object bs = c.getConstructor().newInstance();
        Method addBus = c.getMethod("addBus", String.class, String[].class);
        try {
            String nameBus = "40";
            for (int i = 0; i < names.length; ++i) {
                addBus.invoke(bs,names[i], stops[i]);
            }
            Map<String, Set<Integer>> result = (Map)testMethod.invoke(bs,nameBus);
            List<Set<Integer>> exNum = List.of(Set.of(), Set.of(1), Set.of(1, 4));
            String[] exStop = {"Stachki231", "OblBolnica", "Pleven", "Vokzal", "Centr"};

            for (Map.Entry<String, Set<Integer>> e : result.entrySet()) {
                String s = e.getKey();
                assertTrue(List.of(exStop).contains(s));

                Set<Integer> v = e.getValue();
                assertTrue(exNum.contains(v));
            }
        } catch (Exception e) {
            fail("The test case is a prototype.");
        }
    }

    //StopsForBus,проверяет верность составленной map для маршрута 56
    //так же если выкидывается какое-либо исключение, не входящее в условия задачи, выбрасываем сообщение
    // о непредвиденном исключении
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void stopsForBusTest4(Method testMethod, Class<?> c) throws Exception {
        Object bs = c.getConstructor().newInstance();
        Method addBus = c.getMethod("addBus", String.class, String[].class);

        try {
            String nameBus = "56";
            for (int i = 0; i < names.length; ++i) {
               addBus.invoke(bs,names[i], stops[i]);
            }
            Map<String, Set<Integer>> result = (Map)testMethod.invoke(bs,nameBus);
            List<Set<Integer>> exNum = List.of(Set.of(), Set.of(2), Set.of(1, 3));
            String[] exStop = {"Krasnidarskay", "Stachki231", "Vavilon", "Tekuceva", "Gorizont"};

            for (Map.Entry<String, Set<Integer>> e : result.entrySet()) {
                String s = e.getKey();
                assertTrue(List.of(exStop).contains(s));

                Set<Integer> v = e.getValue();
                assertTrue(exNum.contains(v));
            }
        } catch (Exception e) {
            fail("The test case is a prototype.");
        }
    }

    //StopsForBus,проверяет выбрасываемое исключение с текстом No bus для, когда указанно автоба нет
    //так же если выкидывается какое-либо исключение, не входящее в условия задачи, выбрасываем сообщение
    // о непредвиденном исключении
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void stopsForBusTest3(Method testMethod, Class<?> c) throws Exception {
        Object bs = c.getConstructor().newInstance();
        Method addBus = c.getMethod("addBus", String.class, String[].class);
        try {
            String nameBus = "5";
            for (int i = 0; i < names.length; ++i) {
                addBus.invoke(bs,names[i], stops[i]);
            }
           assertThrows(Exception.class, () -> testMethod.invoke(bs,nameBus));

        } catch (Exception e) {
            fail("The test case is a prototype.");
        }
    }

    //AllBuses, проверяет выдаваемый список названий всех маршрутов автобусов  в алфавитном порядке для цифр
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void allBusesTest(Method testMethod, Class<?> c) throws Exception {
        Object bs = c.getConstructor().newInstance();
        Method addBus = c.getMethod("addBus", String.class, String[].class);
        for (int i = 0; i < names.length; ++i) {
            addBus.invoke(bs,names[i], stops[i]);
        }
        List<String> s = List.of("16", "40", "56", "85");
        List<String> res = (List<String>) testMethod.invoke(bs);
        assertEquals(s, res);

    }

    //AllBuses, проверяет выдаваемый список названий всех маршрутов автобусов  в алфавитном порядке для смешанных названий
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void allBusesTest1(Method testMethod, Class<?> c) throws Exception {
        Object bs = c.getConstructor().newInstance();
        Method addBus = c.getMethod("addBus", String.class, String[].class);

        String[] ss = {"noBus", "bus1", "Bus", "busN"};
        for (int i = 0; i < ss.length; ++i) {
            addBus.invoke(bs,ss[i], stops[i]);
        }
        List<String> s=List.of("Bus", "bus1", "busN", "noBus");
        List<String> res = ((List<String>)testMethod.invoke(bs));
        assertEquals(s, res);

    }
}