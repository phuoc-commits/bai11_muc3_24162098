<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <title><decorator:title default="Trang web" /></title>
    <decorator:head />
    <link rel="stylesheet" href="<c:url value='/assets/app.css'/>">
</head>
<body>
    <header class="site-header">
      <nav class="navbar">
        <a class="brand" href="<c:url value='/home'/>">Mạch Video</a>
        <a class="nav-link" href="<c:url value='/home'/>">Trang chủ</a>
        <a class="nav-link" href="<c:url value='/videos'/>">Danh mục</a>
        <a class="nav-link" href="<c:url value='/shop/products'/>">Cửa hàng</a>
        <a class="nav-link" href="<c:url value='/cart'/>">Giỏ hàng</a>
        <c:if test="${empty sessionScope.account}">
            <a class="nav-link" href="<c:url value='/login'/>">Đăng nhập</a>
        </c:if>
        <c:if test="${not empty sessionScope.account}">
            <span class="account">Xin chào, ${sessionScope.account.fullname}</span>
          <a class="nav-link" href="<c:url value='/order/history'/>">Đơn hàng</a>
            <c:if test="${sessionScope.account.admin}">
                <a class="nav-link" href="<c:url value='/admin/users'/>">Quản trị</a>
            </c:if>
            <a class="nav-link" href="<c:url value='/logout'/>">Đăng xuất</a>
        </c:if>
      </nav>
    </header>

    <main class="page-shell">
        <decorator:body />
    </main>

    <footer class="site-footer">
      <div class="site-footer-inner">
        Dương Minh Phước &nbsp;·&nbsp; MSSV 24162098 &nbsp;·&nbsp; Mã đề 04
      </div>
    </footer>
</body>
</html>