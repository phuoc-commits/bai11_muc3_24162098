<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html><head><title>User</title></head><body>
<div class="form-card"><div class="eyebrow">Bảng điều khiển</div><h1>${empty user ? 'Thêm User' : 'Cập nhật User'}</h1>
<form method="post" action="<c:url value='/admin/user/save'/>">
<div class="form-row"><label>Username <input name="username" value="${user.username}" ${not empty user ? 'readonly' : ''} required></label></div>
<div class="form-row"><label>Password <input type="password" name="password" value="${user.password}" required></label></div>
<div class="form-row"><label>Họ tên <input name="fullname" value="${user.fullname}"></label></div>
<div class="form-row"><label>Email <input type="email" name="email" value="${user.email}"></label></div>
<div class="form-row"><label>Phone <input name="phone" value="${user.phone}"></label></div>
<div class="form-row"><label>Images <input name="images" value="${user.images}"></label></div>
<p><label><input type="checkbox" name="admin" ${user.admin ? 'checked' : ''}> Admin</label></p>
<p><label><input type="checkbox" name="active" ${empty user or user.active ? 'checked' : ''}> Active</label></p>
<button type="submit">Luu</button>
<a href="<c:url value='/admin/users'/>">Huy</a>
</form></div>
</body></html>
