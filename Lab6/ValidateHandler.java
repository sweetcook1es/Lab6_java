package Lab6;

// Обработчик аннотации @Validate
public class ValidateHandler {

    public static void printValidationClasses(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Объект не может быть null");
        }

        Class<?> clazz = obj.getClass();

        System.out.println("\nОбработка аннотации @Validate");
        System.out.println("Класс: " + clazz.getSimpleName());

        // проверка наличия аннотации @Validate на классе
        if (clazz.isAnnotationPresent(Validate.class)) {
            Validate annotation = clazz.getAnnotation(Validate.class);
            Class<?>[] classes = annotation.value();

            if (classes.length == 0) {
                throw new IllegalArgumentException("Список классов для валидации пуст");
            } else {
                System.out.println("Классы (" + classes.length + "):");
                for (int i = 0; i < classes.length; i++) {
                    System.out.println("  " + (i + 1) + ". " + classes[i].getName());
                }
            }
        } else {
            System.out.println("Аннотация @Validate не найдена");
        }
    }
}