package Lab6;

import java.lang.reflect.Method;


// Обработчик аннотации @Invoke
public class InvokeHandler {

    public static void invokeAnnotatedMethods(Object obj) throws Exception {

        if (obj == null) {
            throw new IllegalArgumentException("Объект не может быть null");
        }

        Class<?> clazz = obj.getClass();

        Method[] methods = clazz.getDeclaredMethods(); // все методы, объявленные именно в этом классе

        System.out.println("\nОбработка аннотации @Invoke");
        System.out.println("Класс: " + clazz.getSimpleName());

        int invokedCount = 0;  // счетчик успешно вызванных методов

        // проходим по всем методам класса
        for (Method method : methods) {
            // проверяет, присутствует ли указанная аннотация
            if (method.isAnnotationPresent(Invoke.class)) {

                method.setAccessible(true); // позволяет обойти проверки доступа Java для private, protected

                System.out.print("Вызов метода: " + method.getName() + " -> ");

                method.invoke(obj); // вызывает метод

                invokedCount++;
            }
        }

        if (invokedCount == 0) {
            System.out.println("Методы с аннотацией @Invoke не найдены");
        } else {
            System.out.println("Всего вызвано методов: " + invokedCount);
        }
    }
}