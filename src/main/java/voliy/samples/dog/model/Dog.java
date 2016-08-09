package voliy.samples.dog.model;

import java.util.ArrayList;
import java.util.List;

public class Dog {
    private Integer id;
    private String name;

    public static final String[] names = {"Cooper", "Abby", "Lucky", "Oscar", "Angel"};

    public Dog() {}

    public Dog(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Dog> samples() {
        List<Dog> dogs = new ArrayList<Dog>();
        for (int i = 0; i < names.length; i++) {
            dogs.add(new Dog(names[i]));
        }
        return dogs;
    }
}
