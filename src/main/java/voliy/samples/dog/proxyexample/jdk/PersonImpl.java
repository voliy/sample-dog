package voliy.samples.dog.proxyexample.jdk;

public class PersonImpl implements Person {
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void test() {
        System.out.println("JDK-based proxy sample");
    }
}