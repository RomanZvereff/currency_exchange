package com.currency_exchange;

import com.currency_exchange.dao.daoImpl.OrderDaoImpl;
import com.currency_exchange.entity.Order;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Demo {

    public static void main(String[] args) {

//        String password = "admin12345";
//
//        User user = new User();
//        user.setUserLogin("admin");
//        user.setUserPassword(password);
//        user.setUserRole(UserRole.ADMIN);
//        user.setUserFirstName("Admin");
//        user.setUserLastName("Admin");
//        UserDaoImpl userDao = new UserDaoImpl();
//        long userId = userDao.save(user);
//
//        System.out.println(userId);

        OrderDaoImpl orderDao = new OrderDaoImpl();
        List<Order> orderList = orderDao.getAll();

        Map<Calendar, Double> orderByDate = orderList.stream().collect(
                Collectors.groupingBy(Order::getOrderDate, Collectors.summingDouble(Order::getBaseCurrencyAmount)));

        for(Map.Entry<Calendar, Double> item : orderByDate.entrySet()){
            System.out.println(item.getKey().getTime() + " - " + item.getValue());
        }

        Map<String, Double> groupByCurrency = orderList.stream().collect(
                Collectors.groupingBy(Order::getCurrency,
                        Collectors.summingDouble(Order::getCurrencyAmount)));

        for(Map.Entry<String, Double> item : groupByCurrency.entrySet()){
            System.out.println(item.getKey() + " - " + item.getValue());
        }

    }

}
