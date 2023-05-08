package com.example.demo.entity;

import com.example.demo.HibernateUtil;
import org.hibernate.Session;
import java.util.List;
public class UserDaoImpl implements UserDao<User> {
    public List<User> getAllByParameters(Integer course, Integer groupe, String surname) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            List<User> result = session.createQuery("from User where course = :cr and groupa = :gr and surname = :sn", User.class)
                    .setParameter("cr", course)
                    .setParameter("gr", groupe)
                    .setParameter("sn", surname)
                    .list();
            return result;
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.save(user);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(User user, Integer pos, Integer neg) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            int number = user.getTestAttempt().size() + 1;
            TestAttempt testAttempt = new TestAttempt(number, pos, neg);
            user.getTestAttempt().add(testAttempt);
            session.getTransaction().begin();
            user.setTestAttempt(user.getTestAttempt());
            session.saveOrUpdate(user);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }



}
