package voliy.samples.dog.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import voliy.samples.dog.model.Dog;

import java.util.Collection;

public class DogDaoHibernate implements DogDao {
    private Session session;

    @Override
    public Collection<Dog> dogs() {
        return session.createQuery("from Dog").list();
    }

    @Override
    public void init() {
        session = new Configuration().configure().buildSessionFactory().openSession();
        Dog.samples().forEach(this::add);
    }

    @Override
    public Dog get(int id) {
        Query query = session.createQuery("from Dog where id = :id");
        query.setParameter("id", id);
        return (Dog) query.list().get(0);
    }

    @Override
    public void add(Dog dog) {
        Transaction transaction = session.beginTransaction();
        session.persist(dog);
        session.flush();
        transaction.commit();
    }

    @Override
    public void update(Dog dog) {
        throw new NotImplementedException(); // TODO
    }

    @Override
    public void delete(int id) {
        throw new NotImplementedException(); // TODO
    }
}
