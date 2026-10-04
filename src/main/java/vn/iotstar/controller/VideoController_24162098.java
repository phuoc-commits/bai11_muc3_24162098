package vn.iotstar.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import vn.iotstar.entity.Video_24162098;
import vn.iotstar.entity.User_24162098;
import vn.iotstar.service.IVideoService_24162098;
import vn.iotstar.service.VideoServiceImpl_24162098;

@WebServlet(urlPatterns = {"/video/detail", "/video/category", "/video/action"})
public class VideoController_24162098 extends HttpServlet {
    private IVideoService_24162098 videoService = new VideoServiceImpl_24162098();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        if (path.equals("/video/detail")) {
            String id = req.getParameter("id");
            Video_24162098 video = videoService.findById(id);
            req.setAttribute("video", video);
            User_24162098 account = (User_24162098) req.getSession().getAttribute("account");
            req.setAttribute("userFavorite", account != null && videoService.isFavorite(id, account.getUsername()));
            req.setAttribute("message", req.getParameter("message"));
            req.getRequestDispatcher("/views/video-detail.jsp").forward(req, resp);
            return;
        }

        if (path.equals("/video/category")) {
            int categoryId = Integer.parseInt(req.getParameter("catId"));
            int page = 1;
            int pageSize = 3;
            if (req.getParameter("page") != null) {
                page = Integer.parseInt(req.getParameter("page"));
            }

            List<Video_24162098> videos = videoService.findByCategoryIdPaging(categoryId, page, pageSize);
            int totalVideos = videoService.countTotalVideosByCategoryId(categoryId); // Câu 6: Đếm số lượng video
            int totalPages = (int) Math.ceil((double) totalVideos / pageSize);

            req.setAttribute("videos", videos);
            req.setAttribute("totalVideos", totalVideos);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);
            req.setAttribute("categoryId", categoryId);
            req.getRequestDispatcher("/views/video-by-category.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getServletPath().equals("/video/action")) return;

        User_24162098 account = (User_24162098) req.getSession().getAttribute("account");
        String videoId = req.getParameter("videoId");
        if (account == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");
        if ("like".equals(action)) {
            videoService.toggleFavorite(videoId, account.getUsername());
            resp.sendRedirect(req.getContextPath() + "/video/detail?id=" + videoId);
            return;
        }

        if ("share".equals(action)) {
            String email = req.getParameter("email");
            boolean sent = videoService.shareVideo(videoId, account.getUsername(), email);
            String message = sent ? "shared" : "share-failed";
            resp.sendRedirect(req.getContextPath() + "/video/detail?id=" + videoId + "&message=" + message);
        }
    }
}