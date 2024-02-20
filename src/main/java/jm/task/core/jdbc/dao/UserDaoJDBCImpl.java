package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS Users (" +
                "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(30), " +
                "lastName VARCHAR(30), " +
                "age TINYINT);";
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("Не удалось создать таблицу.");
        }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS Users;";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("Не удалось удалить таблицу.");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO Users (name, lastName, age) VALUES (?, ?, ?);";
        try (Connection connection = Util.getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, lastName);
            pstmt.setByte(3, age);
            pstmt.execute();
            System.out.printf("User с именем %s добавлен в базу данных.%n", name);
        } catch (SQLException e) {
            System.out.println("Не удалось создать user'а.");
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM Users WHERE id = " + id + ";";
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("Не удалось удалить User'a.");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "select * from Users;";
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");

                User user = new User(name, lastName, age);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Не удалость создать лист пользователей.");
        }

        return users;
    }

    public void cleanUsersTable() {
        String query = "TRUNCATE TABLE Users;";
        try (Connection connection = Util.getConnection(); Statement statement = connection.createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицу");
        }
    }
}
