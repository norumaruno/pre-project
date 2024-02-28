package jm.task.core.jdbc;

import jm.task.core.jdbc.service.*;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Нина", "Терешина", (byte) 62);
        userService.saveUser("Вера", "Гапова", (byte) 63);
        userService.saveUser("Светлана", "Бочарова", (byte) 42);
        userService.saveUser("Александра", "Глушак", (byte) 25);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();

        userService.dropUsersTable();

        Util.closeSessionFactory();
    }
}

