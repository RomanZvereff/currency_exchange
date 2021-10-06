package com.currency_exchange.controller;

import com.currency_exchange.dao.daoImpl.UserDaoImpl;
import com.currency_exchange.entity.User;
import com.currency_exchange.entity.enums.UserRole;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SignUpServlet", value = "/signUp")
public class SignUpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/view/signUp.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = getServletContext().getContextPath();
        HttpSession session = request.getSession();
        String userPassword = request.getParameter("userPass");
        String confirmPassword = request.getParameter("confirmPass");

        if(!userPassword.equals(confirmPassword)) {
            request.setAttribute("message", "Password mismatch");
            getServletContext().getRequestDispatcher("/signUp").forward(request, response);
        }else {
            User user = new User();
            user.setUserLogin(request.getParameter("userLogin"));
            user.setUserPassword(userPassword);
            user.setUserRole(UserRole.CUSTOMER);
            user.setUserFirstName(request.getParameter("userFirstName"));
            user.setUserLastName(request.getParameter("userLastName"));

            UserDaoImpl userDao = new UserDaoImpl();
            long userId = userDao.save(user);
            user.setUserId(userId);

            session.setAttribute("user", user);
            response.sendRedirect(path + "/exchangePage");
        }
    }

}
