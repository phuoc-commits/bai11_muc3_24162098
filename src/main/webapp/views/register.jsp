<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html><head><title>Đăng ký</title></head><body>
<div class="form-card">
<div class="eyebrow">Thành viên mới</div><h1>Đăng ký tài khoản</h1>
<c:if test="${not empty msg}"><p class="notice ${verifyStep ? '' : 'error'}">${msg}</p></c:if>
<c:choose>
<c:when test="${verifyStep}">
    <form method="post" action="<c:url value='/register'/>">
        <div class="form-row"><label>Mã OTP<input name="otp" required maxlength="6" inputmode="numeric"></label></div>
        <button type="submit">Xác nhận OTP</button>
    </form>
</c:when>
<c:otherwise>
    <form method="post" action="<c:url value='/register'/>">
        <div class="form-row"><label>Tên đăng nhập<input name="username" required maxlength="50"></label></div>
        <div class="form-row"><label>Mật khẩu<input type="password" name="password" required></label></div>
        <div class="form-row"><label>Họ tên<input name="fullname" required></label></div>
        <div class="form-row"><label>Email<input type="email" name="email" required></label></div>
        <div class="form-row"><label>Số điện thoại<input name="phone"></label></div>
        <button type="submit">Gửi mã OTP</button>
    </form>
</c:otherwise>
</c:choose>
<p class="meta"><a href="<c:url value='/login'/>">Quay lại đăng nhập</a></p>
</div>
</body></html>
