package com.currency_exchange.controller;

import com.currency_exchange.dao.daoImpl.ExchangeRateDaoImpl;
import com.currency_exchange.dao.daoImpl.OrderDaoImpl;
import com.currency_exchange.entity.ExchangeRate;
import com.currency_exchange.entity.Order;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "AdminPageServlet", value = "/adminPage")
public class AdminPageServlet extends HttpServlet {

    private static final String path = "WEB-INF/view/adminPage.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        if("deleteRate".equals(action)) {
            long rateId = Long.parseLong(request.getParameter("rateId"));
            deleteRate(request, response, rateId);
        }else if("deleteOrder".equals(action)) {
            long orderId = Long.parseLong(request.getParameter("orderId"));
            deleteOrder(request, response, orderId);
        }else {
            getOrdersAndExchangeRates(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void getOrdersAndExchangeRates(HttpServletRequest request, HttpServletResponse response) {
        ExchangeRateDaoImpl exchangeRateDao = new ExchangeRateDaoImpl();
        List<ExchangeRate> exchangeRateList = exchangeRateDao.getAll();
        request.setAttribute("exchangeRateList", exchangeRateList);

        OrderDaoImpl orderDao = new OrderDaoImpl();
        List<Order> orderList = orderDao.getAll();
        request.setAttribute("orderList", orderList);

        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        try {
            dispatcher.forward(request, response);
        }catch(ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteRate(HttpServletRequest request, HttpServletResponse response, long id) {
        ExchangeRateDaoImpl exchangeRateDao = new ExchangeRateDaoImpl();
        Optional<ExchangeRate> optionalExchangeRate = exchangeRateDao.get(id);
        if(optionalExchangeRate.isPresent()) {
            ExchangeRate exchangeRate = optionalExchangeRate.get();
            exchangeRateDao.delete(exchangeRate);
        }
        getOrdersAndExchangeRates(request, response);
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response, long id) {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        Optional<Order> optionalOrder = orderDao.get(id);
        if(optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            orderDao.delete(order);
        }
        getOrdersAndExchangeRates(request, response);
    }

}
