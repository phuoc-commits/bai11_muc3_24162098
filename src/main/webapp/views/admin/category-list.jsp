<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html><head><title>Quản lý danh mục</title></head><body>
<div class="section-heading"><div><div class="eyebrow">Danh mục video</div><h1>Quản lý Category</h1></div><a class="button" href="<c:url value='/admin/category/form'/>">+ Thêm Category</a></div>
<c:if test="${param.error == 'category-in-use'}"><p class="notice error">Không thể xóa Category đang được video sử dụng. Hãy gán lại video trước.</p></c:if>
<table class="data-table"><thead><tr><th>ID</th><th>Tên</th><th>Mã</th><th>Ảnh</th><th>Trạng thái</th><th>Thao tác</th></tr></thead><tbody>
<c:forEach var="category" items="${categories}"><tr><td>${category.categoryId}</td><td>${category.categoryname}</td><td>${category.categorycode}</td><td>${category.images}</td><td>${category.status ? 'Đang bật' : 'Đã tắt'}</td><td><div class="actions"><a href="<c:url value='/admin/category/form?id=${category.categoryId}'/>">Sửa</a><form method="post" action="<c:url value='/admin/category/status'/>"><input type="hidden" name="categoryId" value="${category.categoryId}"><input type="hidden" name="active" value="${!category.status}"><button class="button secondary" type="submit">${category.status ? 'Ngưng' : 'Kích hoạt'}</button></form><form method="post" action="<c:url value='/admin/category/delete'/>"><input type="hidden" name="categoryId" value="${category.categoryId}"><button class="button danger" type="submit" onclick="return confirm('Xóa Category này?')">Xóa</button></form></div></td></tr></c:forEach>
</tbody></table>
</body></html>
