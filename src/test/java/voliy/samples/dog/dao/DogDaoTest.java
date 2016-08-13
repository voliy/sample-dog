package voliy.samples.dog.dao;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import voliy.samples.dog.model.Dog;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class DogDaoTest {
    private DogDao dogDao;

    @BeforeSuite
    private void init() {
        dogDao = new DogDao();
        dogDao.init();
    }

    @Test
    public void mustContainDogs() {
        String[] expected = Dog.names;
        Collection<String> actual = dogNames();

        assertEquals(expected.length, actual.size());
        assertThat(actual, hasItems(expected));
    }

    private Collection<String> dogNames() {
        return dogDao.dogs().stream().map(Dog::getName).collect(Collectors.toList());
    }
}
