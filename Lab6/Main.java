package Lab6;

import java.util.Scanner;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            printMenu();
            System.out.print("\nВыберите действие (1-8): ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        demoInvoke();
                        break;
                    case "2":
                        demoDefault();
                        break;
                    case "3":
                        demoToString();
                        break;
                    case "4":
                        demoValidate();
                        break;
                    case "5":
                        demoTwo();
                        break;
                    case "6":
                        demoCache();
                        break;
                    case "7":
                        runAllTests();
                        break;
                    case "0":
                        exit = true;
                        System.out.println("\nВыход из программы");
                        break;
                    default:
                        System.out.println("\nНекорректный выбор. Попробуйте снова.");
                }
            } catch (Exception e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\nМЕНЮ:");
        System.out.println("1. Демонстрация @Invoke");
        System.out.println("2. Демонстрация @Default");
        System.out.println("3. Демонстрация @ToString");
        System.out.println("4. Демонстрация @Validate");
        System.out.println("5. Демонстрация @Two");
        System.out.println("6. Демонстрация @Cache");
        System.out.println("7. Запустить все тесты JUnit");
        System.out.println("0. Выход");
    }

    private static void demoInvoke() throws Exception {
        System.out.println("\nДемонстрация @Invoke");
        InvokeAnnotationDemoClass demo = new InvokeAnnotationDemoClass();
        InvokeHandler.invokeAnnotatedMethods(demo);
    }

    private static void demoDefault() {
        System.out.println("\nДемонстрация @Default");
        DefaultAnnotationDemoClass demo = new DefaultAnnotationDemoClass();
        DefaultHandler.printDefaultClass(demo);

        System.out.println("\nПроверка на классе без аннотации:");
        ToStringAnnotationDemoClass toStringDemo = new ToStringAnnotationDemoClass("Тест", 25, "pass", "test@mail.ru");
        DefaultHandler.printDefaultClass(toStringDemo);
    }

    private static void demoToString() {
        System.out.println("\nДемонстрация @ToString");

        ToStringAnnotationDemoClass demo = new ToStringAnnotationDemoClass("Иван Иванов", 30, "secret123", "ivan@example.com");

        System.out.println("Объект ToStringAnnotationDemoClass создан:");
        System.out.println("  Имя: " + demo.getName());
        System.out.println("  Возраст: " + demo.getAge());
        System.out.println("  Пароль: " + demo.getPassword());
        System.out.println("  Email: " + demo.getEmail());

        System.out.println("\nСтроковое представление (с учетом @ToString):");
        String result = ToStringHandler.generateToString(demo);
        System.out.println("  " + result);

        System.out.println("\nКласс без аннотации @ToString");
        DefaultAnnotationDemoClass defaultDemo = new DefaultAnnotationDemoClass();
        String defaultResult = ToStringHandler.generateToString(defaultDemo);
        System.out.println("Результат: " + defaultResult);
    }

    private static void demoValidate() {
        System.out.println("\nДемонстрация @Validate");

        try {
            ValidateAnnotationDemoClass demo = new ValidateAnnotationDemoClass("Тестовые данные");
            ValidateHandler.printValidationClasses(demo);
        } catch (IllegalArgumentException e) {
            System.out.println("Поймано ожидаемое исключение: " + e.getMessage());
        }

        System.out.println("\nПроверка на классе без аннотации:");
        ToStringAnnotationDemoClass toStringDemo = new ToStringAnnotationDemoClass("Тест", 25, "pass", "test@mail.ru");
        ValidateHandler.printValidationClasses(toStringDemo);
    }

    private static void demoTwo() {
        System.out.println("\nДемонстрация @Two");
        TwoAnnotationDemoClass demo = new TwoAnnotationDemoClass("Дополнительная информация");
        TwoHandler.printTwoValues(demo);

        System.out.println("\nПроверка на классе без аннотации:");
        ToStringAnnotationDemoClass toStringDemo = new ToStringAnnotationDemoClass("Тест", 25, "pass", "test@mail.ru");
        TwoHandler.printTwoValues(toStringDemo);
    }

    private static void demoCache() {
        System.out.println("\nДемонстрация @Cache");
        CacheAnnotationDemoClass cacheDemo = new CacheAnnotationDemoClass("Кешируемые данные");
        CacheHandler.printCacheAreas(cacheDemo);

        System.out.println("\nПроверка на классе без аннотации:");
        ToStringAnnotationDemoClass toStringDemo = new ToStringAnnotationDemoClass("Тест", 25, "pass", "test@mail.ru");
        CacheHandler.printCacheAreas(toStringDemo);
    }

    private static void runAllTests() {
        System.out.println("\nЗапуск тестов JUnit\n");

        try {
            // запрос на поиск и запуск тестов с использованием JUnit 5 API
            LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                    // конкретные тестовые классы для запуска
                    .selectors(
                            selectClass(InvokeTest.class),
                            selectClass(ValidateTest.class)
                    )
                    // завершаем построение запроса
                    .build();

            // основной компонент JUnit для выполнения тестов
            Launcher launcher = LauncherFactory.create();

            // собирает статистику выполнения тестов
            SummaryGeneratingListener listener = new SummaryGeneratingListener();

            // регистрация listener, чтобы он получал уведомления о событиях тестирования
            launcher.registerTestExecutionListeners(listener);

            System.out.println("Запускаем тесты\n");
            launcher.execute(request);

            // сводка результатов выполнения тестов из listener
            TestExecutionSummary summary = listener.getSummary();


            System.out.println("Всего тестов: " + summary.getTestsFoundCount());
            System.out.println("Успешно: " + summary.getTestsSucceededCount());
            System.out.println("Провалено: " + summary.getTestsFailedCount());

            System.out.println("\nЗапуск тестов завершен");

        } catch (Exception e) {
            System.err.println("Ошибка при запуске тестов: " + e.getMessage());
        }
    }
}