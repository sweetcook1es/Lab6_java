package Lab6;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD}) // применяется к ТИПАМ (классы, интерфейсы), ПОЛЯМ (переменные класса)
@Retention(RetentionPolicy.RUNTIME) // доступна через Reflection во время выполнения
public @interface ToString {
    // перечисление для определения допустимых значений параметра
    enum Mode {
        YES,
        NO
    }

    Mode value() default Mode.YES;
}