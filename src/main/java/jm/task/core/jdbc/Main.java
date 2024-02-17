package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();

        Util.getConnection();

        userDaoJDBC.createUsersTable();

        userDaoJDBC.saveUser("Нина", "Терешина", (byte) 62);
        userDaoJDBC.saveUser("Вера", "Гапова", (byte) 63);
        userDaoJDBC.saveUser("Светлана", "Бочарова", (byte) 42);
        userDaoJDBC.saveUser("Александра", "Глушак", (byte) 25);

        System.out.println(userDaoJDBC.getAllUsers());

        userDaoJDBC.cleanUsersTable();

        userDaoJDBC.dropUsersTable();
    }
}

