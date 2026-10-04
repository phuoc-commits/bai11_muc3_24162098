<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html><head><title>Hóa đơn COD</title><style>@media print {.site-header,.site-footer,.no-print{display:none!important}.page-shell,.content-card{padding:0!important;box-shadow:none!important;border:0!important}}</style></head><body>
<section class="content-card" style="max-width:900px;margin:0 auto">
	<div class="section-heading"><div><div class="eyebrow">Đơn hàng đã được tiếp nhận</div><h1>Hóa đơn COD #${invoice.orderId}</h1></div><button class="no-print" type="button" onclick="window.print()">In hóa đơn</button></div>
	<div class="video-grid" style="grid-template-columns:repeat(2,minmax(0,1fr));margin-bottom:24px">
		<div><p><strong>Ngày đặt:</strong> <fmt:formatDate value="${invoice.orderDate}" pattern="dd/MM/yyyy HH:mm"/></p><p><strong>Tài khoản:</strong> <c:out value="${invoice.username}"/></p><p><strong>Trạng thái:</strong> <c:out value="${invoice.status}"/></p></div>
		<div><p><strong>Người nhận:</strong> <c:out value="${invoice.recipientName}"/></p><p><strong>Điện thoại:</strong> <c:out value="${invoice.phone}"/></p><p><strong>Địa chỉ:</strong> <c:out value="${invoice.shippingAddress}"/></p></div>
	</div>
	<table class="data-table"><thead><tr><th>Sản phẩm</th><th>Đơn giá</th><th>Số lượng</th><th>Thành tiền</th></tr></thead><tbody>
	<c:forEach var="item" items="${invoice.items}"><tr><td><c:out value="${item.productName}"/></td><td><fmt:formatNumber value="${item.unitPrice}" pattern="#,##0.##"/> đ</td><td>${item.quantity}</td><td><fmt:formatNumber value="${item.subtotal}" pattern="#,##0.##"/> đ</td></tr></c:forEach>
	</tbody></table>
	<div class="section-heading" style="margin-top:24px"><span>Thanh toán: <strong>Tiền mặt khi nhận hàng (COD)</strong></span><strong class="stats">Tổng cộng: <fmt:formatNumber value="${invoice.totalAmount}" pattern="#,##0.##"/> đ</strong></div>
	<p><strong>Trạng thái đơn:</strong>
		<c:choose>
			<c:when test="${invoice.status == 'NEW'}">Đơn hàng mới</c:when>
			<c:when test="${invoice.status == 'CONFIRMED'}">Đã xác nhận</c:when>
			<c:when test="${invoice.status == 'PREPARING'}">Chuẩn bị hàng</c:when>
			<c:when test="${invoice.status == 'SHIPPING'}">Vận chuyển</c:when>
			<c:when test="${invoice.status == 'DELIVERING'}">Đang giao hàng</c:when>
			<c:when test="${invoice.status == 'DELIVERED'}">Đã giao</c:when>
			<c:when test="${invoice.status == 'CANCELLED'}">Đơn hàng hủy</c:when>
			<c:when test="${invoice.status == 'RETURNED'}">Đơn hàng hoàn</c:when>
			<c:otherwise><c:out value="${invoice.status}"/></c:otherwise>
		</c:choose>
	</p>
	<p class="meta">Vui lòng chuẩn bị đúng số tiền khi nhận hàng.</p>
	<div class="actions no-print"><a class="button" href="<c:url value='/shop/products'/>">Tiếp tục mua sắm</a><a class="button secondary" href="<c:url value='/order/history'/>">Lịch sử đơn hàng</a><a class="button secondary" href="<c:url value='/home'/>">Về trang chủ</a></div>
</section>
</body></html>
