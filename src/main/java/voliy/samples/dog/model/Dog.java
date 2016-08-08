package voliy.samples.dog.model;

import java.util.ArrayList;
import java.util.List;

public class Dog {
    private int id;
    private String name;

    public Dog() {}

    public Dog(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<Dog> samples() {
        String[] names = {"Cooper", "Abby", "Lucky", "Oscar", "Angel"};
        List<Dog> dogs = new ArrayList<Dog>();
        for (int i = 0; i < names.length; i++) {
            dogs.add(new Dog(i + 1, names[i]));
        }
        return dogs;
    }
}
