<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Danh sách Video theo Danh mục</title>
</head>
<body>
    <div class="section-heading"><div><div class="eyebrow">Danh mục video</div><h1>Khám phá nội dung</h1></div><span class="meta">${totalVideos} video</span></div>

    <div class="video-grid">
        <c:forEach var="v" items="${videos}">
            <article class="video-card">
                <img src="<c:url value='/images/${v.poster}'/>" onerror="this.onerror=null;this.src='<c:url value='/images/video-placeholder.svg'/>';" width="100%" height="150" alt="Video poster"/>
                <div class="video-card-body"><h3>${v.title}</h3>
                <p class="meta">${v.categoryName} · ${v.videoId}</p>
                <p class="stats">${v.views} lượt xem · ${v.shareCount} chia sẻ · ${v.likeCount} yêu thích</p>
                <a class="button secondary" href="<c:url value='/video/detail?id=${v.videoId}'/>">Xem chi tiết</a></div>
            </article>
        </c:forEach>
    </div>

    <div class="pagination">
        <a href="<c:url value='/video/category?catId=${categoryId}&page=1'/>">&lt;&lt;&lt;</a>
        <c:forEach var="i" begin="1" end="${totalPages}">
            <a href="<c:url value='/video/category?catId=${categoryId}&page=${i}'/>" 
               class="${i == currentPage ? 'active' : ''}">${i}</a>
        </c:forEach>
        <a href="<c:url value='/video/category?catId=${categoryId}&page=${totalPages}'/>">&gt;&gt;&gt;</a>
    </div>
</body>
</html>