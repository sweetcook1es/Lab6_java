package Lab6;


import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;


public class ValidateTest {

    @Validate({String.class, Integer.class, Double.class})
    static class TestValidateClass {
        private String data;

        public TestValidateClass(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }
    }

    @Validate({})
    static class TestEmptyValidateClass {
    }

    /**
     * Тест проверяет корректное извлечение классов из аннотации @Validate
     */
    @Test
    void testValidateAnnotationExtraction() {
        // Перехват вывода в консоль
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            TestValidateClass testObj = new TestValidateClass("test data");
            ValidateHandler.printValidationClasses(testObj);

            String output = outputStream.toString();  // преобразование вывода в строку

            // проверка, что вывод содержит нужную информацию
            assertTrue(output.contains("Обработка аннотации @Validate"));
            assertTrue(output.contains("Класс: TestValidateClass"));
            assertTrue(output.contains("Классы (3):"));
            assertTrue(output.contains("java.lang.String"));
            assertTrue(output.contains("java.lang.Integer"));
            assertTrue(output.contains("java.lang.Double"));
        } finally {
            System.setOut(originalOut);  // восстановление оригинального потока вывода
        }
    }

    /**
     * Тест проверяет обработку пустого массива в @Validate
     */
    @Test
    void testEmptyValidateArray() {
        TestEmptyValidateClass testObj = new TestEmptyValidateClass();

        // проверка, что метод выбрасывает исключение
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,  // ожидаемый тип исключения
                () -> ValidateHandler.printValidationClasses(testObj)
        );

        assertEquals("Список классов для валидации пуст", exception.getMessage());
    }

    /**
     * Тест проверяет обработку класса с непустым массивом в @Validate
     */
    @Test
    void testNonEmptyValidateArray() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            TestValidateClass testObj = new TestValidateClass("test data");

            // не должно быть исключения
            assertDoesNotThrow(() -> {
                ValidateHandler.printValidationClasses(testObj);
            });

            String output = outputStream.toString();
            assertFalse(output.isEmpty());  // проверка, что вывод не пустой
            assertTrue(output.contains("Обработка аннотации @Validate"));  // проверка наличия строки в выводе
        } finally {
            System.setOut(originalOut);
        }
    }


    /**
     * Тест для проверки вывода порядка классов
     */
    @Test
    void testClassOrderInOutput() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            TestValidateClass testObj = new TestValidateClass("test data");
            ValidateHandler.printValidationClasses(testObj);

            String output = outputStream.toString();

            // проверка порядок вывода классов
            String[] lines = output.split("\n");
            int stringIndex = -1, integerIndex = -1, doubleIndex = -1;

            for (int i = 0; i < lines.length; i++) {  // цикл по всем строкам вывода
                if (lines[i].contains("java.lang.String")) stringIndex = i;
                if (lines[i].contains("java.lang.Integer")) integerIndex = i;
                if (lines[i].contains("java.lang.Double")) doubleIndex = i;
            }

            // проверка, что все классы найдены в выводе
            assertTrue(stringIndex > 0);
            assertTrue(integerIndex > 0);
            assertTrue(doubleIndex > 0);

            // проверка порядка
            assertTrue(stringIndex < integerIndex);
            assertTrue(integerIndex < doubleIndex);
        } finally {
            System.setOut(originalOut);
        }
    }
}