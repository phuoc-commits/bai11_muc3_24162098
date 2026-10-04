<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html><head><title><decorator:title default="Quan tri" /></title><decorator:head /><link rel="stylesheet" href="<c:url value='/assets/app.css'/>"></head>
<body>
<header class="admin-header"><strong>Mạch Video / Quản trị</strong><a href="<c:url value='/admin/users'/>">Người dùng</a><a href="<c:url value='/admin/categories'/>">Category video</a><a href="<c:url value='/admin/product-categories'/>">Category sản phẩm</a><a href="<c:url value='/admin/products'/>">Sản phẩm</a><a href="<c:url value='/home'/>">Về trang chủ</a><a href="<c:url value='/logout'/>">Đăng xuất</a></header>
<main class="admin-main"><decorator:body /></main>
</body></html>
