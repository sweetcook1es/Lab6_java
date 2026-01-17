package Lab6;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})  // применяется к ТИПАМ (классы, интерфейсы), другая аннотация
@Retention(RetentionPolicy.RUNTIME) // доступна через Reflection во время выполнения
public @interface Validate {
    Class<?>[] value();
}