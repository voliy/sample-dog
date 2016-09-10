package voliy.samples.dog.service;

import org.springframework.transaction.annotation.Transactional;
import voliy.samples.dog.dao.DogDao;
import voliy.samples.dog.model.Dog;

import java.util.Collection;

public class DogService {
    private DogDao dogDao;

    public DogService(DogDao dogDao) {
        this.dogDao = dogDao;
    }

    @Transactional(readOnly = true)
    public Collection<Dog> dogs() {
        return dogDao.dogs();
    }

    @Transactional(readOnly = true)
    public Dog get(int id) {
        return dogDao.get(id);
    }

    @Transactional public void add(Dog dog) {
        dogDao.add(dog);
    }

    @Transactional public void update(Dog dog) {
        dogDao.update(dog);
    }

    @Transactional public void delete(int id) {
        dogDao.delete(id);
    }
}
