<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html><head><title>Sản phẩm</title></head><body>
<div class="form-card"><div class="eyebrow">Cửa hàng COD</div><h1>${empty product ? 'Thêm sản phẩm' : 'Sửa sản phẩm'}</h1>
<c:if test="${not empty error}"><p class="notice error">Tên, giá và tồn kho không hợp lệ.</p></c:if>
<form method="post" action="<c:url value='/admin/product/save'/>">
<input type="hidden" name="productId" value="${product.productId}">
<div class="form-row"><label>Tên sản phẩm<input name="name" value="${product.name}" maxlength="150" required></label></div>
<div class="form-row"><label>Danh mục sản phẩm<select name="productCategoryId" required><option value="">Chọn danh mục</option><c:forEach var="category" items="${productCategories}"><option value="${category.productCategoryId}" ${product.productCategoryId == category.productCategoryId ? 'selected' : ''} ${category.active ? '' : 'disabled'}>${category.name}${category.active ? '' : ' (đã tắt)'}</option></c:forEach></select></label></div>
<div class="form-row"><label>Mô tả<textarea name="description" rows="4" maxlength="4000">${product.description}</textarea></label></div>
<div class="form-row"><label>Giá (VND)<input type="number" name="price" min="0" step="1000" value="${product.price}" required></label></div>
<div class="form-row"><label>Tồn kho<input type="number" name="stock" min="0" step="1" value="${empty product ? 0 : product.stock}" required></label></div>
<div class="form-row"><label>Tên file ảnh trong /images<input name="image" value="${product.image}" maxlength="255" placeholder="product-headphones.svg"></label></div>
<p><label><input type="checkbox" name="active" ${empty product or product.active ? 'checked' : ''}> Đang bán</label></p>
<button type="submit">Lưu sản phẩm</button> <a class="button secondary" href="<c:url value='/admin/products'/>">Hủy</a>
</form></div>
</body></html>
