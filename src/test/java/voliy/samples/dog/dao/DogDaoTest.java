package voliy.samples.dog.dao;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import voliy.samples.dog.model.Dog;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class DogDaoTest {
    private DogDao dogDao;

    @BeforeMethod
    private void init() {
        dogDao = new DogDao();
        dogDao.init();
    }

    @Test
    public void shouldContainDogs() {
        String[] expectedNames = Dog.names;
        Collection<String> actualNames = dogNames();
        assertEquals(expectedNames.length, actualNames.size());
        assertThat(actualNames, hasItems(expectedNames));
    }

    @Test
    public void shouldGetDogById() {
        String expectedName = Dog.names[0];
        Dog actualDog = dogDao.get(1);
        assertNotNull(actualDog);
        assertEquals(expectedName, actualDog.getName());
    }

    @Test
    public void shouldAddDogToStorage() {
        String expectedName = "Test Dog";
        dogDao.add(new Dog(expectedName, null, 1, 1));
        Collection<String> actualNames = dogNames();
        assertThat(actualNames, hasItems(expectedName));
    }

    @Test
    public void shouldDeleteDogFromStorage() {
        dogDao.delete(1);
        assertNull(dogDao.get(1));
    }

    private Collection<String> dogNames() {
        return dogDao.dogs().stream().map(Dog::getName).collect(Collectors.toList());
    }
}
