<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html><head><title>Đăng nhập</title></head><body>
<div class="form-card">
    <div class="eyebrow">Mạch Video</div><h1>Đăng nhập</h1><p class="meta">Tiếp tục hành trình xem video của bạn.</p>
    <c:if test="${not empty param.registered}"><p class="notice">Đăng ký thành công. Hãy đăng nhập.</p></c:if>
    <c:if test="${not empty msg}"><p class="notice error">${msg}</p></c:if>
    <form method="post" action="<c:url value='/login'/>">
        <div class="form-row"><label>Tên đăng nhập<input name="username" required></label></div>
        <div class="form-row"><label>Mật khẩu<input type="password" name="password" required></label></div>
        <button type="submit">Đăng nhập</button>
    </form>
    <p class="meta">Chưa có tài khoản? <a href="<c:url value='/register'/>">Đăng ký ngay</a></p>
</div>
</body></html>
