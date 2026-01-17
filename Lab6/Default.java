package Lab6;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD}) // применяется к ТИПАМ (классы, интерфейсы), ПОЛЯМ (переменные класса)
@Retention(RetentionPolicy.RUNTIME) // доступна через Reflection во время выполнения
public @interface Default {
    Class<?> value();
}