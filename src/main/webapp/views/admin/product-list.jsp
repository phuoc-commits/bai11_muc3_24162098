<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html><head><title>Quản lý sản phẩm</title></head><body>
<div class="section-heading"><div><div class="eyebrow">Cửa hàng COD</div><h1>Quản lý sản phẩm</h1></div><a class="button" href="<c:url value='/admin/product/form'/>">+ Thêm sản phẩm</a></div>
<table class="data-table"><thead><tr><th>ID</th><th>Sản phẩm</th><th>Category</th><th>Giá</th><th>Tồn kho</th><th>Ảnh</th><th>Trạng thái</th><th>Thao tác</th></tr></thead><tbody>
<c:forEach var="product" items="${products}"><tr><td>${product.productId}</td><td><strong>${product.name}</strong><div class="meta">${product.description}</div></td><td>${product.productCategoryName}</td><td>${product.price} đ</td><td>${product.stock}</td><td>${product.image}</td><td>${product.active ? 'Đang bán' : 'Đã ẩn'}</td><td><div class="actions"><a href="<c:url value='/admin/product/form?id=${product.productId}'/>">Sửa</a><form method="post" action="<c:url value='/admin/product/status'/>"><input type="hidden" name="productId" value="${product.productId}"><input type="hidden" name="active" value="${!product.active}"><button class="button secondary" type="submit">${product.active ? 'Ngưng bán' : 'Mở bán'}</button></form><form method="post" action="<c:url value='/admin/product/delete'/>"><input type="hidden" name="productId" value="${product.productId}"><button class="button danger" type="submit" onclick="return confirm('Xóa sản phẩm? Dữ liệu hóa đơn cũ vẫn được giữ.')">Xóa</button></form></div></td></tr></c:forEach>
</tbody></table>
</body></html>
