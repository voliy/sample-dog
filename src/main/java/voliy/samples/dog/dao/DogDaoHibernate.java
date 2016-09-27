package voliy.samples.dog.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import voliy.samples.dog.model.Dog;

import java.util.Collection;

public class DogDaoHibernate implements DogDao {
    private final SessionFactory sessionFactory;

    public DogDaoHibernate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override @SuppressWarnings("unchecked")
    public Collection<Dog> dogs() {
        return getSession().createQuery("from Dog").list();
    }

    @Override public Dog get(int id) {
        return getSession().get(Dog.class, id);
    }

    @Override public Dog add(Dog dog) {
        getSession().persist(dog);
        return dog;
    }

    @Override public void update(Dog dog) {
        getSession().update(dog);
    }

    @Override public void delete(int id) {
        getSession().delete(get(id));
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
