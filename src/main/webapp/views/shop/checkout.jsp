<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html><head><title>Thanh toán COD</title></head><body>
<div class="section-heading"><div><div class="eyebrow">Thanh toán</div><h1>Đặt hàng COD</h1></div></div>
<c:if test="${not empty error}"><p class="notice error"><c:out value="${error}"/></p></c:if>
<div class="video-detail"><section class="form-card"><h2>Thông tin nhận hàng</h2><form method="post" action="<c:url value='/checkout'/>">
<div class="form-row"><label>Người nhận<input name="recipientName" value="${account.fullname}" maxlength="100" required></label></div>
<div class="form-row"><label>Số điện thoại<input name="phone" value="${account.phone}" maxlength="20" required></label></div>
<div class="form-row"><label>Địa chỉ giao hàng<input name="address" maxlength="500" required placeholder="Số nhà, đường, phường/xã, quận/huyện, tỉnh/thành"></label></div>
<div class="notice">Phương thức thanh toán: <strong>Tiền mặt khi nhận hàng (COD)</strong></div>
<button type="submit">Xác nhận đặt hàng</button> <a class="button secondary" href="<c:url value='/cart'/>">Quay lại giỏ</a>
</form></section><aside class="content-card"><h2>Tóm tắt đơn</h2><c:forEach var="line" items="${lines}"><p>${line.product.name} × ${line.quantity}<br><strong>${line.subtotal} đ</strong></p></c:forEach><hr><p class="stats">Tổng thanh toán: ${total} đ</p></aside></div>
</body></html>
