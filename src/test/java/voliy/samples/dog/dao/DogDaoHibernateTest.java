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
        flushAndClear();

        Dog actual = loadDog(expected.getId());
        assertReflectionEquals(expected, actual);
    }

    @Test public void addsDogAndUpdatesItsFields() {
        Dog expected = addDog(Dog.random());
        flushAndClear();

        int dogId = expected.getId();
        expected = Dog.random();
        expected.setId(dogId);
        updateDog(expected);
        flushAndClear();

        Dog actual = loadDog(dogId);
        assertReflectionEquals(expected, actual);
    }

    @Test public void savesAndDeletesDog() {
        Dog dog = addDog(Dog.random());
        flushAndClear();

        int dogId = dog.getId();
        deleteDog(dogId);
        flushAndClear();

        dog = loadDog(dogId);
        assertNull(dog);
    }

    @Test public void addsDogsAndLoadsAllOfThem() {
        Dog firstDog = addDog(Dog.random());
        Dog secondDog = addDog(Dog.random());
        flushAndClear();

        Collection<Dog> allDogs = loadAllDogs();
        assertReflectionEquals(Arrays.asList(secondDog, firstDog), allDogs, LENIENT_ORDER);
    }

    private Dog addDog(Dog dog) {
        dogDao.add(dog);
        return dog;
    }

    private Dog loadDog(Integer id) {
        return dogDao.get(id);
    }

    private Collection<Dog> loadAllDogs() {
        return dogDao.dogs();
    }

    private void updateDog(Dog dog) {
        dogDao.update(dog);
    }

    private void deleteDog(Integer id) {
        dogDao.delete(id);
    }

    private void flushAndClear() {
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
    }
}