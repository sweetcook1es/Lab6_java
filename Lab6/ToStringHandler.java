package Lab6;

import java.lang.reflect.Field;


// Обработчик аннотации @ToString
public class ToStringHandler {

    public static String generateToString(Object obj) {
        if (obj == null) {
            return "null";
        }

        Class<?> clazz = obj.getClass();

        // для построения строки
        StringBuilder sb = new StringBuilder();

        // проверяем есть ли аннотация @ToString на классе
        boolean classHasToString = clazz.isAnnotationPresent(ToString.class);
        // устанавливаем режим по умолчанию (если нет аннотации на классе)
        ToString.Mode defaultMode = ToString.Mode.YES;


        // если класс имеет аннотацию @ToString
        if (classHasToString) {
            // получаем объект аннотации @ToString
            ToString annotation = clazz.getAnnotation(ToString.class);
            // извлекаем значение параметра value() из аннотации
            defaultMode = annotation.value();
        }

        sb.append(clazz.getSimpleName()).append("{");

        // получаем массив всех полей, объявленных в этом классе
        Field[] fields = clazz.getDeclaredFields();
        boolean firstField = true;

        for (Field field : fields) {
            // устанавливаем режим для текущего поля равным режиму по умолчанию
            ToString.Mode fieldMode = defaultMode;

            // проверяем аннотацию на поле
            if (field.isAnnotationPresent(ToString.class)) {
                ToString fieldAnnotation = field.getAnnotation(ToString.class);
                fieldMode = fieldAnnotation.value();
            }

            // если режим NO - пропускаем поле
            if (fieldMode == ToString.Mode.NO) {
                continue;
            }

            try {
                // разрешаем доступ к приватному полю
                field.setAccessible(true);
                Object value = field.get(obj);

                if (!firstField) {
                    sb.append(", ");
                }

                sb.append(field.getName()).append("=");

                if (value instanceof String) {
                    sb.append("\"").append(value).append("\"");
                } else {
                    sb.append(value);
                }

                firstField = false;
            } catch (IllegalAccessException e) {
                sb.append(field.getName()).append("=[недоступно]");
                if (!firstField) {
                    firstField = false;
                }
            }
        }

        sb.append("}");
        return sb.toString();
    }
}