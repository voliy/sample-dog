package voliy.samples.dog.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.util.CollectionUtils;
import voliy.samples.dog.model.Dog;

import java.util.Collection;
import java.util.List;

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
        List dogs = getSession().createQuery("from Dog where id = :id").setParameter("id", id).list();
        return !CollectionUtils.isEmpty(dogs) ? (Dog) dogs.get(0) : null;
    }

    @Override public void add(Dog dog) {
        getSession().persist(dog);
    }

    @Override public void update(Dog dog) {
        getSession().createQuery("update Dog set name = :name, birthDate = :birthDate, height = :height, " +
                "weight = :weight where id = :id")
                .setParameter("id", dog.getId())
                .setParameter("birthDate", dog.getBirthDate())
                .setParameter("name", dog.getName())
                .setParameter("height", dog.getHeight())
                .setParameter("weight", dog.getWeight())
                .executeUpdate();
    }

    @Override public void delete(int id) {
        getSession().createQuery("delete Dog where id = :id").setParameter("id", id).executeUpdate();
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
