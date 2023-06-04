package com.project.testapplication.utils;

import com.project.testapplication.entity.TestAttempt;
import com.project.testapplication.entity.TestCase;
import com.project.testapplication.entity.User;
import org.hibernate.Session;
import java.util.List;
import static com.project.testapplication.HibernateUtil.getSessionFactory;

public class UserDaoImpl implements UserDao<User> {

    @Override
    public List<User> getByParameters(Integer course, Integer groupe, String surname) {
        try (Session session = getSessionFactory().openSession()) {
            session.getTransaction().begin();
            return session.createQuery("from User where course = :cr and groupa = :gr and surname = :sn", User.class)
                    .setParameter("cr", course)
                    .setParameter("gr", groupe)
                    .setParameter("sn", surname)
                    .list();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void save(User user) {
        try (Session session = getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.save(user);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(User user, TestCase testCase, Integer pos, Integer neg) {
        try (Session session = getSessionFactory().openSession()) {
            int number = user.getTestAttempt().stream().filter(testAttempt ->
                    testAttempt.getTestCase().getTitle().equals(testCase.getTitle())).toList().size() + 1;

            session.getTransaction().begin();

            TestAttempt testAttempt = new TestAttempt();
            testAttempt.setNumberAttempt(number);
            testAttempt.setFail(neg);
            testAttempt.setPassed(pos);
            testAttempt.setTestCase(testCase);
            testAttempt.setUser(user);
            user.getTestAttempt().add(testAttempt);
            testCase.getTestAttempts().add(testAttempt);


            session.save(testAttempt);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}
