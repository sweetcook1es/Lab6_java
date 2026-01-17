package Lab6;

// Аннотация применяется ко всему классу
@Two(first = "Пример строки", second = 42)
public class TwoAnnotationDemoClass {
    private String info;

    public TwoAnnotationDemoClass(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}