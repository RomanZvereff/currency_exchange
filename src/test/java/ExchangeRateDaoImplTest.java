import com.currency_exchange.appConfig.DBManager;
import com.currency_exchange.dao.daoImpl.ExchangeRateDaoImpl;
import com.currency_exchange.entity.ExchangeRate;
import com.currency_exchange.entity.enums.Currency;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Optional;
import java.util.Properties;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class ExchangeRateDaoImplTest {

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
        try(OutputStream output = new FileOutputStream(PROPERTY_FILE_URL)) {
            Properties prop = new Properties();
            prop.setProperty("db.connection", URL_CONNECTION_TEST);
            prop.setProperty("user", USER_TEST);
            prop.setProperty("password", PASSWORD_TEST);
            prop.setProperty("driver", JDBC_DRIVER_TEST);

            prop.store(output, null);
        }catch(IOException io) {
            io.printStackTrace();
        }

        DBManager dbManager = DBManager.getInstance();

        try(Connection con = dbManager.getConnection();
            Statement statement = con.createStatement()) {

            String sqlDropTable = "drop table if exists EXCHANGE_RATES;";
            statement.executeUpdate(sqlDropTable);

            String sqlCreateTable = "create table EXCHANGE_RATES (\n" +
                    "EXCH_RATE_ID int auto_increment primary key,\n" +
                    "RATE_DT date not null,\n" +
                    "CCY char(3) not null,\n" +
                    "BASE_CCY char(3) not null,\n" +
                    "BUY numeric(6,3) unsigned not null,\n" +
                    "SALE numeric(6,3) unsigned not null\n" +
                    ");";
            statement.executeUpdate(sqlCreateTable);

        }catch(SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldReturnSavedExchangeRateId() {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setExchangeRateDate(Calendar.getInstance());
        exchangeRate.setExchangeRateCcy(Currency.EUR.toString());
        exchangeRate.setExchangeRateBaseCcy(Currency.UAH.toString());
        exchangeRate.setExchangeRateBuy(30.6f);
        exchangeRate.setExchangeRateSale(31.056f);
        ExchangeRateDaoImpl exchangeRateDao = new ExchangeRateDaoImpl();
        long exchangeRateId = exchangeRateDao.save(exchangeRate);
        assertNotNull(exchangeRateId);
    }

    @Test
    public void shouldDeleteExchangeRate() {
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setExchangeRateDate(Calendar.getInstance());
        exchangeRate.setExchangeRateCcy(Currency.EUR.toString());
        exchangeRate.setExchangeRateBaseCcy(Currency.UAH.toString());
        exchangeRate.setExchangeRateBuy(30.6f);
        exchangeRate.setExchangeRateSale(31.056f);
        ExchangeRateDaoImpl exchangeRateDao = new ExchangeRateDaoImpl();
        exchangeRateDao.delete(exchangeRate);

        Optional<ExchangeRate> optionalExchangeRate = exchangeRateDao.get(1);
        assertFalse(optionalExchangeRate.isPresent());
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
        }catch(IOException io) {
            io.printStackTrace();
        }
    }


}
