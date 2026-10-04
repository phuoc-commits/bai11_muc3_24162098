<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html><head><title>Lịch sử đặt hàng</title></head><body>
<div class="section-heading"><div><div class="eyebrow">Tài khoản</div><h1>Lịch sử đặt hàng</h1></div><a class="button secondary" href="<c:url value='/shop/products'/>">Tiếp tục mua sắm</a></div>
<form class="content-card order-filter" method="get" action="<c:url value='/order/history'/>">
<label for="status">Lọc theo trạng thái</label>
<select id="status" name="status" onchange="this.form.submit()">
<option value="ALL" ${selectedStatus == 'ALL' ? 'selected' : ''}>Tất cả đơn hàng</option>
<option value="NEW" ${selectedStatus == 'NEW' ? 'selected' : ''}>Đơn hàng mới</option>
<option value="CONFIRMED" ${selectedStatus == 'CONFIRMED' ? 'selected' : ''}>Đã xác nhận</option>
<option value="PREPARING" ${selectedStatus == 'PREPARING' ? 'selected' : ''}>Chuẩn bị hàng</option>
<option value="SHIPPING" ${selectedStatus == 'SHIPPING' ? 'selected' : ''}>Vận chuyển</option>
<option value="DELIVERING" ${selectedStatus == 'DELIVERING' ? 'selected' : ''}>Đang giao hàng</option>
<option value="DELIVERED" ${selectedStatus == 'DELIVERED' ? 'selected' : ''}>Đã giao</option>
<option value="CANCELLED" ${selectedStatus == 'CANCELLED' ? 'selected' : ''}>Đơn hàng hủy</option>
<option value="RETURNED" ${selectedStatus == 'RETURNED' ? 'selected' : ''}>Đơn hàng hoàn</option>
</select>
<noscript><button type="submit">Lọc</button></noscript>
</form>
<c:choose><c:when test="${empty orders}"><div class="content-card"><p>Không có đơn hàng ở trạng thái này.</p></div></c:when><c:otherwise>
<table class="data-table order-history-table"><thead><tr><th>Mã đơn</th><c:if test="${sessionScope.account.admin}"><th>Khách hàng</th></c:if><th>Ngày đặt</th><th>Tổng tiền</th><th>Trạng thái</th><th>Thanh toán</th><th></th></tr></thead><tbody>
<c:forEach var="order" items="${orders}"><tr>
<td>#${order.orderId}</td><c:if test="${sessionScope.account.admin}"><td><c:out value="${order.username}"/></td></c:if>
<td><fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm"/></td>
<td><fmt:formatNumber value="${order.totalAmount}" pattern="#,##0.##"/> đ</td>
<td><span class="status-badge status-${order.status}"><c:choose>
<c:when test="${order.status == 'NEW'}">Đơn hàng mới</c:when>
<c:when test="${order.status == 'CONFIRMED'}">Đã xác nhận</c:when>
<c:when test="${order.status == 'PREPARING'}">Chuẩn bị hàng</c:when>
<c:when test="${order.status == 'SHIPPING'}">Vận chuyển</c:when>
<c:when test="${order.status == 'DELIVERING'}">Đang giao hàng</c:when>
<c:when test="${order.status == 'DELIVERED'}">Đã giao</c:when>
<c:when test="${order.status == 'CANCELLED'}">Đơn hàng hủy</c:when>
<c:when test="${order.status == 'RETURNED'}">Đơn hàng hoàn</c:when>
<c:otherwise><c:out value="${order.status}"/></c:otherwise>
</c:choose></span></td>
<td><c:out value="${order.paymentMethod}"/></td>
<td><a href="<c:url value='/order/success?id=${order.orderId}'/>">Xem hóa đơn</a></td>
</tr></c:forEach>
</tbody></table>
</c:otherwise></c:choose>
</body></html>
