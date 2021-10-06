package com.currency_exchange.controller;

import com.currency_exchange.dao.daoImpl.OrderDaoImpl;
import com.currency_exchange.entity.Order;
import com.currency_exchange.entity.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet(name = "UserPageServlet", value = "/userPage")
public class UserPageServlet extends HttpServlet {

    private static final String path = "WEB-INF/view/userPage.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        OrderDaoImpl orderDao = new OrderDaoImpl();
        List<Order> orderList = orderDao.getAll();

        Map<Calendar, Double> groupByDate = orderList.stream().collect(
                Collectors.groupingBy(Order::getOrderDate,
                        Collectors.summingDouble(Order::getBaseCurrencyAmount)));
        request.setAttribute("groupByDate", groupByDate);

        Map<String, Double> groupByCurrency = orderList.stream().collect(
                Collectors.groupingBy(Order::getCurrency,
                        Collectors.summingDouble(Order::getCurrencyAmount)));
        request.setAttribute("groupByCurrency", groupByCurrency);

        orderList = orderList.stream()
                .filter(o -> o.getUser().getUserId() == user.getUserId()).collect(Collectors.toList());
        request.setAttribute("orderList", orderList);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
