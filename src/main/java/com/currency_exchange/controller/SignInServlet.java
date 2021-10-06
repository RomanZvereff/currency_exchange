package com.currency_exchange.controller;

import com.currency_exchange.dao.daoImpl.UserDaoImpl;
import com.currency_exchange.entity.User;
import com.currency_exchange.entity.enums.UserRole;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "SingInServlet", value = "/signIn")
public class SignInServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("signIn.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = getServletContext().getContextPath();
        HttpSession oldSession = request.getSession(false);
        if(oldSession != null) {
            oldSession.invalidate();
        }

        HttpSession session = request.getSession(true);
        String userLogin = request.getParameter("userLogin");
        String userPassword = request.getParameter("userPassword");

        User user;
        UserDaoImpl userDao = new UserDaoImpl();
        Optional<User> optionalUser = userDao.validateUser(userLogin, userPassword);

        if(optionalUser.isPresent()) {
            user = optionalUser.get();
            session.setAttribute("user", user);
            if(user.getUserRole().equals(UserRole.ADMIN)) {
                response.sendRedirect(path + "/adminPage");
            }else if(user.getUserRole().equals(UserRole.CUSTOMER)) {
                response.sendRedirect(path + "/exchangePage");
            }
        }else {
            session.setAttribute("message", "Invalid username or password");
            response.sendRedirect(path + "/signIn.jsp");
        }
    }

}
