<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <link href="webjars/bootstrap/3.2.0/css/bootstrap.css" rel="stylesheet">
    <script src="webjars/jquery/1.11.1/jquery.min.js"></script>
    <script src="webjars/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <title>TOS: Tariffs</title>
</head>
<div class="container">
    <jsp:include page="/mainmenu"></jsp:include>
</div>
<body>
<div class="container col-lg-5 col-lg-offset-3" style="margin-top: 5%">
    <c:choose>
        <c:when test="${not empty tariffs}">
            <ul class="list-group">
                <c:forEach var="listValue" items="${tariffs}">
                    <li class="list-group-item">
                        <p>Operator: ${listValue.operator.name}</p>
                        <p>Tariff: ${listValue.name}</p>
                        <p>Description: ${listValue.description}</p>
                    </li>
                </c:forEach>
            </ul>
        </c:when>
        <c:otherwise>
            Oops! Empty list.
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>