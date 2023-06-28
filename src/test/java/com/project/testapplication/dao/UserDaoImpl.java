package com.project.testapplication.dao;

import com.project.testapplication.JdbcConnection;
import com.project.testapplication.entity.TestAttempt;
import com.project.testapplication.entity.TestCase;
import com.project.testapplication.entity.User;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class UserDaoImpl implements UserDao<User> {

    private final Optional<Connection> connection;
    private final TestCaseDaoImpl testCaseDaoImpl = new TestCaseDaoImpl();

    public UserDaoImpl() {
        this.connection = JdbcConnection.getConnection();
    }


    //    @Override
//    public List getByParameters(Integer course, Integer groupe, String surname) {
//        try (Session session = getSessionFactory().openSession()) {
//            session.getTransaction().begin();
//            return session.createQuery("from User where course = :cr and groupa = :gr and surname = :sn")
//                    .setParameter("cr", course)
//                    .setParameter("gr", groupe)
//                    .setParameter("sn", surname).list();
//        } catch (Throwable ex) {
//            ex.printStackTrace();
//        }
//        return null;
//    }
    @Override
    public Optional<User> getByParameters(Integer course, Integer groupe, String surname) {
        return connection.flatMap(conn -> {
            Optional<User> user = Optional.empty();
            String sql = "SELECT * FROM my_user WHERE course = " + course +
                    " and groupa = " + groupe + " and surname = '" + surname + "'";

            String sql2 = "SELECT * FROM test_attempt WHERE user_id = ";


            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    long id = resultSet.getInt("id");
                    User customer = new User(id, course, groupe, surname);
                    List<TestAttempt> testAttempts = testCaseDaoImpl.getTestAttempt(id);
                    customer.setTestAttempt(testAttempts);
                    user = Optional.of(customer);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            return user;
        });
    }

    public  long getUserId(User user){
        return getByParameters(user.getCourse(), user.getGroupa(), user.getSurname()).get().getId();
    }


//    @Override
//    public void save(User user) {
//        try (Session session = getSessionFactory().openSession()) {
//            session.getTransaction().begin();
//            session.save(user);
//            session.getTransaction().commit();
//        } catch (Throwable ex) {
//            ex.printStackTrace();
//        }
//    }

    @Override
    public void save(User user) {
        String message = "The customer to be added should not be null";
        User nonNullCustomer = Objects.requireNonNull(user, message);
        String sql = "INSERT INTO "
                + "my_user(course,groupa, surname) "
                + "VALUES(?, ?, ?)";

        connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement =
                         conn.prepareStatement(
                                 sql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                statement.setInt(1, nonNullCustomer.getCourse());
                statement.setInt(2, nonNullCustomer.getGroupa());
                statement.setString(3, nonNullCustomer.getSurname());

                int numberOfInsertedRows = statement.executeUpdate();

                // Retrieve the auto-generated id
                if (numberOfInsertedRows > 0) {
                    try (ResultSet resultSet = statement.getGeneratedKeys()) {
                        if (resultSet.next()) {
                            generatedId = Optional.of(resultSet.getInt(1));
                        }
                    }
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return generatedId;
        });
    }

    //    @Override
//    public void update(User user, TestCase testCase, Integer pos, Integer neg) {
//        try (Session session = getSessionFactory().openSession()) {
//            int number = user.getTestAttempt().stream().filter(testAttempt ->
//                    testAttempt.getTestCase().getTitle().equals(testCase.getTitle())).toList().size() + 1;
//
//            session.getTransaction().begin();
//
//            TestAttempt testAttempt = new TestAttempt();
//            testAttempt.setNumberAttempt(number);
//            testAttempt.setFail(neg);
//            testAttempt.setPassed(pos);
//            testAttempt.setTestCase(testCase);
//            testAttempt.setUser(user);
//            user.getTestAttempt().add(testAttempt);
//            testCase.getTestAttempts().add(testAttempt);
//
//
//            session.save(testAttempt);
//            session.getTransaction().commit();
//        } catch (Throwable ex) {
//            ex.printStackTrace();
//        }
//    }
    @Override
    public void update(User user, TestCase testCase, Integer pos, Integer neg) {
        int number = user.getTestAttempt().stream().filter(testAttempt ->
                testAttempt.getTestCase().getTitle().equals(testCase.getTitle())).toList().size() + 1;

        TestAttempt testAttempt = new TestAttempt();
        testAttempt.setNumberAttempt(number);
        testAttempt.setFail(neg);
        testAttempt.setPassed(pos);
        testAttempt.setTestCase(testCase);
        testAttempt.setUser(user);
        user.getTestAttempt().add(testAttempt);
        testCase.getTestAttempts().add(testAttempt);

        String sql = "INSERT INTO " +
                "test_attempt(number_attempt,test_case, passed, fail, user_id) " +
                "VALUES(?, ?, ?, ?, ?)";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setInt(1, testAttempt.getNumberAttempt());
                statement.setLong(2, testAttempt.getTestCase().getId());
                statement.setInt(3, testAttempt.getPassed());
                statement.setInt(4, testAttempt.getFail());
                statement.setLong(5, testAttempt.getUser().getId());

                int numberOfUpdatedRows = statement.executeUpdate();


            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }
}
