package com.currency_exchange.controller;

import com.currency_exchange.dao.daoImpl.ExchangeRateDaoImpl;
import com.currency_exchange.entity.ExchangeRate;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Calendar;

@WebServlet(name = "CreatePageServlet", value = "/createPage")
public class CreatePageServlet extends HttpServlet {

    private static final String path = "WEB-INF/view/createPage.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(path);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = getServletContext().getContextPath();

        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setExchangeRateDate(Calendar.getInstance());
        exchangeRate.setExchangeRateCcy(request.getParameter("currency"));
        exchangeRate.setExchangeRateBaseCcy(request.getParameter("baseCurrency"));
        exchangeRate.setExchangeRateBuy(Float.parseFloat(request.getParameter("buy")));
        exchangeRate.setExchangeRateSale(Float.parseFloat(request.getParameter("sale")));

        ExchangeRateDaoImpl exchangeRateDao = new ExchangeRateDaoImpl();
        exchangeRateDao.save(exchangeRate);

        response.sendRedirect(path + "/adminPage");
    }
}
