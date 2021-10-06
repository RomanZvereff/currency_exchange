<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 03.10.2021
  Time: 23:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Currency exchange</title>
    <meta charset="UTF-8">
    <meta name="author" content="Roman Zvieriev">
    <meta name="description" content="Currency exchange service">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
    <style>
        .form-bl {
            height: 100vh;
            padding-top: 5rem;
        }
    </style>
</head>
<body>

<main>
    <div class="container">
        <div class="row justify-content-center">
            <div class="form-bl col-sm-12 col-md-4">
                <form action="<%=request.getContextPath()%>/signIn" method="post">
                    <div class="form-group">
                        <label for="inputLogin">Login:</label>
                        <input type="text" class="form-control" id="inputLogin" name="userLogin" minlength="5"
                               maxlength="16" required>
                    </div>
                    <div class="form-group">
                        <label for="inputPassword">Password:</label>
                        <input type="password" class="form-control" id="inputPassword" name="userPassword" minlength="8"
                               maxlength="16" required>
                    </div>
                    <%
                        String message = (String) session.getAttribute("message");
                        request.setAttribute("message", message);
                    %>
                    <c:if test="${message != null}">
                        <div>
                            <span id="message" class="invalid-login" style="color: red">${message}</span>
                        </div>
                    </c:if>
                    <button type="submit" class="btn btn-success">Sign in</button>
                    <a class="btn btn-link" href="<%=request.getContextPath()%>/signUp">Sign Up</a>
                </form>
            </div>
        </div>
    </div>
</main>

</body>
</html>
