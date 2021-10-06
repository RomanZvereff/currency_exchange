<%--
  Created by IntelliJ IDEA.
  User: roman
  Date: 05.10.2021
  Time: 22:17
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
                <form action="<%=request.getContextPath()%>/createPage" method="post">
                    <div class="form-group">
                        <label for="inputCurrency">Currency:</label>
                        <input type="text" class="form-control" id="inputCurrency" name="currency"
                               placeholder="USD" required>
                    </div>
                    <div class="form-group">
                        <label for="inputBaseCurrency">Base currency:</label>
                        <input type="text" class="form-control" id="inputBaseCurrency" name="baseCurrency"
                               placeholder="UAH" required>
                    </div>
                    <div class="form-group">
                        <label for="inputBuy">Buy:</label>
                        <input type="number" step="0.001" class="form-control" id="inputBuy" name="buy"
                               placeholder="7.99" required>
                    </div>
                    <div class="form-group">
                        <label for="inputBaseSale">Sale:</label>
                        <input type="number" step="0.001"  class="form-control" id="inputBaseSale" name="sale"
                               placeholder="8.15" required>
                    </div>
                    <button type="submit" class="btn btn-success">Save</button>
                </form>
            </div>
        </div>
    </div>
</main>

</body>
</html>
