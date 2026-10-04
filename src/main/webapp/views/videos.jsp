<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html><head><title>Danh mục</title></head><body>
<div class="section-heading"><div><div class="eyebrow">Thư viện</div><h1>Chọn một danh mục</h1></div></div>
<div class="video-grid">
	<c:forEach var="category" items="${categories}">
		<a class="content-card" href="${pageContext.request.contextPath}/video/category?catId=${category.categoryId}"><div class="eyebrow">Category ${category.categorycode}</div><h2>${category.categoryname}</h2></a>
	</c:forEach>
</div>
</body></html>
