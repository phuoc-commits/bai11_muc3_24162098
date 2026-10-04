<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html><head><title>Quản lý User</title></head><body>
<div class="section-heading"><div><div class="eyebrow">Bảng điều khiển</div><h1>Người dùng</h1></div><a class="button" href="<c:url value='/admin/user/form'/>">+ Thêm User</a></div>
<table class="data-table">
<tr><th>Username</th><th>Ho ten</th><th>Email</th><th>Admin</th><th>Active</th><th>Thao tac</th></tr>
<c:forEach var="u" items="${users}">
<tr>
<td>${u.username}</td><td>${u.fullname}</td><td>${u.email}</td>
<td>${u.admin ? 'Co' : 'Khong'}</td><td>${u.active ? 'Co' : 'Khong'}</td>
<td><div class="actions"><a href="<c:url value='/admin/user/form?username=${u.username}'/>">Sửa</a>
<a href="<c:url value='/admin/user/delete?username=${u.username}'/>" onclick="return confirm('Xoa user nay?')">Xoa</a></div></td>
</tr>
</c:forEach>
</table>
<p>
<c:forEach var="i" begin="1" end="${totalPages}">
<a href="<c:url value='/admin/users?page=${i}'/>" class="${i == currentPage ? 'active' : ''}">${i}</a>
</c:forEach>
</p>
</body></html>
