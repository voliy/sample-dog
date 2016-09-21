package voliy.samples.dog.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;
import voliy.samples.dog.model.Dog;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(locations = {"classpath:dao-context.xml"})
public class FlushModeExamples extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired private DogDao dogDao;
    @Autowired private SessionFactory sessionFactory;

    /*
    ALWAYS
    The Session is flushed before every query.

    AUTO
    The Session is sometimes flushed before query execution in order to ensure that queries never return stale state.

    COMMIT
    The Session is flushed when Transaction.commit() is called.

    MANUAL
    The Session is only ever flushed when Session.flush() is explicitly called by the application.

    NEVER
    Deprecated. use MANUAL instead.
    */

    @Test public void testFlushMode() throws Exception {
        Dog expected = addDog(Dog.random());
        flushAndClear();

        Dog actual = loadDog(expected.getId());
        assertReflectionEquals(expected, actual);
    }

    private Dog addDog(Dog dog) {
        dogDao.add(dog);
        return dog;
    }

    private Dog loadDog(Integer id) {
        return dogDao.get(id);
    }

    private void flushAndClear() {
        sessionFactory.getCurrentSession().flush();
        sessionFactory.getCurrentSession().clear();
    }
}
