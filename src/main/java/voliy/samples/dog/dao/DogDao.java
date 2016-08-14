package voliy.samples.dog.dao;

import voliy.samples.dog.model.Dog;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class DogDao {
    private volatile int nextId;
    private Map<Integer, Dog> dogs = new LinkedHashMap<>();

    public Collection<Dog> dogs() {
        return dogs.values();
    }

    public void init() {
        Dog.samples().forEach(this::add);
    }

    public Dog get(int id) {
        return dogs.get(id);
    }

    public void add(Dog dog) {
        if (dog.getId() == null) {
            dog.setId(++nextId);
            dogs.put(dog.getId(), dog);
        }
    }

    public void delete(int id) {
        dogs.remove(id);
    }
}
