package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static String getVerificationCode() {
        var verificationCode = "SELECT code FROM auth_codes";
        return runner.query(getConnection(), verificationCode, new ScalarHandler<String>());
    }

    @SneakyThrows
    public static void cleanAuthcode() {
        runner.execute(getConnection(), "DELETE FROM auth_codes");
    }

    @SneakyThrows
    public static void cleanDatabase() {
        runner.execute(getConnection(),"DELETE FROM auth_codes");
        runner.execute(getConnection(), "DELETE FROM cards");
        runner.execute(getConnection(),"DELETE FROM users");
        runner.execute(getConnection(),"DELETE FROM card_transactions");
    }

    @SneakyThrows
    public static String getStatus() {
        var status = "SELECT status FROM users WHERE login = 'vasya'";
        return runner.query(getConnection(), status, new ScalarHandler<String>());
    }

}