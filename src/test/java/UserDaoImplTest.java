import com.currency_exchange.appConfig.DBManager;
import com.currency_exchange.dao.daoImpl.UserDaoImpl;
import com.currency_exchange.entity.User;
import com.currency_exchange.entity.enums.UserRole;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static org.junit.Assert.assertNotNull;

public class UserDaoImplTest {

    private static final String PROPERTY_FILE_URL = "src/main/resources/app.properties";

    private static final String JDBC_DRIVER_TEST = "org.h2.Driver";
    private static final String URL_CONNECTION_TEST = "jdbc:h2:~/CURRENCY_EXCHANGE";
    private static final String USER_TEST = "sa";
    private static final String PASSWORD_TEST = "";

    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL_CONNECTION = "jdbc\\:mysql\\://localhost\\:3306/currency_exchange?autoReconnect=true&useUnicode=yes&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    @BeforeClass
    public static void beforeTest() {
        try (OutputStream output = new FileOutputStream(PROPERTY_FILE_URL)) {
            Properties prop = new Properties();
            prop.setProperty("db.connection", URL_CONNECTION_TEST);
            prop.setProperty("user", USER_TEST);
            prop.setProperty("password", PASSWORD_TEST);
            prop.setProperty("driver", JDBC_DRIVER_TEST);

            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }

        DBManager dbManager = DBManager.getInstance();

        try(Connection con = dbManager.getConnection();
            Statement statement = con.createStatement()) {

            String sqlDropTable = "drop table if exists USERS;";
            statement.executeUpdate(sqlDropTable);

            String sqlCreateTable = "create table USERS (\n" +
                    "    USER_ID int auto_increment primary key,\n" +
                    "    USER_LOGIN varchar(16) not null unique,\n" +
                    "    USER_PASS varchar(255) not null,\n" +
                    "    USER_ROLE varchar(16) not null,\n" +
                    "    USER_F_NAME varchar(50) not null,\n" +
                    "    USER_L_NAME varchar(50) not null\n" +
                    ");";
            statement.executeUpdate(sqlCreateTable);

        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnSavedUserId() {
        User user = new User();
        user.setUserLogin("login1");
        user.setUserPassword("password1");
        user.setUserRole(UserRole.CUSTOMER);
        user.setUserFirstName("FirstName1");
        user.setUserLastName("LastName1");
        UserDaoImpl userDao = new UserDaoImpl();
        long userId = userDao.save(user);
        assertNotNull(userId);
    }

    @AfterClass
    public static void afterTest() {
        try(OutputStream output = new FileOutputStream(PROPERTY_FILE_URL)) {
            Properties prop = new Properties();
            prop.setProperty("db.connection", URL_CONNECTION);
            prop.setProperty("user", USER);
            prop.setProperty("password", PASSWORD);
            prop.setProperty("driver", JDBC_DRIVER);

            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

}
