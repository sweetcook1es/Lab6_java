package Lab6;


@Default(Integer.class)
public class DefaultAnnotationDemoClass {
    @Default(String.class)
    private String field;

    public DefaultAnnotationDemoClass() {
        this.field = "default value";
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}