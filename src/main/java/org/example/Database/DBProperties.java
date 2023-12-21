package org.example.Database;

import org.example.Mail.ErrorEmailSender;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBProperties {
    private static final Properties prop = new Properties();

    static {
        try {
            FileInputStream config = new FileInputStream("E:\\Warehouse\\config.properties");
            prop.load(config);
            config.close();
        } catch (IOException e) {
            e.printStackTrace();
            ErrorEmailSender.sendMail("config", "Fail can't read file config.properties");
        }
    }

    public static String getDbHost() {
        return prop.get("db.host").toString();
    }

    public static String getDbPort() {
        return prop.get("db.port").toString();
    }

    public static String getUsername() {
        return prop.get("db.username").toString();
    }

    public static String getPassword() {
        return prop.get("db.password").toString();
    }

    public static String getDbOption() {
        return prop.get("db.options").toString();
    }

    public static String getDbName() {
        return prop.get("db.databaseName").toString();
    }

    public static String getJdbcUrl() {
        return prop.get("db.jdbcUrl").toString();
    }
    public static String getRun() { return prop.get("run").toString();}
    public static String getDate() { return prop.get("date").toString();}

    public static void main(String[] args) {
        System.out.println(getDate());
    }

}
