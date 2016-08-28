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
        if (dogId != null) {
            Dog dogInStore = dogs.get(dogId);
            if (dog.getName() != null) {
                dogInStore.setName(dog.getName());
            }
            if (dog.getBirthDate() != null) {
                dogInStore.setBirthDate(dog.getBirthDate());
            }
            if (dog.getHeight() != null) {
                dogInStore.setHeight(dog.getHeight());
            }
            if (dog.getWeight() != null) {
                dogInStore.setWeight(dog.getWeight());
            }
        }
    }

    @Override
    public void delete(int id) {
        dogs.remove(id);
    }
}
