package vn.iotstar.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import vn.iotstar.service.CatalogServiceImpl_24162098;
import vn.iotstar.service.ICatalogService_24162098;

@WebServlet(urlPatterns = {"/home", "/videos"})
public class HomeController_24162098 extends HttpServlet {
    private final ICatalogService_24162098 catalogService = new CatalogServiceImpl_24162098();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getServletPath().equals("/videos")) {
            req.setAttribute("categories", catalogService.findActiveCategories());
            req.getRequestDispatcher("/views/videos.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/views/home.jsp").forward(req, resp);
        }
    }
}
