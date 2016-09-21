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

// todo: see http://docs.jboss.org/hibernate/orm/5.2/userguide/html_single/Hibernate_User_Guide.html#flushing

@ContextConfiguration(locations = {"classpath:dao-context.xml"})
public class FlushModeExamples extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired private SessionFactory sessionFactory;

    @Test @Transactional
    public void testFlushMode() throws Exception {
        session().setFlushMode(FlushMode.MANUAL);

        System.out.println("Step 1: persist dog");
        Dog expected = Dog.random();
        session().persist(expected);

        System.out.println("Step 2: clear session and load dog");
        session().clear();
        Dog actual = session().load(Dog.class, expected.getId());
        assertReflectionEquals(expected, actual);

        System.out.println("Step 3: clear session and update dog");
        session().clear();
        Dog newDog = Dog.random();
        newDog.setId(expected.getId());
        session().update(newDog);

        System.out.println("Step 4: flush");
        session().flush();

        System.out.println("Step 5: clear session and load dog");
        session().clear();
        actual = session().load(Dog.class, expected.getId());
        assertReflectionEquals(newDog, actual);
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }
}
