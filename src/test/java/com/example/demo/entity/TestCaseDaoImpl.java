package com.example.demo.entity;

import org.hibernate.Session;
import java.util.List;
import static com.example.demo.HibernateUtil.getSessionFactory;

public class TestCaseDaoImpl implements TestCaseDao {
    @Override
    public void save(TestCase testCase) {
        try (Session session = getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.save(testCase);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void delete(TestCase testCase) {
        try (Session session = getSessionFactory().openSession()) {
            session.getTransaction().begin();
            session.delete(testCase);
            session.getTransaction().commit();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<TestCase> getAll() {
        try (Session session = getSessionFactory().openSession()) {
            session.getTransaction().begin();
            List<TestCase> testCaseList = session.createQuery("from TestCase", TestCase.class).list();
            session.getTransaction().commit();
            return testCaseList;
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        return null;
    }
}