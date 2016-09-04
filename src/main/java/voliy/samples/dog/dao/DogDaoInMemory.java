package voliy.samples.dog.dao;

import voliy.samples.dog.model.Dog;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DogDaoInMemory implements DogDao {
    private AtomicInteger nextId = new AtomicInteger(0);
    private Map<Integer, Dog> dogs = new ConcurrentHashMap<>();

    @Override
    public Collection<Dog> dogs() {
        return dogs.values();
    }

    @Override
    public void init() {
        Dog.samples().forEach(this::add);
    }

    @Override
    public Dog get(int id) {
        return dogs.get(id);
    }

    @Override
    public void add(Dog dog) {
        if (dog.getId() == null) {
            dog.setId(nextId.incrementAndGet());
            dogs.put(dog.getId(), dog);
        }
    }

    @Override
    public void update(Dog dog) {
        Integer dogId = dog.getId();
        if (dogId == null) {
            throw new IllegalArgumentException("Dog id isn't defined");
        }
        if (!dogs.containsKey(dogId)) {
            throw new IllegalArgumentException("There is no dog with id = " + dogId);
        }
        dogs.put(dogId, dog);
    }

    @Override
    public void delete(int id) {
        dogs.remove(id);
    }
}
