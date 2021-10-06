package com.currency_exchange.controller;

import com.currency_exchange.dao.daoImpl.ExchangeRateDaoImpl;
import com.currency_exchange.dao.daoImpl.OrderDaoImpl;
import com.currency_exchange.entity.ExchangeRate;
import com.currency_exchange.entity.Order;
import com.currency_exchange.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@WebServlet(name = "ExchangePageServlet", value = "/exchangePage")
public class ExchangePageServlet extends HttpServlet {

    private static final String path = "WEB-INF/view/exchangePage.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);

        List<ExchangeRate> exchangeRateList = maxExchangeRate();
        request.setAttribute("exchangeRateList", exchangeRateList);

        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = getServletContext().getContextPath();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String rateId = request.getParameter("rateId");
        String operation = request.getParameter("operation");
        float amount = Float.parseFloat(request.getParameter("amount"));

        createOrder(rateId, operation, amount, user);
        response.sendRedirect(path + "/userPage");

    }

    private List<ExchangeRate> maxExchangeRate() {
        List<ExchangeRate> maxExchangeRateList = null;
        ExchangeRate maxExchangeRate;
        ExchangeRateDaoImpl exchangeRateDao = new ExchangeRateDaoImpl();
        List<ExchangeRate> exchangeRateList = exchangeRateDao.getAll();
        if(exchangeRateList.stream().max(ExchangeRate::compareTo).isPresent()) {
            maxExchangeRate = exchangeRateList.stream().max(ExchangeRate::compareTo).get();
            long time = maxExchangeRate.getExchangeRateDate().getTimeInMillis();
            maxExchangeRateList = exchangeRateList
                    .stream()
                    .filter(e -> e.getExchangeRateDate().getTimeInMillis() == time).collect(Collectors.toList());
        }
        return maxExchangeRateList;
    }

    private void createOrder(String rateId, String operation, float amount, User user) {
        float baseAmount;
        ExchangeRate exchangeRate;
        ExchangeRateDaoImpl exchangeRateDao = new ExchangeRateDaoImpl();
        Optional<ExchangeRate> optionalExchangeRate = exchangeRateDao.get(Integer.parseInt(rateId));
        if(optionalExchangeRate.isPresent()) {
            exchangeRate = optionalExchangeRate.get();
            Order order = new Order();
            if("buy".equals(operation)) {
                baseAmount = exchangeRate.getExchangeRateSale() * amount;
                order.setExchangeRate(exchangeRate.getExchangeRateSale());
            }else {
                baseAmount = exchangeRate.getExchangeRateBuy() * amount;
                order.setExchangeRate(exchangeRate.getExchangeRateBuy());
            }
            order.setOrderNumber(Order.generateOrderNumber());
            order.setUser(user);
            order.setOrderDate(Calendar.getInstance());
            order.setRateDate(exchangeRate.getExchangeRateDate());
            order.setCurrency(exchangeRate.getExchangeRateCcy());
            order.setBaseCurrency(exchangeRate.getExchangeRateBaseCcy());
            order.setOrderOperation(operation);
            order.setCurrencyAmount(amount);
            order.setBaseCurrencyAmount(baseAmount);
            order.setOrderStatus("ACT");
            OrderDaoImpl orderDao = new OrderDaoImpl();
            orderDao.save(order);
        }
    }

}
