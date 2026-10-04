<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Chi tiết Video</title>
</head>
<body>
    <div class="video-detail">
        <div>
            <img src="<c:url value='/images/${video.poster}'/>" onerror="this.onerror=null;this.src='<c:url value='/images/video-placeholder.svg'/>';" width="250" height="180" alt="Poster"/>
        </div>
        <div>
            <div class="eyebrow">Chi tiết video · ${video.categoryName}</div>
            <h1>${video.title}</h1>
            <p class="meta">Mã video: ${video.videoId}</p>
            <p class="stats">${video.views} lượt xem · ${video.shareCount} chia sẻ · ${video.likeCount} yêu thích</p>
            <c:if test="${param.message == 'shared'}"><p class="notice">Đã lưu và gửi video đến email người nhận.</p></c:if>
            <c:if test="${param.message == 'share-failed'}"><p class="notice error">Không gửi được email chia sẻ. Kiểm tra lại email người nhận hoặc cấu hình Gmail.</p></c:if>
            <div class="actions">
                <c:choose>
                    <c:when test="${not empty sessionScope.account}">
                        <form method="post" action="<c:url value='/video/action'/>">
                            <input type="hidden" name="action" value="like">
                            <input type="hidden" name="videoId" value="${video.videoId}">
                            <button type="submit">${userFavorite ? 'Bỏ yêu thích' : 'Yêu thích video'}</button>
                        </form>
                    </c:when>
                    <c:otherwise><a class="button secondary" href="<c:url value='/login'/>">Đăng nhập để yêu thích</a></c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <hr/>
    <div class="content-card"><div class="eyebrow">Mô tả</div><p>${video.description}</p></div>
    <div class="content-card" style="margin-top: 22px;">
        <div class="eyebrow">Chia sẻ</div><h2>Gửi video cho bạn bè</h2>
        <c:choose>
            <c:when test="${not empty sessionScope.account}">
                <form method="post" action="<c:url value='/video/action'/>">
                    <input type="hidden" name="action" value="share">
                    <input type="hidden" name="videoId" value="${video.videoId}">
                    <div class="form-row"><label>Email người nhận<input type="email" name="email" required placeholder="ban@example.com"></label></div>
                    <button type="submit">Chia sẻ qua email</button>
                </form>
            </c:when>
            <c:otherwise><p class="meta">Vui lòng <a href="<c:url value='/login'/>">đăng nhập</a> để chia sẻ video.</p></c:otherwise>
        </c:choose>
    </div>
</body>
</html>