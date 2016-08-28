package voliy.samples.dog.service;

import voliy.samples.dog.dao.DogDao;
import voliy.samples.dog.model.Dog;

import java.util.Collection;

public class DogService {
    private DogDao dogDao;

    public DogService(DogDao dogDao) {
        this.dogDao = dogDao;
    }

    public Collection<Dog> dogs() {
        return dogDao.dogs();
    }

    public Dog get(int id) {
        return dogDao.get(id);
    }

    public void add(Dog dog) {
        dogDao.add(dog);
    }

    public void update(Dog dog) {
        dogDao.update(dog);
    }

    public void delete(int id) {
        dogDao.delete(id);
    }
}
