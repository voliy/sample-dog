package voliy.samples.dog.service;

import org.springframework.transaction.annotation.Transactional;
import voliy.samples.dog.dao.DogDao;
import voliy.samples.dog.model.Dog;

import java.util.Collection;
import java.util.List;

public class DogService {
    private DogDao dogDao;

    public DogService(DogDao dogDao) {
        this.dogDao = dogDao;
    }

    public void init() {
        add(Dog.samples());
    }

    public Collection<Dog> dogs() {
        return dogDao.dogs();
    }

    public Dog get(int id) {
        return dogDao.get(id);
    }

    @Transactional
    public void add(Dog dog) {
        dogDao.add(dog);
    }

    @Transactional
    private void add(List<Dog> dogs) {
        for (Dog dog : dogs) {
            dogDao.add(dog);
        }
    }

    public void update(Dog dog) {
        dogDao.update(dog);
    }

    public void delete(int id) {
        dogDao.delete(id);
    }
}
