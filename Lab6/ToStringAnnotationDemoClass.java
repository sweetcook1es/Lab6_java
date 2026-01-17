package Lab6;

@ToString
public class ToStringAnnotationDemoClass {
    private String name;
    private int age;

    // @ToString с явным указанием Mode.NO
    // означает никогда не включать это поле в строковое представление
    @ToString(ToString.Mode.NO)
    private String password;

    @ToString
    private String email;

    public ToStringAnnotationDemoClass(String name, int age, String password, String email) {
        this.name = name;
        this.age = age;
        this.password = password;
        this.email = email;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}