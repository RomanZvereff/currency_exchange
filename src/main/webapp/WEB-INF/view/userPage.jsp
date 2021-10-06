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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
          integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
</head>
<body>


<main>
    <div class="container">
        <div class="row">
            <div class="col-12 d-flex">
                <div>
                    <h1>User Page</h1>
                </div>
                <div>
                    <a href="<%=request.getContextPath()%>/signOut" style="color: red">Sign Out</a>
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-12 col-md-4">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>ORDER DATE</th>
                            <th>TOTAL BASE CURRENCY</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="byDateItem" items="${groupByDate}">
                            <tr>
                                <td><c:out value="${byDateItem.key.time.toLocaleString()}"/></td>
                                <td><c:out value="${byDateItem.value}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-sm-12 col-md-4">
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>CURRENCY</th>
                            <th>TOTAL CURRENCY AMOUNT</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="byCurrItem" items="${groupByCurrency}">
                            <tr>
                                <td><c:out value="${byCurrItem.key}"/></td>
                                <td><c:out value="${byCurrItem.value}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <div class="container-fluid">
        <div class="row">
            <div class="col-sm-12 col-md-12" style="margin-top: 3rem">
                <div>
                    <h2>Orders:</h2>
                </div>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>ORDER NUMBER</th>
                            <th>ORDER DATE</th>
                            <th>RATE DATE</th>
                            <th>CURRENCY</th>
                            <th>BASE CURRENCY</th>
                            <th>OPERATION</th>
                            <th>EXCHANGE RATE</th>
                            <th>CURRENCY AMOUNT</th>
                            <th>BASE CURRENCY AMOUNT</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="order" items="${orderList}">
                            <tr>
                                <td><c:out value="${order.orderNumber}"/></td>
                                <td><c:out value="${order.orderDate.time.toLocaleString()}"/></td>
                                <td><c:out value="${order.rateDate.time.toLocaleString()}"/></td>
                                <td><c:out value="${order.currency}"/></td>
                                <td><c:out value="${order.baseCurrency}"/></td>
                                <td><c:out value="${order.orderOperation}"/></td>
                                <td><c:out value="${order.exchangeRate}"/></td>
                                <td><c:out value="${order.currencyAmount}"/></td>
                                <td><c:out value="${order.baseCurrencyAmount}"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</main>

</body>
</html>
