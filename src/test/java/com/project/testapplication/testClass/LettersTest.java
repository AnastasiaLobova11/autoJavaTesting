package com.project.testapplication.testClass;

import com.project.testapplication.service.RunTestsService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.lang.reflect.Method;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class LettersTest {

    // метод size() для пустого конструктора
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void sizeTestNoArg(Method testMethod, Class<?> c) throws Exception {

        Object letters = c.getConstructor().newInstance();

        Integer res = (Integer) testMethod.invoke(letters);
        assertEquals(0, res);
    }

    //метод size() для конструктора, принимающего пустую строку
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void sizeTestEmptyString(Method testMethod, Class<?> c) throws Exception {

        Object letters = c.getConstructor(String.class).newInstance("");

        Integer res = (Integer) testMethod.invoke(letters);
        assertEquals(0, res);
    }

    //метод size() для конструктора, принимающего строку из 5 символов
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void sizeTestNotEmptyString(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("12345");

        Integer res = (Integer) testMethod.invoke(letters);
        assertEquals(5, res);
    }

    //метод size() для конструктора, принимающего строку из 1 символа
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void sizeTestNotEmptyString2(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("2");

        Integer res = (Integer) testMethod.invoke(letters);
        assertEquals(1, res);
    }

    //метод add(), добавляющий к непустой строке 1 символ
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void addTest(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("qw");
        Boolean res = (Boolean) testMethod.invoke(letters, '1');
        Method toString = c.getDeclaredMethod("toString");
        String stringRes = toString.invoke(letters).toString();
        assertEquals("qw1", stringRes);
        assertTrue(res);
    }

    //метод add(), добавляющий к созданному экзмепляру, созданного пустым конструктором,1 символ
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void addTestNoArgs(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor().newInstance();
        Boolean res = (Boolean) testMethod.invoke(letters, '1');
        Method toString = c.getDeclaredMethod("toString");
        String stringRes = toString.invoke(letters).toString();
        assertEquals("1", stringRes);
        assertTrue(res);
    }

    //метод add(), добавляющий к созданному экзмепляру, созданного конструктором из пустой строки, 1 символ
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void addTestEmptyString(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("");
        Boolean res = (Boolean) testMethod.invoke(letters, '1');
        Method toString = c.getDeclaredMethod("toString");
        String stringRes = toString.invoke(letters).toString();
        assertEquals("1", stringRes);
        assertTrue(res);
    }

    //метод add(), добавляющий к созданному экземпляру, созданного конструктором из длинной строки, символ
    //для изменения размера внутреннего массива
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void addTestLongString(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("knfklKK193");
        Method toString = c.getDeclaredMethod("toString");
        Boolean res = (Boolean) testMethod.invoke(letters, ' ');
        String stringRes = toString.invoke(letters).toString();
        assertTrue(res);
        assertEquals("knfklKK193 ", stringRes);
    }

    //метод contains(), проверяющий содержит ли экземляр, созданный из пустого конструктора, данный символ
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void containsTestNoArgs(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor().newInstance();
        Boolean res = (Boolean) testMethod.invoke(letters, '1');
        assertFalse(res);
    }

    //метод contains(), проверяющий содержит ли экземляр, созданный конструктором из пустой строки, данный символ
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void containsTestEmptyString(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("");
        Boolean res = (Boolean) testMethod.invoke(letters, '1');
        assertFalse(res);
    }

    //метод contains(), проверяющий содержит ли экземляр, созданный из конструктора со строкой, данные символы
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void containsTestLongString(Method testMethod, Class<?> c) throws Exception {

        Object letters = c.getConstructor(String.class).newInstance("12345");

        assertTrue((Boolean) testMethod.invoke(letters, '1'));
        assertTrue((Boolean) testMethod.invoke(letters, '3'));
        assertTrue((Boolean) testMethod.invoke(letters, '5'));
        assertFalse((Boolean) testMethod.invoke(letters, '0'));
    }

    //метод contains(), проверяющий содержит ли экземляр, созданный из 1 символа, заданные символы
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void containsTestOneCharacterString(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("1");

        assertTrue((Boolean) testMethod.invoke(letters, '1'));
        assertFalse((Boolean) testMethod.invoke(letters, '3'));
    }

    //метод addAll(), проверяющие добавление всех элементов одной строки,не содержащей символы из 1 строки, к другой
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void addAllTest(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("qw");
        Object addLetters = c.getConstructor(String.class).newInstance("123");
        Method toString = c.getDeclaredMethod("toString");
        testMethod.invoke(letters, addLetters);
        String stringRes = toString.invoke(letters).toString();
        assertEquals("qw123", stringRes);
    }

    //метод addAll(), проверяющие добавление всех элементов одной строки к элементу, созданного из пустого конструктора
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void addAllTestNoArgs(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor().newInstance();
        Object addLetters = c.getConstructor(String.class).newInstance("123");
        Method toString = c.getDeclaredMethod("toString");
        testMethod.invoke(letters, addLetters);
        String stringRes = toString.invoke(letters).toString();
        assertEquals("123", stringRes);
    }

    //метод addAll(), проверяющие добавление всех элементов одной строки к пустой строке
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void addAllTestEmptyStringArg(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("");
        Object addLetters = c.getConstructor(String.class).newInstance("123");
        Method toString = c.getDeclaredMethod("toString");
        testMethod.invoke(letters, addLetters);
        String stringRes = toString.invoke(letters).toString();
        assertEquals("123", stringRes);
    }

    //метод addAll(), проверяющий расширение внутреннего массива при переполнении
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void addAllTestLongString(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("0123456789");
        Object addLetters = c.getConstructor(String.class).newInstance("00123456789");
        Method toString = c.getDeclaredMethod("toString");
        testMethod.invoke(letters, addLetters);
        String stringRes = toString.invoke(letters).toString();
        assertEquals("012345678900123456789", stringRes);
    }

    //метод clear(), удаляющий все элементы непустой строки
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void clearTest(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("qw");
        Method sizeMethod = c.getDeclaredMethod("size");
        testMethod.invoke(letters);
        Integer size = (Integer) sizeMethod.invoke(letters);
        assertEquals(0, size);
    }

    //метод clear(), удаляющий все элементы строки, созданной из пустого конструтора
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void clearTestNoArg(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor().newInstance();
        Method sizeMethod = c.getDeclaredMethod("size");
        testMethod.invoke(letters);
        Integer size = (Integer) sizeMethod.invoke(letters);
        assertEquals(0, size);
    }

    //метод clear(), удаляющий все элементы строки, созданной из пустой строки
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void clearTestEmptyStringArg(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("");
        Method sizeMethod = c.getDeclaredMethod("size");
        testMethod.invoke(letters);
        Integer size = (Integer) sizeMethod.invoke(letters);
        assertEquals(0, size);
    }

    //instanceof, проверяет, что первый элемент из нашей коллекции- Character
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestInstanceOf(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("12345");
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);
        Method next = iterator.getClass().getMethod("next");

        next.setAccessible(true);

        if (!(next.invoke(iterator) instanceof Character)) {
            fail("Returned value is not a Character");
        }
    }

    //IteratorNext, проверяет метод next() в непустом экземпляре
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestNext(Method testMethod, Class<?> c) throws Exception {

        Object letters = c.getConstructor(String.class).newInstance("123");
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);
        Method next = iterator.getClass().getMethod("next");
        next.setAccessible(true);

        assertEquals("1", next.invoke(iterator).toString());
        assertEquals("2", next.invoke(iterator).toString());
        assertEquals("3", next.invoke(iterator).toString());
    }

    //IteratorNext, проверяет метод next() в экзмепляре, созданным из конструктора с пустой строкой
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestNextExceptionEmptyStringArg(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("");
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);
        Method next = iterator.getClass().getMethod("next");
        next.setAccessible(true);

        assertThrows(Exception.class, () -> next.invoke(iterator));
    }

    //IteratorNext, проверяет метод next() в экзмепляре, созданным из конструктора с пустого конструктора
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestNextExceptionNoArg(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor().newInstance();
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);
        Method next = iterator.getClass().getMethod("next");
        next.setAccessible(true);

        assertThrows(Exception.class, () -> next.invoke(iterator));
    }

    //IteratorNext, проверяет метод next() на выход за пределы и выбрасывает исключение
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestNextException(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("12");
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);
        Method next = iterator.getClass().getMethod("next");
        next.setAccessible(true);
        //Iterator<Character> cl = new Letters("12").iterator();
        assertThrows(Exception.class, () -> {
            assertEquals("1", next.invoke(iterator).toString());
            assertEquals("2", next.invoke(iterator).toString());
            next.invoke(iterator);
        });
    }

    //IteratorHasNext, проверяет метод  hasNext() для экзепляра из 1 символа
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestHasNext(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("2");
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);
        Method hasNext = iterator.getClass().getMethod("hasNext");
        Method next = iterator.getClass().getMethod("next");
        hasNext.setAccessible(true);
        next.setAccessible(true);
        assertTrue((Boolean) hasNext.invoke(iterator));
        next.invoke(iterator);
        assertFalse((Boolean) hasNext.invoke(iterator));
    }

    //IteratorHasNext, проверяет метод hasNext() для экземпляра из пустого конструктора
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestHasNextNoArg(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor().newInstance();
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);
        Method hasNext = iterator.getClass().getMethod("hasNext");
        hasNext.setAccessible(true);
        assertFalse((Boolean) hasNext.invoke(iterator));
    }

    //IteratorHasNext, проверяет метод  hasNext() для экзепляра из пустой строки
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestHasNextEmptyStringArg(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("");
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);
        Method hasNext = iterator.getClass().getMethod("hasNext");
        hasNext.setAccessible(true);
        assertFalse((Boolean) hasNext.invoke(iterator));
    }

    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    //IteratorClear, очищает наш экземпляр и пробует вызвать next(), но получает ошибку
    public void iteratorTestHasNextClear(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("123");
        Method clear = c.getMethod("clear");
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);
        Method hasNext = iterator.getClass().getMethod("hasNext");
        Method next = iterator.getClass().getMethod("next");
        next.setAccessible(true);
        hasNext.setAccessible(true);
        assertTrue((Boolean) hasNext.invoke(iterator));
        clear.invoke(letters);
        assertFalse((Boolean) hasNext.invoke(iterator));
        assertThrows(Exception.class, () -> next.invoke(iterator));
    }

    //TwoIterators, проверяет на совместное использование 2х итератором для одного экзмепляра
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestTwoIterator(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("123");
        Iterator<Character> iterator1 = (Iterator<Character>) testMethod.invoke(letters);
        Iterator<Character> iterator2 = (Iterator<Character>) testMethod.invoke(letters);

        Method hasNext1 = iterator1.getClass().getMethod("hasNext");
        Method next1 = iterator1.getClass().getMethod("next");
        Method hasNext2 = iterator2.getClass().getMethod("hasNext");
        Method next2 = iterator2.getClass().getMethod("next");

        next1.setAccessible(true);
        hasNext1.setAccessible(true);
        next2.setAccessible(true);
        hasNext2.setAccessible(true);

        assertEquals(hasNext1.invoke(iterator1), hasNext2.invoke(iterator2));
        assertEquals("1", next1.invoke(iterator1).toString());
        assertEquals("1", next2.invoke(iterator2).toString());
        next1.invoke(iterator1);
        assertEquals("3", next1.invoke(iterator1).toString());
        assertEquals("2", next2.invoke(iterator2).toString());
        assertTrue((Boolean) hasNext2.invoke(iterator2));
        assertFalse((Boolean) hasNext1.invoke(iterator1));
    }

    //DoubleUsing, двойной проход по списку
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestDoubleUsing(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("12345");
        Iterator<Character> iterator1 = (Iterator<Character>) testMethod.invoke(letters);
        Iterator<Character> iterator2 = (Iterator<Character>) testMethod.invoke(letters);

        Method hasNext1 = iterator1.getClass().getMethod("hasNext");
        Method next1 = iterator1.getClass().getMethod("next");
        Method hasNext2 = iterator2.getClass().getMethod("hasNext");
        Method next2 = iterator2.getClass().getMethod("next");

        next1.setAccessible(true);
        hasNext1.setAccessible(true);
        next2.setAccessible(true);
        hasNext2.setAccessible(true);

        int i = 0;
        while ((Boolean) hasNext1.invoke(iterator1)) {
            i++;
            next1.invoke(iterator1);
        }
        while ((Boolean) hasNext2.invoke(iterator2)) {
            i++;
            next2.invoke(iterator2);
        }
        assertEquals(10, i);
    }

    //removeAll, удаляет из заданного экземпляра все совпадения с другим экземпляром, оставляя не совпадающие элеемнты
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void removeAllTest(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("12345");
        Object lettersToRemove = c.getConstructor(String.class).newInstance("4");
        testMethod.invoke(letters, lettersToRemove);
        Method toString = c.getMethod("toString");
        assertEquals("1235", toString.invoke(letters));
    }

    //removeAll, удаляет из заданного экземпляра все совпадения с другим экземпляром,
    //учитывая то,что во 2 есть все элементы первого
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void removeAllTestDoubleChar(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("444");
        Object lettersToRemove = c.getConstructor(String.class).newInstance("4");
        testMethod.invoke(letters, lettersToRemove);
        Method size = c.getMethod("size");
        assertEquals(0, (Integer) size.invoke(letters));
    }

    //removeAll, удаляет из заданного экземпляра все совпадения с другим экземпляром,
    //учитывая критические ситуации(удаление из начала/конца)
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void removeAllTestDoubleChar2(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("414243");
        Object lettersToRemove = c.getConstructor(String.class).newInstance("4");
        testMethod.invoke(letters, lettersToRemove);
        Method toString = c.getMethod("toString");
        assertEquals("123", toString.invoke(letters));
    }

    //removeAll, пытается удалить если нет совпадений
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void removeAllTestNoChar(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("414243");
        Object lettersToRemove = c.getConstructor(String.class).newInstance("5");
        testMethod.invoke(letters, lettersToRemove);
        Method toString = c.getMethod("toString");
        assertEquals("414243", toString.invoke(letters));
    }

    //remove, удаляет первый встретившийся символ, совпавший с указанным
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void removeTest(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("142345");
        testMethod.invoke(letters, '4');
        Method toString = c.getMethod("toString");
        assertEquals("12345", toString.invoke(letters));
    }

    //remove, не удаляет символ,так как ни одни не совпал с указанным
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void removeTestNoChar(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("142345");
        testMethod.invoke(letters, '9');
        Method toString = c.getMethod("toString");
        assertEquals("142345", toString.invoke(letters));
    }

    //remove, не удаляет символ,так как экземпляр создан из пустой строки
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void removeTestEmptyStringArg(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("");
        testMethod.invoke(letters, '4');
        Method toString = c.getMethod("toString");
        assertEquals("", toString.invoke(letters));
    }

    //remove, не удаляет символ,так как экземпляр создан из пустого конструктора
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void removeTestNoArg(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor().newInstance();
        testMethod.invoke(letters, '4');
        Method toString = c.getMethod("toString");
        assertEquals("", toString.invoke(letters));
    }

    //remove, удаляет символ в экземпляре, где рядом находятся 2 одинаковых необходимых символа
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void removeTest1(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("444414");
        testMethod.invoke(letters, '4');
        Method toString = c.getMethod("toString");
        Method size = c.getMethod("size");
        assertEquals("44414", toString.invoke(letters));
        assertEquals(5, (Integer) size.invoke(letters));
    }

    //remove, удаляет символ в экземпляре, где рядом находятся только этот элемент
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void removeTestIneElement(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("4");
        testMethod.invoke(letters, '4');
        Method toString = c.getMethod("toString");
        Method size = c.getMethod("size");
        assertEquals("", toString.invoke(letters));
        assertEquals(0, (Integer) size.invoke(letters));
    }

    //IteratorRemove, вызываем метод next(),а после remove()
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestRemove1Next(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("123");
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);

        Method remove = iterator.getClass().getMethod("remove");
        Method next = iterator.getClass().getMethod("next");
        Method toString = c.getMethod("toString");

        remove.setAccessible(true);
        next.setAccessible(true);

        next.invoke(iterator);
        remove.invoke(iterator);
        assertEquals("23", toString.invoke(letters));
    }

    //IteratorRemove, вызываем два раза метод next(),а после remove()
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestRemove2Next(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("123");
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);

        Method remove = iterator.getClass().getMethod("remove");
        Method next = iterator.getClass().getMethod("next");
        Method toString = c.getMethod("toString");

        remove.setAccessible(true);
        next.setAccessible(true);

        next.invoke(iterator);
        next.invoke(iterator);
        remove.invoke(iterator);
        assertEquals("13", toString.invoke(letters));
    }

    //IteratorRemove, вызываем три раза метод next(),а после remove()
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestRemove3Next(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("123");
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);

        Method remove = iterator.getClass().getMethod("remove");
        Method next = iterator.getClass().getMethod("next");
        Method toString = c.getMethod("toString");

        remove.setAccessible(true);
        next.setAccessible(true);

        next.invoke(iterator);
        next.invoke(iterator);
        next.invoke(iterator);
        remove.invoke(iterator);
        assertEquals("12", toString.invoke(letters));
    }

    //IteratorRemove,корректно удаляем первые 2 символа
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestSomeRemove(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("123");
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);

        Method remove = iterator.getClass().getMethod("remove");
        Method next = iterator.getClass().getMethod("next");
        Method toString = c.getMethod("toString");
        Method size = c.getMethod("size");

        remove.setAccessible(true);
        next.setAccessible(true);

        next.invoke(iterator);
        remove.invoke(iterator);
        next.invoke(iterator);
        remove.invoke(iterator);
        assertEquals("3", toString.invoke(letters));
        assertEquals(1, size.invoke(letters));
    }

    //IteratorRemove,корректно удаляем все символы
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestRemoveAllElem(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("123");
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);

        Method remove = iterator.getClass().getMethod("remove");
        Method next = iterator.getClass().getMethod("next");
        Method toString = c.getMethod("toString");
        Method size = c.getMethod("size");

        remove.setAccessible(true);
        next.setAccessible(true);

        next.invoke(iterator);
        remove.invoke(iterator);
        next.invoke(iterator);
        remove.invoke(iterator);
        next.invoke(iterator);
        remove.invoke(iterator);
        assertEquals("", toString.invoke(letters));
        assertEquals(0, size.invoke(letters));
    }

    //IteratorRemove,корректно удаляем символы внутри строки
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestRemove1(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("12345");
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);

        Method remove = iterator.getClass().getMethod("remove");
        Method next = iterator.getClass().getMethod("next");
        Method toString = c.getMethod("toString");
        Method size = c.getMethod("size");

        remove.setAccessible(true);
        next.setAccessible(true);

        next.invoke(iterator);
        next.invoke(iterator);
        remove.invoke(iterator);
        next.invoke(iterator);
        remove.invoke(iterator);
        assertEquals("145", toString.invoke(letters));
        assertEquals(3, size.invoke(letters));
    }

    //IllegalIteratorRemove, пытаемся вызвать remove() без next()
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestRemoveException(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("123");
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);

        Method remove = iterator.getClass().getMethod("remove");
        remove.setAccessible(true);

        assertThrows(Exception.class, () -> remove.invoke(iterator));
    }

    //IteratorRemove,пытаемся вызвать remove() без next() и проверяем не изменилась ли длина строки
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestRemove2(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("123");
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);

        Method size = c.getMethod("size");
        Method remove = iterator.getClass().getMethod("remove");
        Method next = iterator.getClass().getMethod("next");
        remove.setAccessible(true);
        next.setAccessible(true);

        assertThrows(Exception.class, () -> remove.invoke(iterator));

        assertEquals(3, size.invoke(letters));
        next.invoke(iterator);
        remove.invoke(iterator);
        assertEquals(2, size.invoke(letters));

        assertThrows(Exception.class, () -> remove.invoke(iterator));
        assertEquals(2, size.invoke(letters));
    }

    //IteratorRemove,сначала 2 next(), в затем 2 remove() подряд
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestRemove3(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("12345");
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);

        Method remove = iterator.getClass().getMethod("remove");
        Method next = iterator.getClass().getMethod("next");
        remove.setAccessible(true);
        next.setAccessible(true);

        assertThrows(Exception.class, () -> {
            next.invoke(iterator);
            next.invoke(iterator);
            remove.invoke(iterator);
            remove.invoke(iterator);
        });
    }

    //IteratorRemove,пытаемся вызвать remove(), когда вышли за гранцы
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestRemove4(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("1");
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);

        Method remove = iterator.getClass().getMethod("remove");
        Method next = iterator.getClass().getMethod("next");
        remove.setAccessible(true);
        next.setAccessible(true);

        assertThrows(Exception.class, () -> {
            next.invoke(iterator);
            next.invoke(iterator);
            remove.invoke(iterator);
        });
    }

    //IteratorRemove,пытаемся вызвать remove(), когда экземпляр пуст
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void iteratorTestRemove5(Method testMethod, Class<?> c) throws Exception {
        Object letters = c.getConstructor(String.class).newInstance("");
        Iterator<Character> iterator = (Iterator<Character>) testMethod.invoke(letters);

        Method remove = iterator.getClass().getMethod("remove");
        remove.setAccessible(true);

        assertThrows(Exception.class, () -> remove.invoke(iterator));
    }

    //retainAll(), выделяет совпадение из 2х непустых строк
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void retainAllTest(Method testMethod, Class<?> c) throws Exception {
        Object letters1 = c.getConstructor(String.class).newInstance("qwerty");
        Object letters2 = c.getConstructor(String.class).newInstance("bgtredcv");

        Method toString = c.getMethod("toString");
        testMethod.invoke(letters1, letters2);
        assertEquals("ert", toString.invoke(letters1));
    }

    //retainAll(), выделяет совпадение из 2х непустых строк с повторяющимися элементами
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void retainAllTest1(Method testMethod, Class<?> c) throws Exception {
        Object letters1 = c.getConstructor(String.class).newInstance("qwertyqwrtye");
        Object letters2 = c.getConstructor(String.class).newInstance("bgtredcv");

        Method toString = c.getMethod("toString");
        testMethod.invoke(letters1, letters2);
        assertEquals("ertrte", toString.invoke(letters1));
    }

    //retainAll(), выделяет совпадение из 2х непустых несовпадающих строк
    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void retainAllTest2(Method testMethod, Class<?> c) throws Exception {
        Object letters1 = c.getConstructor(String.class).newInstance("afasa");
        Object letters2 = c.getConstructor(String.class).newInstance("bgtredcv");

        Method toString = c.getMethod("toString");
        testMethod.invoke(letters1, letters2);
        assertEquals("", toString.invoke(letters1));
    }

    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void retainAllTest3(Method testMethod, Class<?> c) throws Exception {
        Object letters1 = c.getConstructor(String.class).newInstance("");
        Object letters2 = c.getConstructor(String.class).newInstance("bgtredcv");

        Method toString = c.getMethod("toString");
        testMethod.invoke(letters1, letters2);
        assertEquals("", toString.invoke(letters1));
    }

    @ParameterizedTest
    @ArgumentsSource(RunTestsService.class)
    public void retainAllTest4(Method testMethod, Class<?> c) throws Exception {
        Object letters1 = c.getConstructor(String.class).newInstance("afasa");
        Object letters2 = c.getConstructor(String.class).newInstance("");

        Method toString = c.getMethod("toString");
        testMethod.invoke(letters1, letters2);
        assertEquals("", toString.invoke(letters1));
    }

}