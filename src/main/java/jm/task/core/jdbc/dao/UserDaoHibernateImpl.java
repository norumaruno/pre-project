package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Transaction;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory SESSION_FACTORY = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = SESSION_FACTORY.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                    "id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(30), " +
                    "lastName VARCHAR(30), " +
                    "age TINYINT);";

            session.createSQLQuery(sql).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Не удалось создать таблицу.");
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = SESSION_FACTORY.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS Users;").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Не удалось удалить таблицу.");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = SESSION_FACTORY.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();

            System.out.printf("User с именем %s добавлен в базу данных.%n", name);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            System.out.println("Не удалось создать User'a.");
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = SESSION_FACTORY.getCurrentSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            System.out.println("Не удалось удалить User'a.");
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> users = null;

        try (Session session = SESSION_FACTORY.getCurrentSession()) {
            transaction = session.beginTransaction();
            users = session.createQuery("from User", User.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }

            System.out.println("Не удалось создать лист User'ов.");
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = SESSION_FACTORY.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE Users;").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println("Не удалось очистить таблицу.");
        }
    }
}
