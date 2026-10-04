<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html><head><title>Category sản phẩm</title></head><body>
<div class="section-heading"><div><div class="eyebrow">Cửa hàng COD</div><h1>Category sản phẩm</h1></div><a class="button" href="<c:url value='/admin/product-category/form'/>">+ Thêm Category</a></div>
<c:if test="${param.error == 'category-in-use'}"><p class="notice error">Category vẫn đang được sản phẩm sử dụng. Hãy chuyển sản phẩm sang category khác trước khi xóa.</p></c:if>
<table class="data-table"><thead><tr><th>ID</th><th>Tên</th><th>Mã</th><th>Mô tả</th><th>Trạng thái</th><th>Thao tác</th></tr></thead><tbody>
<c:forEach var="category" items="${productCategories}"><tr><td>${category.productCategoryId}</td><td>${category.name}</td><td>${category.code}</td><td>${category.description}</td><td>${category.active ? 'Đang bật' : 'Đã tắt'}</td><td><div class="actions"><a href="<c:url value='/admin/product-category/form?id=${category.productCategoryId}'/>">Sửa</a><form method="post" action="<c:url value='/admin/product-category/status'/>"><input type="hidden" name="productCategoryId" value="${category.productCategoryId}"><input type="hidden" name="active" value="${!category.active}"><button class="button secondary" type="submit">${category.active ? 'Ngưng' : 'Kích hoạt'}</button></form><form method="post" action="<c:url value='/admin/product-category/delete'/>"><input type="hidden" name="productCategoryId" value="${category.productCategoryId}"><button class="button danger" type="submit" onclick="return confirm('Xóa Category sản phẩm này?')">Xóa</button></form></div></td></tr></c:forEach>
</tbody></table>
</body></html>
