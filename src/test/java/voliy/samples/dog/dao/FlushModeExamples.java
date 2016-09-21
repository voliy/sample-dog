package voliy.samples.dog.dao;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;
import voliy.samples.dog.model.Dog;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ContextConfiguration(locations = {"classpath:dao-context.xml"})
public class FlushModeExamples extends AbstractTransactionalTestNGSpringContextTests {
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

    @Test @Transactional
    public void testFlushMode() throws Exception {
//        session().setFlushMode(FlushModeType.COMMIT);
        session().setFlushMode(FlushMode.MANUAL);

        Dog expected = Dog.random();
        session().persist(expected);

        session().clear();

        Dog actual = session().load(Dog.class, expected.getId());
        assertReflectionEquals(expected, actual);

        session().clear();

        Dog newDog = Dog.random();
        newDog.setId(expected.getId());
        session().update(newDog);

        session().clear();

        actual = session().load(Dog.class, expected.getId());
        assertReflectionEquals(expected, actual);
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }
}
