package com.currency_exchange.appConfig;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {

    private static DBManager dbManager;
    private static String url, user, pass, driver;

    private DBManager() {
        Properties properties = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("app.properties");
            properties.load(inputStream);
            url = properties.getProperty("db.connection");
            user = properties.getProperty("user");
            pass = properties.getProperty("password");
            driver = properties.getProperty("driver");
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Singleton
    public static synchronized DBManager getInstance() {
        if(dbManager == null) {
            dbManager = new DBManager();
        }
        return dbManager;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, pass);
        }catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
