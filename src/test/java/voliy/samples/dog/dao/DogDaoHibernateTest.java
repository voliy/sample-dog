package voliy.samples.dog.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;
import voliy.samples.dog.model.Dog;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.AssertJUnit.assertNull;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(locations = {"classpath:dao-context.xml"})
// todo: why ContextConfiguration and @Transactional didn't work without extends?
// todo: org.springframework.test.context.testng - package?
public class DogDaoHibernateTest extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired
    private DogDao dogDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    public void saveAndLoadDog() {
        Dog expected = Dog.random();
        assertNull(expected.getId());
        dogDao.add(expected);
        Integer dogId = expected.getId();
        assertNotNull(dogId);
        Dog actual = loadDog(dogId);
        assertReflectionEquals(expected, actual);
    }

    @Test
    public void updateNameOfDog() throws Exception {
        Dog dog = Dog.random();
        dog.setName("Cooper");
        dogDao.add(dog);
        Dog actual = loadDog(dog.getId());
        assertEquals("Cooper", actual.getName());
        dog.setName("Oscar");
        sessionFactory.getCurrentSession().clear();
        dogDao.update(dog);
        actual = loadDog(dog.getId());
        assertEquals("Oscar", actual.getName());
    }

    private Dog loadDog(Integer dogId) {
        sessionFactory.getCurrentSession().clear();
        return dogDao.get(dogId);
    }
}