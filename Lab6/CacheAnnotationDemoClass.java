package Lab6;

@Cache({"session", "database", "filesystem"})
public class CacheAnnotationDemoClass {
    private String data;

    public CacheAnnotationDemoClass(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}