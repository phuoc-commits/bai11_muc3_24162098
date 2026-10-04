<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html><head><title>Cửa hàng</title></head><body>
<div class="section-heading"><div><div class="eyebrow">Cửa hàng</div><h1>Sản phẩm</h1><p class="meta">Chọn món bạn cần, thanh toán khi nhận hàng.</p></div><a class="button secondary" href="<c:url value='/cart'/>">Giỏ hàng (${cartCount})</a></div>
<c:if test="${param.error == 'stock'}"><p class="notice error">Số lượng yêu cầu không hợp lệ hoặc đã vượt tồn kho.</p></c:if>
<div class="video-grid">
<c:forEach var="p" items="${products}">
<article class="video-card">
<img src="<c:url value='/images/${p.image}'/>" alt="${p.name}" onerror="this.onerror=null;this.src='<c:url value='/images/video-placeholder.svg'/>';">
<div class="video-card-body"><div class="eyebrow">${p.productCategoryName} · Còn ${p.stock}</div><h3>${p.name}</h3><p class="meta">${p.description}</p><p class="stats">${p.price} đ</p>
<c:choose><c:when test="${p.stock gt 0}"><form method="post" action="<c:url value='/cart/add'/>"><input type="hidden" name="productId" value="${p.productId}"><div class="form-row"><label>Số lượng<input type="number" name="quantity" value="1" min="1" max="${p.stock}" required></label></div><button type="submit">Thêm vào giỏ</button></form></c:when><c:otherwise><span class="meta">Tạm hết hàng</span></c:otherwise></c:choose>
</div></article>
</c:forEach>
</div>
</body></html>
