<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 02.10.2021
  Time: 21:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Currency exchange</title>
    <meta charset="UTF-8">
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
        <div class="row justify-content-center ">
            <div class="form-bl col-4">
                <form action="<%=request.getContextPath()%>/signUp" method="post">
                    <div class="row">
                        <div class="col-12">
                            <div class="form-group">
                                <label for="inputLogin">Login</label>
                                <input type="text" class="form-control" id="inputLogin" name="userLogin" minlength="5"
                                       maxlength="16" required>
                            </div>
                        </div>
                        <div class="col-12">
                            <div class="form-group">
                                <label for="inputPassword">Password</label>
                                <input type="password" class="form-control" id="inputPassword" name="userPass"
                                       minlength="8"
                                       maxlength="16" required>
                            </div>
                        </div>
                        <div class="col-12">
                            <div class="form-group">
                                <label for="confirmPassword">Confirm password</label>
                                <input type="password" class="form-control" id="confirmPassword" name="confirmPass"
                                       minlength="8"
                                       maxlength="16" required>
                            </div>
                            <div>
                                <span style="color: red"><c:out value="${message}" default=""/></span>
                            </div>
                        </div>
                        <div class="col-12">
                            <div class="form-group">
                                <label for="inputFirstName">First name</label>
                                <input type="text" class="form-control" id="inputFirstName" name="userFirstName"
                                       minlength="3"
                                       maxlength="50" required>
                            </div>
                        </div>
                        <div class="col-12">
                            <div class="form-group">
                                <label for="inputLastName">Last name</label>
                                <input type="text" class="form-control" id="inputLastName" name="userLastName"
                                       minlength="3"
                                       maxlength="50" required>
                            </div>
                        </div>
                        <div class="col-12">
                            <div class="form-group">
                                <button type="submit" class="btn btn-success">Sign Up</button>
                                <a class="btn btn-link" href="<%=request.getContextPath()%>/signIn">Sign In</a>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</main>

</body>
</html>
