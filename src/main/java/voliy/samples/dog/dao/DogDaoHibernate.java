package voliy.samples.dog.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import voliy.samples.dog.model.Dog;

import java.util.Collection;

public class DogDaoHibernate implements DogDao {
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Collection<Dog> dogs() {
        return getSession().createQuery("from Dog").list();
    }

    @Override
    public Dog get(int id) {
        Query query = getSession().createQuery("from Dog where id = :id");
        query.setParameter("id", id);
        return (Dog) query.list().get(0);
    }

    @Override
    // todo: transaction isn't created when init method is invoked in the service
    @Transactional
    public void add(Dog dog) {
        getSession().persist(dog);
    }

    @Override
    public void update(Dog dog) {
        throw new NotImplementedException(); // TODO
    }

    @Override
    public void delete(int id) {
        throw new NotImplementedException(); // TODO
    }

    private Session getSession() {
//        SEVERE: Servlet /sample-dog threw load() exception
//        org.hibernate.HibernateException: Could not obtain transaction-synchronized Session for current thread
//        at org.springframework.orm.hibernate5.SpringSessionContext.currentSession(SpringSessionContext.java:133)
//        at org.hibernate.internal.SessionFactoryImpl.getCurrentSession(SessionFactoryImpl.java:454)
//        at voliy.samples.dog.dao.DogDaoHibernate.getSession(DogDaoHibernate.java:50)
        // todo: what can we do with this?
        try {
            return sessionFactory.getCurrentSession();
        } catch (HibernateException e) {
            return sessionFactory.openSession();
        }
    }
}
