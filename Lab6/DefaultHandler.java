package Lab6;

import java.lang.reflect.Field;


// Обработчик аннотации @Default
public class DefaultHandler {

    public static void printDefaultClass(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Объект не может быть null");
        }

        Class<?> clazz = obj.getClass();

        System.out.println("\nОбработка аннотации @Default");
        System.out.println("Класс: " + clazz.getSimpleName());

        // проверка наличия аннотации @Default на самом классе
        if (clazz.isAnnotationPresent(Default.class)) {
            // получение объекта аннотации @Default
            Default annotation = clazz.getAnnotation(Default.class);
            System.out.println("Класс по умолчанию: " + annotation.value().getName());
        } else {
            System.out.println("Аннотация @Default не найдена на классе");
        }

        // проверка аннотаций на полях класса
        Field[] fields = clazz.getDeclaredFields(); // возвращает все поля
        for (Field field : fields) {
            if (field.isAnnotationPresent(Default.class)) {
                // получение объекта аннотации @Default
                Default annotation = field.getAnnotation(Default.class);
                System.out.println("Поле '" + field.getName() +
                        "' имеет класс по умолчанию: " + annotation.value().getName());
            }
        }
    }
}