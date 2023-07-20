<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- ▽ jstl을 적용하는데 사용되는 라이브러리 태그 d--%>
<%-- ▽ 적용하면 자바의 여러 기능을 사용할수 있다. (태그양식마다 지정된 기능이 다르다) d--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>MALL</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">쇼핑몰</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="collapsibleNavbar">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="/">상품목록</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/write">상품등록</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<div class="container mt-3">
    <table class="table">
        <thead>
            <tr>
                <th>상품번호</th>
                <th>상품명</th>
                <th>상품가격</th>
                <th>상품재고</th>
            </tr>
        </thead>
        <tbody>
        <%-- '$' 는 request 객체에 접근한다.(문법)--%>
        <%-- '${}' 는 request 객체에 있는 값을 가져오는 역할이다.(문법)--%>
        <c:forEach var="p" items="${productList}">
            <tr>
                    <%-- '${p.id}'라고 적어야 자바의 p.getId 처럼 접근한다.(문법)--%>
                <td>${p.id}</td>
                <td><a href="/product/${p.id}">${p.name}</a></td>
                <td>${p.price}원</td>
                <td>${p.qty}</td>
            </tr>
        </c:forEach>


        </tbody>
    </table>
</div>

</body>
</html>