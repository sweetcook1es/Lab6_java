package Lab6;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.InvocationTargetException;
import static org.junit.jupiter.api.Assertions.*;


public class InvokeTest {
    private StringBuilder executionLog; // лог вызовов методов для проверки порядка вызовов
    private TestInvokeClass testObject; // тестируемый объект


    static class TestInvokeClass {
        private int counter = 0; // Счетчик для проверки изменения состояния объекта
        private StringBuilder log; // Логгер для записи вызовов методов
        private boolean shouldThrowException = false; // Флаг для управления исключением


        public TestInvokeClass(StringBuilder log) {
            this.log = log;
        }


        @Invoke
        public void incrementCounter() {
            counter++;
            log.append("incrementCounter called, counter=").append(counter).append("; ");
        }


        @Invoke
        public void logMessage() {
            log.append("logMessage called; ");
        }


        @Invoke
        private void privateMethod() {
            log.append("privateMethod called; ");
        }


        public void notAnnotatedMethod() {
            log.append("notAnnotatedMethod called; ");
        }


        @Invoke
        public String methodWithReturnValue() {
            log.append("methodWithReturnValue called; ");
            return "ReturnValue";
        }


        @Invoke
        public void methodWithException() throws Exception {
            log.append("methodWithException called; ");
            if (shouldThrowException) {
                throw new IllegalStateException("Test exception from annotated method");
            }
        }


        public int getCounter() {
            return counter;
        }

        public void setShouldThrowException(boolean value) {
            this.shouldThrowException = value;
        }
    }


    // выполняется перед каждым тестом, новые данные для теста
    @BeforeEach
    void setUp() {
        executionLog = new StringBuilder(); // новый лог для каждого теста
        testObject = new TestInvokeClass(executionLog); // новый тестовый объект
    }

    /**
     * Тест проверяет базовую функциональность:
     * - вызов всех методов с аннотацией @Invoke
     * - игнорирование методов без аннотации
     * - изменение состояния объекта
     */
    @Test
    void testInvokeAnnotatedMethods() throws Exception {
        // исключение не будет выброшено для этого теста
        testObject.setShouldThrowException(false);

        // вызываем все методы с аннотацией @Invoke через обработчик
        InvokeHandler.invokeAnnotatedMethods(testObject);

        String log = executionLog.toString(); // получаем лог вызовов

        // проверка, что ВСЕ аннотированные методы были вызваны
        assertTrue(log.contains("incrementCounter called"),
                "Публичный метод с аннотацией должен быть вызван");
        assertTrue(log.contains("logMessage called"),
                "Публичный метод с аннотацией должен быть вызван");
        assertTrue(log.contains("privateMethod called"),
                "Приватный метод с аннотацией должен быть вызван");
        assertTrue(log.contains("methodWithReturnValue called"),
                "Метод с возвращаемым значением и аннотацией должен быть вызван");
        assertTrue(log.contains("methodWithException called"),
                "Метод, который может выбрасывать исключение, должен быть вызван");

        // проверка, что НЕ аннотированный метод НЕ был вызван:
        assertFalse(log.contains("notAnnotatedMethod called"),
                "Метод без аннотации НЕ должен вызываться");

        // Проверяем изменение состояния объекта после вызова методов
        assertEquals(1, testObject.getCounter(),
                "Счетчик должен быть увеличен на 1 после вызова incrementCounter()");
    }

    /**
     * Тест проверяет обработку исключений в аннотированных методах
     */
    @Test
    void testInvokeMethodWithException() {

        testObject.setShouldThrowException(true);

        // ожидаем InvocationTargetException
        InvocationTargetException exception = assertThrows(InvocationTargetException.class, () -> {
            InvokeHandler.invokeAnnotatedMethods(testObject);
        });

        // проверка, что внутри наше исключение
        assertTrue(exception.getCause() instanceof IllegalStateException,
                "Причина исключения должна быть IllegalStateException");
        assertEquals("Test exception from annotated method",
                exception.getCause().getMessage(),
                "Сообщение исключения должно совпадать");

        // проверяем, что некоторые методы были вызваны ДО возникновения исключения
        String log = executionLog.toString();
        assertTrue(log.contains("incrementCounter called"),
                "Методы до исключения должны быть вызваны");
        assertTrue(log.contains("methodWithException called"),
                "Метод, выбрасывающий исключение, должен быть вызван");
    }

    /**
     * Тест проверяет сохранение состояния объекта после многократного
     * вызова аннотированных методов
     */
    @Test
    void testObjectStateAfterInvocation() throws Exception {
        // исключение не будет выброшено
        testObject.setShouldThrowException(false);

        // начальное состояние объекта
        assertEquals(0, testObject.getCounter(),
                "Начальное значение счетчика должно быть 0");

        // вызываем аннотированные методы дважды
        InvokeHandler.invokeAnnotatedMethods(testObject);
        InvokeHandler.invokeAnnotatedMethods(testObject);

        // проверка состояние объекта после двух вызовов
        assertEquals(2, testObject.getCounter(),
                "Счетчик должен быть увеличен дважды (1 + 1)");

        // проверка, что метод действительно вызывался 2 раза
        // путем подсчета вхождений в логе
        String log = executionLog.toString();
        int count = countOccurrences(log, "incrementCounter called");
        assertEquals(2, count,
                "Метод incrementCounter должен быть вызван ровно 2 раза");
    }

    // метод для подсчета вхождений подстроки в строке
    private int countOccurrences(String text, String substring) {
        int count = 0;
        int index = 0;

        // все вхождения подстроки в тексте
        while ((index = text.indexOf(substring, index)) != -1) {
            count++;
            index += substring.length(); // двигаемся дальше
        }

        return count;
    }
}