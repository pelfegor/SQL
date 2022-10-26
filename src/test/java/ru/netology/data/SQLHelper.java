package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();
    private static Connection conn;

    @SneakyThrows
    public static void setUp() {
        conn = DriverManager.getConnection
                ("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    @SneakyThrows
    public static void reloadVerifyCodeTable() {
        setUp();
        var sqlQuery = "DELETE FROM auth_codes;";
        runner.update(conn, sqlQuery);
    }

    @SneakyThrows
    public static void setDown() {
        setUp();
        reloadVerifyCodeTable();
        runner.execute(conn, "DELETE FROM card_transactions;");
        runner.execute(conn, "DELETE FROM cards");
        runner.execute(conn, "DELETE FROM users");
    }

    @SneakyThrows
    public static String getVerificationCode(String login) {
        setUp();
        var sqlQuery = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        return runner.query(conn, sqlQuery, new ScalarHandler<>());
    }
}