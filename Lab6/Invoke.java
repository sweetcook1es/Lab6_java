package Lab6;

import java.lang.annotation.*;

@Target(ElementType.METHOD)        // применима только к методам
@Retention(RetentionPolicy.RUNTIME) // доступна через Reflection во время выполнения
public @interface Invoke {

}