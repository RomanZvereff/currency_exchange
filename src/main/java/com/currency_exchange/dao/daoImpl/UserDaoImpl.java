package com.currency_exchange.dao.daoImpl;

import com.currency_exchange.appConfig.DBManager;
import com.currency_exchange.dao.Dao;
import com.currency_exchange.entity.User;
import com.currency_exchange.entity.enums.UserRole;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserDaoImpl implements Dao<User> {

    private static final Lock CONNECTION_LOCK = new ReentrantLock();
    private static final DBManager dbManager = DBManager.getInstance();

    public Optional<User> validateUser(String userLogin, String userPassword) {
        Optional<User> optionalUser = Optional.empty();
        String securedPassword;
        ResultSet resultSet;
        String getUserQuery = "select  * from USERS where USER_LOGIN = ?";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getUserQuery)) {
            preparedStatement.setString(1, userLogin);
            resultSet = preparedStatement.executeQuery();
            User user = new User();
            while(resultSet.next()) {
                securedPassword = resultSet.getString("USER_PASS");
                boolean matched = User.validatePassword(userPassword, securedPassword);
                if(matched) {
                    user.setUserId(resultSet.getLong("USER_ID"));
                    user.setUserLogin(resultSet.getString("USER_LOGIN"));
                    user.setUserRole(UserRole.valueOf(resultSet.getString("USER_ROLE")));
                    optionalUser = Optional.of(user);
                }
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return optionalUser;
    }

    @Override
    public Optional<User> get(long id) {
        Optional<User> optionalUser = Optional.empty();
        ResultSet resultSet;
        String getUserQuery = "select  * from USERS where USER_ID = ?;";
        try(Connection connection = dbManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getUserQuery)) {
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            User user = new User();
            while(resultSet.next()) {
                user.setUserId(resultSet.getInt("USER_ID"));
                user.setUserLogin(resultSet.getString("USER_LOGIN"));
                user.setUserRole(UserRole.valueOf(resultSet.getString("USER_ROLE")));
                user.setUserFirstName(resultSet.getString("USER_F_NAME"));
                user.setUserLastName(resultSet.getString("USER_L_NAME"));
                optionalUser = Optional.of(user);
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return optionalUser;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public long save(User user) {
        CONNECTION_LOCK.lock();
        long userId = 0;
        ResultSet resultSet;
        String saveUserSqlQuery = "insert into USERS values (?,?,?,?,?,?)";
        try(Connection connection = DBManager.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(saveUserSqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setNull(1, Types.BIGINT);
            preparedStatement.setString(2, user.getUserLogin());
            String generatedSecuredPasswordHash = User.generateStrongPasswordHash(user.getUserPassword());
            preparedStatement.setString(3, generatedSecuredPasswordHash);
            preparedStatement.setString(4, user.getUserRole().toString());
            preparedStatement.setString(5, user.getUserFirstName());
            preparedStatement.setString(6, user.getUserLastName());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()) {
                userId = resultSet.getLong(1);
            }
            CONNECTION_LOCK.unlock();
        }catch(SQLException e) {
            CONNECTION_LOCK.unlock();
            e.printStackTrace();
        }
        return userId;
    }

    @Override
    public void update(User user, String[] params) {

    }

    @Override
    public void delete(User user) {

    }
}
