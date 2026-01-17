package Lab6;

// Обработчик аннотации @Two
public class TwoHandler {

    public static void printTwoValues(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Объект не может быть null");
        }

        Class<?> clazz = obj.getClass();

        System.out.println("\nОбработка аннотации @Two");
        System.out.println("Класс: " + clazz.getSimpleName());

        if (clazz.isAnnotationPresent(Two.class)) {
            Two annotation = clazz.getAnnotation(Two.class);
            System.out.println("Свойство first: \"" + annotation.first() + "\"");
            System.out.println("Свойство second: " + annotation.second());
        } else {
            System.out.println("Аннотация @Two не найдена");
        }
    }
}