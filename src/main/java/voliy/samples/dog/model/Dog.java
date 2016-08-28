package voliy.samples.dog.model;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Dog {
    private Integer id;
    private String name;
    private Date birthDate;
    private double height;
    private double weight;

    public static final String[] names = {"Cooper", "Abby", "Lucky", "Oscar", "Angel"};

    private static final Dog[] SAMPLE_DOGS = {
            new Dog(names[0], prepareDate(2014, Month.JANUARY, 5), 55, 10),
            new Dog(names[1], prepareDate(2014, Month.JUNE, 12), 45, 12),
            new Dog(names[2], prepareDate(2014, Month.NOVEMBER, 23), 40, 8),
            new Dog(names[3], prepareDate(2015, Month.MAY, 17), 25, 5),
            new Dog(names[4], prepareDate(2016, Month.MARCH, 15), 20, 3)
    };

    public Dog() {}

    public Dog(String name, Date birthDate, double height, double weight) {
        this.name = name;
        this.birthDate = birthDate;
        this.height = height;
        this.weight = weight;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public static List<Dog> samples() {
        List<Dog> dogs = new ArrayList<>();
        for (Dog sampleDog : SAMPLE_DOGS) {
            dogs.add(prepareDogFromSample(sampleDog));
        }
        return dogs;
    }

    private static Date prepareDate(int year, Month month, int dayOfMonth) {
        return Date.from(LocalDate.of(year, month, dayOfMonth).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private static Dog prepareDogFromSample(Dog dog) {
        return new Dog(dog.name, dog.birthDate, dog.height, dog.weight);
    }
}
