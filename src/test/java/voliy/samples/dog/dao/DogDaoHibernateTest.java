package voliy.samples.dog.dao;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:dao-context.xml"})
// todo: why ContextConfiguration and @Transactional didn't work without extends?
public class DogDaoHibernateTest extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired
    private DogDao dogDao;

    @Test
    public void testName() {
        // todo: in progress
        dogDao.dogs();
    }
}