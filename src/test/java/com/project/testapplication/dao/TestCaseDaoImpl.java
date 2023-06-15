package com.project.testapplication.dao;

import com.project.testapplication.entity.TestCase;
import org.hibernate.Session;

import java.util.List;

import static com.project.testapplication.HibernateUtil.getSessionFactory;

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
    public List getAll() {
        try (Session session = getSessionFactory().openSession()) {
            session.getTransaction().begin();
            return session.createQuery("from TestCase").list();
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        return null;
    }
}