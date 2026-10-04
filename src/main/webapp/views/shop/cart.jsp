<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html><head><title>Giỏ hàng</title></head><body>
<div class="section-heading"><div><div class="eyebrow">Đơn hàng</div><h1>Giỏ hàng</h1></div><a href="<c:url value='/shop/products'/>">Tiếp tục mua sắm</a></div>
<c:if test="${param.error == 'stock'}"><p class="notice error">Số lượng không hợp lệ hoặc vượt tồn kho. Vui lòng cập nhật giỏ hàng.</p></c:if>
<c:if test="${not empty added}"><p class="notice">Đã thêm sản phẩm vào giỏ.</p></c:if>
<c:if test="${not empty updated}"><p class="notice">Đã cập nhật số lượng.</p></c:if>
<c:choose><c:when test="${empty lines}"><div class="content-card"><p>Giỏ hàng đang trống.</p><a class="button" href="<c:url value='/shop/products'/>">Xem sản phẩm</a></div></c:when><c:otherwise>
<div class="content-card"><table class="data-table"><thead><tr><th>Sản phẩm</th><th>Đơn giá</th><th>Số lượng</th><th>Tạm tính</th><th></th></tr></thead><tbody>
<c:forEach var="line" items="${lines}"><tr><td><strong>${line.product.name}</strong><div class="meta">Còn ${line.product.stock}</div></td><td>${line.product.price} đ</td><td><form class="actions" method="post" action="<c:url value='/cart/update'/>"><input type="hidden" name="productId" value="${line.product.productId}"><input style="max-width:90px;margin:0" type="number" name="quantity" min="1" max="${line.product.stock}" value="${line.quantity}" required><button type="submit">Cập nhật</button></form></td><td>${line.subtotal} đ</td><td><form method="post" action="<c:url value='/cart/remove'/>"><input type="hidden" name="productId" value="${line.product.productId}"><button class="button secondary" type="submit">Xóa</button></form></td></tr></c:forEach>
</tbody></table><div class="section-heading" style="margin-top:24px"><strong>Tổng cộng</strong><strong class="stats">${total} đ</strong></div><a class="button" href="<c:url value='/checkout'/>">Tiến hành thanh toán</a></div>
</c:otherwise></c:choose>
</body></html>
