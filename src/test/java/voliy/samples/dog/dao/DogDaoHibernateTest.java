package voliy.samples.dog.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;
import voliy.samples.dog.model.Dog;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import static io.qala.datagen.RandomShortApi.*;
import static io.qala.datagen.RandomValue.between;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNull;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;
import static org.unitils.reflectionassert.ReflectionComparatorMode.LENIENT_ORDER;

@ContextConfiguration(locations = {"classpath:context.xml"})
public class DogDaoHibernateTest extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired private DogDao dogDao;
    @Autowired private SessionFactory sessionFactory;

    @Test public void savesAndLoadsDog() {
        Dog expected = addDog(Dog.random());
        flushAndClear();

        Dog actual = getDog(expected.getId());
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

        Dog actual = getDog(dogId);
        assertReflectionEquals(expected, actual);
    }

    @Test public void savesAndDeletesDog() {
        Dog dog = addDog(Dog.random());
        flushAndClear();

        int dogId = dog.getId();
        deleteDog(dogId);
        flushAndClear();

        dog = getDog(dogId);
        assertNull(dog);
    }

    @Test public void addsDogsAndLoadsAllOfThem() {
        Dog firstDog = addDog(Dog.random());
        Dog secondDog = addDog(Dog.random());
        flushAndClear();

        Collection<Dog> allDogs = loadAllDogs();
        assertReflectionEquals(Arrays.asList(secondDog, firstDog), allDogs, LENIENT_ORDER);
    }

    @Test public void savesDog_withNameSizeBetween1and100() {
        Dog expected = Dog.random();
        expected.setName(alphanumeric(1, 100));
        expected = addDog(expected);
        flushAndClear();

        Dog actual = getDog(expected.getId());
        assertEquals(actual.getName(), expected.getName());
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void errorsWhenSavesDog_withNameSizeMoreThan100() {
        Dog expected = Dog.random();
        expected.setName(alphanumeric(101, 1000));
        addDog(expected);
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void errorsWhenSavesDog_withNullOrEmptyName() {
        Dog expected = Dog.random();
        expected.setName(nullOrEmpty());
        addDog(expected);
    }

    @Test public void savesDog_withBirthDateBeforeNow() {
        Dog expected = Dog.random();
        expected.setBirthDate(between(Long.MIN_VALUE, new Date().getTime()).date());
        expected = addDog(expected);
        flushAndClear();

        Dog actual = getDog(expected.getId());
        assertEquals(actual.getBirthDate(), expected.getBirthDate());
    }

    @Test public void savesDog_withNullBirthDate() {
        Dog expected = Dog.random();
        expected.setBirthDate(null);
        expected = addDog(expected);
        flushAndClear();

        Dog actual = getDog(expected.getId());
        assertEquals(actual.getBirthDate(), expected.getBirthDate());
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void errorsWhenSavesDog_withBirthDateAfterNow() {
        Dog expected = Dog.random();
        expected.setBirthDate(between(new Date().getTime() + 3000, Long.MAX_VALUE).date());
        expected = addDog(expected);
        flushAndClear();

        Dog actual = getDog(expected.getId());
        assertEquals(actual.getBirthDate(), expected.getBirthDate());
    }

    @Test public void savesDog_withPositiveHeight() {
        Dog expected = Dog.random();
        expected.setHeight(positiveDouble());
        expected = addDog(expected);
        flushAndClear();

        Dog actual = getDog(expected.getId());
        assertEquals(actual.getHeight(), expected.getHeight());
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void errorsWhenSavesDog_withNullOrZeroHeight() {
        Dog expected = Dog.random();
        expected.setHeight(sample(0d, null));
        addDog(expected);
    }

    @Test public void savesDog_withPositiveWeight() {
        Dog expected = Dog.random();
        expected.setWeight(positiveDouble());
        expected = addDog(expected);
        flushAndClear();

        Dog actual = getDog(expected.getId());
        assertEquals(actual.getWeight(), expected.getWeight());
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void errorsWhenSavesDog_withNullOrZeroWeight() {
        Dog expected = Dog.random();
        expected.setWeight(sample(0d, null));
        addDog(expected);
    }

    private Dog addDog(Dog dog) {
        dogDao.add(dog);
        return dog;
    }

    private Dog getDog(Integer id) {
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