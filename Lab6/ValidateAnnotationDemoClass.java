package Lab6;


@Validate({String.class, Integer.class, Double.class})
public class ValidateAnnotationDemoClass {
    private String data;

    public ValidateAnnotationDemoClass(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}