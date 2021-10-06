package com.currency_exchange.dao.daoImpl;

import com.currency_exchange.appConfig.DBManager;
import com.currency_exchange.dao.Dao;
import com.currency_exchange.entity.Order;
import com.currency_exchange.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderDaoImpl implements Dao<Order> {

    private static final Lock CONNECTION_LOCK = new ReentrantLock();
    private static final DBManager dbManager = DBManager.getInstance();

    @Override
    public Optional<Order> get(long id) {
        Calendar orderDate = Calendar.getInstance();
        Calendar rateDate = Calendar.getInstance();
        Optional<Order> optionalOrder = Optional.empty();
        ResultSet resultSet;
        String getExchangeRateQuery = "select  * from ORDERS where ORDER_ID = ?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getExchangeRateQuery)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            Order order = new Order();
            while(resultSet.next()) {
                order.setOrderId(resultSet.getInt("ORDER_ID"));
                order.setOrderNumber(resultSet.getString("ORDER_NUM"));

                int userId = resultSet.getInt("USER_ID");
                UserDaoImpl userDao = new UserDaoImpl();
                Optional<User> optionalUser = userDao.get(userId);
                optionalUser.ifPresent(order::setUser);

                orderDate.setTime(resultSet.getDate("ORDER_DT"));
                order.setOrderDate(orderDate);
                rateDate.setTime(resultSet.getDate("RATE_DT"));
                order.setOrderDate(rateDate);

                order.setCurrency(resultSet.getString("CCY"));
                order.setBaseCurrency(resultSet.getString("BASE_CCY"));
                order.setOrderOperation(resultSet.getString("ORDER_OPER"));
                order.setExchangeRate(resultSet.getFloat("EXCH_RATE"));
                order.setCurrencyAmount(resultSet.getFloat("CCY_AMT"));
                order.setBaseCurrencyAmount(resultSet.getFloat("BASE_CCY_AMT"));
                order.setOrderStatus(resultSet.getString("ORDER_STATUS"));
                optionalOrder = Optional.of(order);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return optionalOrder;
    }

    @Override
    public List<Order> getAll() {
        List<Order> orderList = new ArrayList<>();
        ResultSet resultSet;
        String getExchangeRateQuery = "select  * from ORDERS;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getExchangeRateQuery)) {
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                Calendar orderDate = Calendar.getInstance();
                Calendar rateDate = Calendar.getInstance();
                Order order = new Order();
                order.setOrderId(resultSet.getInt("ORDER_ID"));
                order.setOrderNumber(resultSet.getString("ORDER_NUM"));

                int userId = resultSet.getInt("USER_ID");
                UserDaoImpl userDao = new UserDaoImpl();
                Optional<User> optionalUser = userDao.get(userId);
                optionalUser.ifPresent(order::setUser);

                orderDate.setTime(resultSet.getDate("ORDER_DT"));
                order.setOrderDate(orderDate);
                rateDate.setTime(resultSet.getDate("RATE_DT"));
                order.setRateDate(rateDate);

                order.setCurrency(resultSet.getString("CCY"));
                order.setBaseCurrency(resultSet.getString("BASE_CCY"));
                order.setOrderOperation(resultSet.getString("ORDER_OPER"));
                order.setExchangeRate(resultSet.getFloat("EXCH_RATE"));
                order.setCurrencyAmount(resultSet.getFloat("CCY_AMT"));
                order.setBaseCurrencyAmount(resultSet.getFloat("BASE_CCY_AMT"));
                order.setOrderStatus(resultSet.getString("ORDER_STATUS"));
                orderList.add(order);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    @Override
    public long save(Order order) {
        long orderId = -1;
        ResultSet generatedKeys;
        CONNECTION_LOCK.lock();
        String createOrderQuery = "insert into ORDERS values (?,?,?,?,?,?,?,?,?,?,?,?);";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(createOrderQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setString(2, order.getOrderNumber());
            preparedStatement.setLong(3, order.getUser().getUserId());
            preparedStatement.setDate(4, new java.sql.Date(order.getOrderDate().getTimeInMillis()));
            preparedStatement.setDate(5, new java.sql.Date(order.getRateDate().getTimeInMillis()));
            preparedStatement.setString(6, order.getCurrency());
            preparedStatement.setString(7, order.getBaseCurrency());
            preparedStatement.setString(8, order.getOrderOperation());
            preparedStatement.setFloat(9, order.getExchangeRate());
            preparedStatement.setFloat(10, order.getCurrencyAmount());
            preparedStatement.setFloat(11, order.getBaseCurrencyAmount());
            preparedStatement.setString(12, order.getOrderStatus());
            preparedStatement.executeUpdate();
            generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                orderId = generatedKeys.getLong(1);
            }
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            e.printStackTrace();
        }
        return orderId;
    }

    @Override
    public void update(Order order, String[] params) {
        CONNECTION_LOCK.lock();
        String updateOrderQuery = "update ORDERS set ORDER_OPER=?, EXCH_RATE=?, CCY_AMT=?, BASE_CCY_AMT=?, ORDER_STATUS=? where ORDER_ID=?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateOrderQuery)) {
            preparedStatement.setString(1, params[0]);
            preparedStatement.setFloat(2, Float.parseFloat(params[1]));
            preparedStatement.setFloat(3, Float.parseFloat(params[2]));
            preparedStatement.setFloat(4, Float.parseFloat(params[3]));
            preparedStatement.setString(5, params[4]);
            preparedStatement.setLong(6, order.getOrderId());
            preparedStatement.executeUpdate();
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Order order) {
        CONNECTION_LOCK.lock();
        String updateOrderQuery = "update ORDERS set ORDER_STATUS=? where ORDER_ID=?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(updateOrderQuery)) {
            preparedStatement.setString(1, "DEL");
            preparedStatement.setLong(2, order.getOrderId());
            preparedStatement.executeUpdate();
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            e.printStackTrace();
        }
    }
}
