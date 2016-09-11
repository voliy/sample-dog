package voliy.samples.dog.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;
import voliy.samples.dog.model.Dog;

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
        sessionFactory.getCurrentSession().clear();
        Dog actual = dogDao.get(dogId);
        assertReflectionEquals(expected, actual);
    }
}