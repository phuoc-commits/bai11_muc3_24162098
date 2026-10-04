<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html><head><title>Category sản phẩm</title></head><body>
<div class="form-card"><div class="eyebrow">Cửa hàng COD</div><h1>${empty productCategory ? 'Thêm Category sản phẩm' : 'Sửa Category sản phẩm'}</h1>
<c:if test="${not empty error}"><p class="notice error">Tên và mã Category là bắt buộc.</p></c:if>
<form method="post" action="<c:url value='/admin/product-category/save'/>">
<input type="hidden" name="productCategoryId" value="${productCategory.productCategoryId}">
<div class="form-row"><label>Tên Category<input name="name" value="${productCategory.name}" maxlength="100" required></label></div>
<div class="form-row"><label>Mã Category<input name="code" value="${productCategory.code}" maxlength="100" required></label></div>
<div class="form-row"><label>Mô tả<textarea name="description" rows="4" maxlength="500">${productCategory.description}</textarea></label></div>
<p><label><input type="checkbox" name="active" ${empty productCategory or productCategory.active ? 'checked' : ''}> Đang hoạt động</label></p>
<button type="submit">Lưu Category</button> <a class="button secondary" href="<c:url value='/admin/product-categories'/>">Hủy</a>
</form></div>
</body></html>
