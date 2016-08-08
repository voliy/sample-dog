package voliy.samples.dog.service;

import voliy.samples.dog.model.Dog;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class DogService {
    private Map<Integer, Dog> dogs;

    public Collection<Dog> dogs() {
        return dogs.values();
    }

    public void add(Dog dog) {
        dogs.put(dog.getId(), dog);
    }

    public void delete(int id) {
        dogs.remove(id);
    }

    private void init() {
        dogs = new LinkedHashMap<Integer, Dog>();
        for (Dog dog : Dog.samples()) {
            add(dog);
        }
    }
}
