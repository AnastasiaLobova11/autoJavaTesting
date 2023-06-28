package com.project.testapplication.dao;

import com.project.testapplication.JdbcConnection;
import com.project.testapplication.entity.TestAttempt;
import com.project.testapplication.entity.TestCase;
import com.project.testapplication.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class TestCaseDaoImpl implements TestCaseDao {

    private final Optional<Connection> connection;

    public TestCaseDaoImpl() {
        this.connection = JdbcConnection.getConnection();
    }


//    @Override
//    public void save(TestCase testCase) {
//        try (Session session = getSessionFactory().openSession()) {
//            session.getTransaction().begin();
//            session.save(testCase);
//            session.getTransaction().commit();
//        } catch (Throwable ex) {
//            ex.printStackTrace();
//        }
//    }

    @Override
    public void save(TestCase testCase) {
        String message = "The customer to be added should not be null";
        TestCase nonNullCustomer = Objects.requireNonNull(testCase, message);
        String sql = "INSERT INTO "
                + "test_case(title, class_name) "
                + "VALUES(?, ?)";

        connection.flatMap(conn -> {
            Optional<Integer> generatedId = Optional.empty();

            try (PreparedStatement statement =
                         conn.prepareStatement(
                                 sql,
                                 Statement.RETURN_GENERATED_KEYS)) {

                statement.setString(1, nonNullCustomer.getTitle());
                statement.setString(2, nonNullCustomer.getClassName());

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
//    public void delete(TestCase testCase) {
//        try (Session session = getSessionFactory().openSession()) {
//            session.getTransaction().begin();
//            session.delete(testCase);
//            session.getTransaction().commit();
//        } catch (Throwable ex) {
//            ex.printStackTrace();
//        }
//    }

    @Override
    public void delete(TestCase testCase) {
        String message = "The customer to be deleted should not be null";
        TestCase nonNullCustomer = Objects.requireNonNull(testCase, message);
        String sql = "DELETE FROM test_case WHERE id = ?";

        connection.ifPresent(conn -> {
            try (PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setLong(1, nonNullCustomer.getId());

                int numberOfDeletedRows = statement.executeUpdate();


            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    //    @Override
//    public List getAll() {
//        try (Session session = getSessionFactory().openSession()) {
//            session.getTransaction().begin();
//            return session.createQuery("from TestCase").list();
//        } catch (Throwable ex) {
//            ex.printStackTrace();
//        }
//
//        return null;
//    }
    @Override
    public List<TestCase> getAll() {
        List<TestCase> customers = new ArrayList<>();
        String sql = "SELECT * FROM test_case";

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    long id = resultSet.getInt("id");
                    String title = resultSet.getString("title");
                    String class_name = resultSet.getString("class_name");

                    TestCase customer = new TestCase(id, title, class_name);

                    customers.add(customer);

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        return customers;
    }


    public Optional<TestCase> get(long id) {
        return connection.flatMap(conn -> {
            Optional<TestCase> testCase = Optional.empty();
            String sql = "SELECT * FROM test_case where id = " + id;

            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                if (resultSet.next()) {
                    String title = resultSet.getString("title");
                    String class_name = resultSet.getString("class_name");

                    testCase = Optional.of(  new TestCase(id, title, class_name));

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return testCase;
        });
    }

    public List<TestAttempt> getTestAttempt(long user_id){
        String sql = "SELECT * FROM test_attempt WHERE user_id = "+user_id;
        List<TestAttempt> testAttempts = new ArrayList<>();

        connection.ifPresent(conn -> {
            try (Statement statement = conn.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    long id = resultSet.getInt("id");
                    int passed = resultSet.getInt("passed");
                    int fail = resultSet.getInt("fail");
                    int number_attempt = resultSet.getInt("number_attempt");

                    TestCase test_case = get(resultSet.getLong("test_case")).get();
                    TestAttempt testAttempt = new TestAttempt(number_attempt,test_case, passed, fail);

                    testAttempts.add(testAttempt);

                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        return testAttempts;
    }
}