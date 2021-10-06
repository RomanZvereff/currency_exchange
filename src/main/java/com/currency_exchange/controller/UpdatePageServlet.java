package com.currency_exchange.controller;

import com.currency_exchange.dao.daoImpl.ExchangeRateDaoImpl;
import com.currency_exchange.dao.daoImpl.OrderDaoImpl;
import com.currency_exchange.entity.ExchangeRate;
import com.currency_exchange.entity.Order;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "UpdatePageServlet", value = "/updatePage")
public class UpdatePageServlet extends HttpServlet {

    private static final String path = "WEB-INF/view/updatePage.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        String rateIdParam = request.getParameter("rateId");
        String orderIdParam = request.getParameter("orderId");

        if(rateIdParam != null) {
            ExchangeRateDaoImpl exchangeRateDao = new ExchangeRateDaoImpl();
            Optional<ExchangeRate> optionalExchangeRate = exchangeRateDao.get(Integer.parseInt(rateIdParam));
            if(optionalExchangeRate.isPresent()) {
                ExchangeRate exchangeRate = optionalExchangeRate.get();
                request.setAttribute("exchangeRate", exchangeRate);
            }
        }else if(orderIdParam != null) {
            OrderDaoImpl orderDao = new OrderDaoImpl();
            Optional<Order> optionalOrder = orderDao.get(Long.getLong(orderIdParam));
            if(optionalOrder.isPresent()) {
                Order order = optionalOrder.get();
                request.setAttribute("order", order);
            }
        }
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = getServletContext().getContextPath();
        String rateId = request.getParameter("rateId");
        String orderId = request.getParameter("orderId");

        if(rateId != null) {
            updateRate(request, response, rateId);
        }else if(orderId != null) {
            updateOrder(request, response, orderId);
        }
        response.sendRedirect(path + "/adminPage");
    }

    private void updateRate(HttpServletRequest request, HttpServletResponse response, String id) {
        ExchangeRateDaoImpl exchangeRateDao = new ExchangeRateDaoImpl();
        Optional<ExchangeRate> optionalExchangeRate = exchangeRateDao.get(Integer.parseInt(id));
        if(optionalExchangeRate.isPresent()) {
            ExchangeRate exchangeRate = optionalExchangeRate.get();
            String currency = request.getParameter("currency");
            String baseCurrency = request.getParameter("baseCurrency");
            String buy = request.getParameter("buy");
            String sale = request.getParameter("sale");
            String[] params = {currency, baseCurrency, buy, sale};
            exchangeRateDao.update(exchangeRate, params);
        }
    }

    private void updateOrder(HttpServletRequest request, HttpServletResponse response, String id) {
        OrderDaoImpl orderDao = new OrderDaoImpl();
        Optional<Order> optionalOrder = orderDao.get(Long.parseLong(id));
        if(optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            String orderOperation = request.getParameter("orderOperation");
            String exchangeRate = request.getParameter("exchangeRate");
            String currencyAmount = request.getParameter("currencyAmount");
            String baseCurrencyAmount = request.getParameter("baseCurrencyAmount");
            String orderStatus = request.getParameter("orderStatus");
            String[] params = {orderOperation, exchangeRate, currencyAmount, baseCurrencyAmount, orderStatus};
            orderDao.update(order, params);
        }
    }

}
