package voliy.samples.dog.dao;

import voliy.samples.dog.model.Dog;

import java.util.Collection;

public interface DogDao {
    Collection<Dog> dogs();

    Dog get(int id);

    Dog add(Dog dog);

    void update(Dog dog);

    void delete(int id);
}
