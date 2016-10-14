package voliy.samples.dog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import voliy.samples.dog.dao.DogDao;
import voliy.samples.dog.model.Dog;

import java.util.Collection;

public class DogService {
    private static final Logger logger = LoggerFactory.getLogger(DogService.class);

    private final DogDao dogDao;

    public DogService(DogDao dogDao) {
        this.dogDao = dogDao;
    }

    @Transactional(readOnly = true)
    public Collection<Dog> dogs() {
        logger.info("Load all dogs");
        return dogDao.dogs();
    }

    @Transactional(readOnly = true)
    public Dog get(int id) {
        logger.info("Load dog with id = {}", id);
        return dogDao.get(id);
    }

    @Transactional public Dog add(Dog dog) {
        logger.info("Add dog [{}]", dog);
        return dogDao.add(dog);
    }

    @Transactional public void update(Dog dog) {
        logger.info("Update dog [{}]", dog);
        dogDao.update(dog);
    }

    @Transactional public void delete(int id) {
        logger.info("Delete dog with id = {}", id);
        dogDao.delete(id);
    }
}
