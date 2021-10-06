package com.currency_exchange.dao.daoImpl;

import com.currency_exchange.appConfig.DBManager;
import com.currency_exchange.dao.Dao;
import com.currency_exchange.entity.ExchangeRate;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ExchangeRateDaoImpl implements Dao<ExchangeRate> {

    private static final Lock CONNECTION_LOCK = new ReentrantLock();
    private static final DBManager dbManager = DBManager.getInstance();

    @Override
    public Optional<ExchangeRate> get(long id) {
        Calendar rateDate = Calendar.getInstance();
        Optional<ExchangeRate> optionalExchangeRate = Optional.empty();
        ResultSet resultSet;
        String getExchangeRateQuery = "select  * from EXCHANGE_RATES where EXCH_RATE_ID = ?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getExchangeRateQuery)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            ExchangeRate exchangeRate = new ExchangeRate();
            while(resultSet.next()) {
                exchangeRate.setExchangeRateId(resultSet.getInt("EXCH_RATE_ID"));
                rateDate.setTime(resultSet.getDate("RATE_DT"));
                exchangeRate.setExchangeRateDate(rateDate);
                exchangeRate.setExchangeRateCcy(resultSet.getString("CCY"));
                exchangeRate.setExchangeRateBaseCcy(resultSet.getString("BASE_CCY"));
                exchangeRate.setExchangeRateBuy(resultSet.getFloat("BUY"));
                exchangeRate.setExchangeRateSale(resultSet.getFloat("SALE"));
                optionalExchangeRate = Optional.of(exchangeRate);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return optionalExchangeRate;
    }

    @Override
    public List<ExchangeRate> getAll() {
        List<ExchangeRate> exchangeRateList = new ArrayList<>();
        ResultSet resultSet;
        String getExchangeRateQuery = "select  * from EXCHANGE_RATES;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getExchangeRateQuery)) {
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Calendar rateDate = Calendar.getInstance();
                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setExchangeRateId(resultSet.getInt("EXCH_RATE_ID"));
                rateDate.setTime(resultSet.getDate("RATE_DT"));
                exchangeRate.setExchangeRateDate(rateDate);
                exchangeRate.setExchangeRateCcy(resultSet.getString("CCY"));
                exchangeRate.setExchangeRateBaseCcy(resultSet.getString("BASE_CCY"));
                exchangeRate.setExchangeRateBuy(resultSet.getFloat("BUY"));
                exchangeRate.setExchangeRateSale(resultSet.getFloat("SALE"));
                exchangeRateList.add(exchangeRate);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return exchangeRateList;
    }

    @Override
    public long save(ExchangeRate exchangeRate) {
        CONNECTION_LOCK.lock();
        long exchangeRateId = 0;
        ResultSet resultSet;
        String saveExchangeRateQuery = "insert into EXCHANGE_RATES values (?,?,?,?,?,?)";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(saveExchangeRateQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setDate(2, new java.sql.Date(exchangeRate.getExchangeRateDate().getTimeInMillis()));
            preparedStatement.setString(3, exchangeRate.getExchangeRateCcy());
            preparedStatement.setString(4, exchangeRate.getExchangeRateBaseCcy());
            preparedStatement.setFloat(5, exchangeRate.getExchangeRateBuy());
            preparedStatement.setFloat(6, exchangeRate.getExchangeRateSale());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                exchangeRateId = resultSet.getLong(1);
            }
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            e.printStackTrace();
        }
        return exchangeRateId;
    }

    @Override
    public void update(ExchangeRate exchangeRate, String[] params) {
        CONNECTION_LOCK.lock();
        String updateExchangeRateQuery = "update EXCHANGE_RATES set CCY=?, BASE_CCY=?, BUY=?, SALE=? where EXCH_RATE_ID=?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateExchangeRateQuery)) {
            preparedStatement.setString(1, params[0]);
            preparedStatement.setString(2, params[1]);
            preparedStatement.setFloat(3, Float.parseFloat(params[2]));
            preparedStatement.setFloat(4, Float.parseFloat(params[3]));
            preparedStatement.setInt(5, exchangeRate.getExchangeRateId());
            preparedStatement.executeUpdate();
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(ExchangeRate exchangeRate) {
        CONNECTION_LOCK.lock();
        String deleteExchangeRateQuery = "delete from EXCHANGE_RATES where EXCH_RATE_ID=?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(deleteExchangeRateQuery)) {
            preparedStatement.setInt(1, exchangeRate.getExchangeRateId());
            preparedStatement.executeUpdate();
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            e.printStackTrace();
        }
    }
}
