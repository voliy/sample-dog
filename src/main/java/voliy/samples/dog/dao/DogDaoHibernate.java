package voliy.samples.dog.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Required;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import voliy.samples.dog.model.Dog;

import java.util.Collection;

public class DogDaoHibernate implements DogDao {
    private SessionFactory sessionFactory;

    @Required // todo: why didn't happen BeanInitializationException?
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override public Collection<Dog> dogs() {
        return getSession().createQuery("from Dog").list();
    }

    @Override public Dog get(int id) {
        return (Dog) getSession().createQuery("from Dog where id = :id").setParameter("id", id).list().get(0);
    }

    @Override public void add(Dog dog) {
        getSession().persist(dog);
    }

    @Override public void update(Dog dog) {
        getSession().createQuery("update Dog set name = :name where id.id = :id").setParameter("id", dog.getId())
                .setParameter("name", dog.getName()).executeUpdate();
    }

    @Override public void delete(int id) {
        throw new NotImplementedException(); // TODO
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
