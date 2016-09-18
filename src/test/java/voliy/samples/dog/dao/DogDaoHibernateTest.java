package voliy.samples.dog.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;
import voliy.samples.dog.model.Dog;

import java.util.Arrays;
import java.util.Collection;

import static org.testng.AssertJUnit.assertNull;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static org.unitils.reflectionassert.ReflectionComparatorMode.LENIENT_ORDER;

@ContextConfiguration(locations = {"classpath:dao-context.xml"})
public class DogDaoHibernateTest extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired private DogDao dogDao;

    @Autowired private SessionFactory sessionFactory;

    @Test public void savesAndLoadsDog() {
        Dog expected = addDog(Dog.random());
        Dog actual = loadDog(expected.getId());
        assertReflectionEquals(expected, actual);
    }

    @Test public void addsDogAndUpdatesItsFields() {
        Dog expected = addDog(Dog.random());
        copyFields(Dog.random(), expected);
        updateDog(expected);
        Dog actual = dogDao.get(expected.getId());
        assertReflectionEquals(expected, actual);
    }

    @Test public void savesAndDeletesDog() {
        Dog dog = addDog(Dog.random());
        deleteDog(dog.getId());
        dog = loadDog(dog.getId());
        assertNull(dog);
    }

    @Test public void addsDogsAndLoadsAllOfThem() {
        Dog firstDog = addDog(Dog.random());
        Dog secondDog = addDog(Dog.random());
        Collection<Dog> allDogs = loadAllDogs();
        assertReflectionEquals(Arrays.asList(secondDog, firstDog), allDogs, LENIENT_ORDER);
    }

    private Dog addDog(Dog dog) {
        clearSession();
        dogDao.add(dog);
        return dog;
    }

    private Dog loadDog(Integer id) {
        clearSession();
        return dogDao.get(id);
    }

    private Collection<Dog> loadAllDogs() {
        clearSession();
        return dogDao.dogs();
    }

    private void updateDog(Dog dog) {
        clearSession();
        dogDao.update(dog);
    }

    private void deleteDog(Integer id) {
        clearSession();
        dogDao.delete(id);
    }

    private void copyFields(Dog from, Dog to) {
        to.setName(from.getName());
        to.setBirthDate(from.getBirthDate());
        to.setHeight(from.getHeight());
        to.setWeight(from.getWeight());
    }

    private void clearSession() {
        sessionFactory.getCurrentSession().clear();
    }
}