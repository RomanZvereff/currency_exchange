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
                    <h1>Admin Page</h1>
                </div>
                <div>
                    <a href="<%=request.getContextPath()%>/signOut" style="color: red">Sign Out</a>
                </div>
            </div>
        </div>
    </div>
    <div class="container-fluid">
        <div class="row">

            <div class="col-sm-12 col-md-12" style="margin-top: 3rem">
                <div>
                    <h2>Exchange rates:</h2>
                </div>
                <div>
                    <a class="btn btn-success" href="<%=request.getContextPath()%>/createPage">Create exchange rate</a>
                </div>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>EXCH_RATE_ID</th>
                            <th>RATE_DT</th>
                            <th>CCY</th>
                            <th>BASE_CCY</th>
                            <th>BUY</th>
                            <th>SALE</th>
                            <th>ACTION</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="exchange_rate" items="${exchangeRateList}">
                            <tr>
                                <td><c:out value="${exchange_rate.exchangeRateId}"/></td>
                                <td><c:out value="${exchange_rate.exchangeRateDate.time.toLocaleString()}"/></td>
                                <td><c:out value="${exchange_rate.exchangeRateCcy}"/></td>
                                <td><c:out value="${exchange_rate.exchangeRateBaseCcy}"/></td>
                                <td><c:out value="${exchange_rate.exchangeRateBuy}"/></td>
                                <td><c:out value="${exchange_rate.exchangeRateSale}"/></td>
                                <td><a href="<%=request.getContextPath()%>/updatePage?rateId=<c:out value="${exchange_rate.exchangeRateId}"/>">update</a>
                                    <a href="<%=request.getContextPath()%>/adminPage?action=deleteRate&rateId=<c:out value="${exchange_rate.exchangeRateId}"/>">delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="col-sm-12 col-md-12" style="margin-top: 3rem">
                <div>
                    <h2>Orders:</h2>
                </div>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>ORDER_ID</th>
                            <th>ORDER_NUM</th>
                            <th>ORDER_DT</th>
                            <th>RATE_DT</th>
                            <th>CCY</th>
                            <th>BASE_CCY</th>
                            <th>ORDER_OPER</th>
                            <th>EXCH_RATE</th>
                            <th>CCY_AMT</th>
                            <th>BASE_CCY_AMT</th>
                            <th>ORDER_STATUS</th>
                            <th>ACTION</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="order" items="${orderList}">
                        <tr>
                            <td><c:out value="${order.orderId}"/></td>
                            <td><c:out value="${order.orderNumber}"/></td>
                            <td><c:out value="${order.orderDate.time.toLocaleString()}"/></td>
                            <td><c:out value="${order.rateDate.time.toLocaleString()}"/></td>
                            <td><c:out value="${order.currency}"/></td>
                            <td><c:out value="${order.baseCurrency}"/></td>
                            <td><c:out value="${order.orderOperation}"/></td>
                            <td><c:out value="${order.exchangeRate}"/></td>
                            <td><c:out value="${order.currencyAmount}"/></td>
                            <td><c:out value="${order.baseCurrencyAmount}"/></td>
                            <td><c:out value="${order.orderStatus}"/></td>
                            <td><a href="<%=request.getContextPath()%>/updatePage?orderId=<c:out value="${order.orderId}"/>">update</a>
                                <a href="<%=request.getContextPath()%>/adminPage?action=deleteOrder&orderId=<c:out value="${order.orderId}"/>">delete</a>
                                </td>
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
