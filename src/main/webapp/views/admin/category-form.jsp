<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html><head><title>Category</title></head><body>
<div class="form-card"><div class="eyebrow">Danh mục video</div><h1>${empty category ? 'Thêm Category' : 'Sửa Category'}</h1>
<c:if test="${not empty error}"><p class="notice error">Vui lòng nhập tên và mã Category.</p></c:if>
<form method="post" action="<c:url value='/admin/category/save'/>">
<input type="hidden" name="categoryId" value="${category.categoryId}">
<div class="form-row"><label>Tên Category<input name="categoryname" value="${category.categoryname}" maxlength="100" required></label></div>
<div class="form-row"><label>Mã Category<input name="categorycode" value="${category.categorycode}" maxlength="100" required></label></div>
<div class="form-row"><label>Ảnh<input name="images" value="${category.images}" maxlength="500"></label></div>
<p><label><input type="checkbox" name="status" ${empty category or category.status ? 'checked' : ''}> Đang hoạt động</label></p>
<button type="submit">Lưu Category</button> <a class="button secondary" href="<c:url value='/admin/categories'/>">Hủy</a>
</form></div>
</body></html>
