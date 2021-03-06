package voliy.samples.dog.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static io.qala.datagen.RandomShortApi.alphanumeric;
import static io.qala.datagen.RandomShortApi.positiveDouble;
import static io.qala.datagen.RandomValue.between;

public class Dog {
    private Integer id;

    @NotBlank
    @Size(min = 1, max = 100, message = "Dog name size must be between {min} and {max}")
    private String name;

    @Past
    private Date birthDate;

    @NotNull
    @DecimalMin(value = "0", inclusive = false)
    private Double height;

    @NotNull
    @DecimalMin(value = "0", inclusive = false)
    private Double weight;

    public static final String[] names = {"Cooper", "Abby", "Lucky", "Oscar", "Angel"};

    private static final Dog[] SAMPLE_DOGS = {
            new Dog(names[0], prepareDate(2014, Month.JANUARY, 5), 55.0, 10.0),
            new Dog(names[1], prepareDate(2014, Month.JUNE, 12), 45.0, 12.0),
            new Dog(names[2], prepareDate(2014, Month.NOVEMBER, 23), 40.0, 8.0),
            new Dog(names[3], prepareDate(2015, Month.MAY, 17), 25.0, 5.0),
            new Dog(names[4], prepareDate(2016, Month.MARCH, 15), 20.0, 3.0)
    };

    public Dog() {}

    public Dog(String name, Date birthDate, Double height, Double weight) {
        this.name = name;
        this.birthDate = birthDate;
        this.height = height;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return String.format("Dog(id=%s, name=%s, birthDate=%s, height=%s, weight=%s)", this.getId(), this.getName(),
                this.getBirthDate(), this.getHeight(), this.getWeight());
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

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public static List<Dog> samples() {
        List<Dog> dogs = new ArrayList<>();
        for (Dog sampleDog : SAMPLE_DOGS) {
            dogs.add(prepareDogFromSample(sampleDog));
        }
        return dogs;
    }

    public static Dog random() {
        Dog dog = new Dog();
        dog.setName(alphanumeric(1, 100));
        dog.setBirthDate(between(Long.MIN_VALUE, new Date().getTime()).date());
        dog.setHeight(positiveDouble());
        dog.setWeight(positiveDouble());
        return dog;
    }

    private static Date prepareDate(int year, Month month, int dayOfMonth) {
        return Date.from(LocalDate.of(year, month, dayOfMonth).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private static Dog prepareDogFromSample(Dog dog) {
        return new Dog(dog.name, dog.birthDate, dog.height, dog.weight);
    }
}
