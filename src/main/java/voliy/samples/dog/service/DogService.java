package voliy.samples.dog.service;

import voliy.samples.dog.model.Dog;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class DogService {
    private Map<Integer, Dog> dogs = new LinkedHashMap<Integer, Dog>();

    public Collection<Dog> dogs() {
        return dogs.values();
    }

    public Dog get(int id) {
        return dogs.get(id);
    }

    public void add(Dog dog) {
        if (dog.getId() == null) {
            dog.setId(nextId());
            dogs.put(dog.getId(), dog);
        }
    }

    public void delete(int id) {
        dogs.remove(id);
    }

    public void init() {
        for (Dog dog : Dog.samples()) {
            add(dog);
        }
    }

    private int nextId() {
        return dogs.isEmpty() ? 1 : Collections.max(dogs.keySet()) + 1;
    }
}
