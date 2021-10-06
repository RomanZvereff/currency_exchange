<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 05.10.2021
  Time: 22:11
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
        <div class="row justify-content-center">
            <div class="form-bl col-sm-12 col-md-4">
                <c:choose>

                    <c:when test="${exchangeRate != null}">
                        <form action="<%=request.getContextPath()%>/updatePage" method="post">
                            <div class="form-group">
                                <label for="inputCurrency">Currency:</label>
                                <input type="text" class="form-control" id="inputCurrency" name="currency"
                                       value="<c:out value="${exchangeRate.exchangeRateCcy}"/>" required>
                            </div>
                            <div class="form-group">
                                <label for="inputBaseCurrency">Base currency:</label>
                                <input type="text" class="form-control" id="inputBaseCurrency" name="baseCurrency"
                                       value="<c:out value="${exchangeRate.exchangeRateBaseCcy}"/>" required>
                            </div>
                            <div class="form-group">
                                <label for="inputBuy">Buy:</label>
                                <input type="number" step="0.001" class="form-control" id="inputBuy" name="buy"
                                       value="<c:out value="${exchangeRate.exchangeRateBuy}"/>" required>
                            </div>
                            <div class="form-group">
                                <label for="inputBaseSale">Sale:</label>
                                <input type="number" step="0.001"  class="form-control" id="inputBaseSale" name="sale"
                                       value="<c:out value="${exchangeRate.exchangeRateSale}"/>" required>
                            </div>
                            <input type="hidden" name="rateId" value="<c:out value='${exchangeRate.exchangeRateId}' />" />
                            <button type="submit" class="btn btn-success">Save</button>
                        </form>
                    </c:when>

                    <c:when test="${order != null}">
                        <form action="<%=request.getContextPath()%>/updatePage" method="post">
                            <div class="form-group">
                                <label for="inputOperation">Operation:</label>
                                <input type="text" class="form-control" id="inputOperation" name="orderOperation"
                                       value="<c:out value="${order.orderOperation}"/>" placeholder="BUY / SALE" required>
                            </div>
                            <div class="form-group">
                                <label for="inputExchRate">Base currency:</label>
                                <input type="number" step="0.001" class="form-control" id="inputExchRate" name="exchangeRate"
                                       value="<c:out value="${order.exchangeRate}"/>" required>
                            </div>
                            <div class="form-group">
                                <label for="inputCurrAmt">Currency amount:</label>
                                <input type="number" step="0.001" class="form-control" id="inputCurrAmt" name="currencyAmount"
                                       value="<c:out value="${order.currencyAmount}"/>" required>
                            </div>
                            <div class="form-group">
                                <label for="inputBaseCurrAmt">Base currency amount:</label>
                                <input type="number" step="0.001" class="form-control" id="inputBaseCurrAmt" name="baseCurrencyAmount"
                                       value="<c:out value="${order.baseCurrencyAmount}"/>" required>
                            </div>
                            <div class="form-group">
                                <label for="inputStatus">Status:</label>
                                <input type="text" class="form-control" id="inputStatus" name="orderStatus"
                                       value="<c:out value="${order.orderStatus}"/>" required>
                            </div>
                            <input type="hidden" name="orderId" value="<c:out value='${order.orderId}' />" />
                            <button type="submit" class="btn btn-success">Save</button>
                        </form>
                    </c:when>

                </c:choose>
            </div>
        </div>
    </div>
</main>

</body>
</html>
