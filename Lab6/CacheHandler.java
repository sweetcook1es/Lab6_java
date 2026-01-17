package Lab6;

// Обработчик аннотации @Cache
public class CacheHandler {

    public static void printCacheAreas(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Объект не может быть null");
        }

        Class<?> clazz = obj.getClass();

        System.out.println("\nОбработка аннотации @Cache");
        System.out.println("Класс: " + clazz.getSimpleName());

        if (clazz.isAnnotationPresent(Cache.class)) {
            Cache annotation = clazz.getAnnotation(Cache.class);
            String[] areas = annotation.value();

            if (areas.length == 0) {
                System.out.println("Список кешируемых областей пуст (значение по умолчанию)");
            } else {
                System.out.println("Кешируемые области (" + areas.length + "):");
                for (int i = 0; i < areas.length; i++) {
                    System.out.println("  " + (i + 1) + ". " + areas[i]);
                }
            }
        } else {
            System.out.println("Аннотация @Cache не найдена");
        }
    }
}