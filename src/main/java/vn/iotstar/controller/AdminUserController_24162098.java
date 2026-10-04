package vn.iotstar.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import vn.iotstar.entity.User_24162098;
import vn.iotstar.service.IUserService_24162098;
import vn.iotstar.service.UserServiceImpl_24162098;

@WebServlet(urlPatterns = {"/admin/users", "/admin/user/form", "/admin/user/save", "/admin/user/delete"})
public class AdminUserController_24162098 extends HttpServlet {
    private IUserService_24162098 userService = new UserServiceImpl_24162098();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String path = req.getServletPath();
        if (path.equals("/admin/user/form")) {
            String username = req.getParameter("username");
            if (username != null && !username.isEmpty()) {
                req.setAttribute("user", userService.findByUsername(username));
            }
            req.getRequestDispatcher("/views/admin/user-form.jsp").forward(req, resp);
            return;
        }

        if (path.equals("/admin/user/delete")) {
            String username = req.getParameter("username");
            userService.delete(username);
            resp.sendRedirect(req.getContextPath() + "/admin/users");
            return;
        }

        int page = 1;
        int pageSize = 6; // Đề bài yêu cầu 6 user/trang
        if (req.getParameter("page") != null) {
            page = Integer.parseInt(req.getParameter("page"));
        }

        List<User_24162098> listUsers = userService.findAllPaging(page, pageSize);
        int totalUsers = userService.countAll();
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

        req.setAttribute("users", listUsers);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.getRequestDispatcher("/views/admin/user-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.setCharacterEncoding("UTF-8");
        User_24162098 user = new User_24162098(
                req.getParameter("username"),
                req.getParameter("password"),
                req.getParameter("phone"),
                req.getParameter("fullname"),
                req.getParameter("email"),
                "on".equals(req.getParameter("admin")),
                "on".equals(req.getParameter("active")),
                req.getParameter("images"));

        if (userService.findByUsername(user.getUsername()) == null) {
            userService.register(user);
        } else {
            userService.update(user);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }

    private boolean isAdmin(HttpServletRequest req) {
        Object account = req.getSession().getAttribute("account");
        return account instanceof User_24162098 && ((User_24162098) account).isAdmin();
    }
}