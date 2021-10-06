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
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"
          integrity="sha384-DyZ88mC6Up2uqS4h/KRgHuoeGwBcD4Ng9SiP4dIRy0EXTlnuz47vAwmeGwVChigm" crossorigin="anonymous">

    <style>
        p {
            margin: 1rem;
        }

        select {
            padding: 3px;
        }

        input,
        .reserve-btn {
            margin: 1rem;
        }

        .item-bl {
            border: 1px solid #e8e8e8;
            margin: 1rem;
        }
    </style>
</head>
<body>

<main>
    <div class="container" style="padding-top: 3rem;">
        <div class="row justify-content-center">
            <div class="form-bl col-sm-12 col-md-8">

                <c:forEach var="rate" items="${exchangeRateList}">
                    <form action="<%=request.getContextPath()%>/exchangePage" method="post">
                        <div class="form-group d-flex flex-column">
                            <div class="item-bl">
                                <div class="d-flex">
                                    <p>${rate.exchangeRateCcy} / ${rate.exchangeRateBaseCcy}</p>
                                    <p>${rate.exchangeRateBuy}<i
                                            class="fas fa-long-arrow-alt-down"></i> ${rate.exchangeRateSale}<i
                                            class="fas fa-long-arrow-alt-up"></i></p>
                                </div>
                                <div>
                                    <p>
                                        <select class="form-select" name="operation">
                                            <option value="buy">Buy</option>
                                            <option value="sale">Sale</option>
                                        </select>
                                    </p>
                                </div>
                                <div>
                                    <input type="number" step="0.01" class="form-control" id="inputCurrency"
                                           name="amount"
                                           style="width: 80%;">
                                    <input type="hidden" name="rateId"
                                           value="<c:out value='${rate.exchangeRateId}' />"/>
                                </div>
                                <div class="reserve-btn">
                                    <button type="submit" class="btn btn-success">Reserve</button>
                                </div>
                            </div>
                        </div>
                    </form>
                </c:forEach>

            </div>
        </div>
    </div>
</main>

</body>
</html>
