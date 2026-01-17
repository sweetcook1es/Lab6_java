package Lab6;

public class InvokeAnnotationDemoClass {

    @Invoke
    public void method1() {
        System.out.println("Метод 1 вызван через аннотацию @Invoke");
    }

    public void method2() {
        System.out.println("Метод 2 НЕ вызван (нет аннотации @Invoke)");
    }

    @Invoke
    private void privateMethod() {
        System.out.println("Приватный метод вызван через аннотацию @Invoke");
    }
}