package Lab6;

import java.lang.annotation.*;

@Target(ElementType.TYPE) // применяется к ТИПАМ (классы, интерфейсы)
@Retention(RetentionPolicy.RUNTIME) // доступна через Reflection во время выполнения
public @interface Two {
    String first();
    int second();
}