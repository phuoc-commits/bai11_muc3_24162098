package vn.iotstar.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import vn.iotstar.entity.User_24162098;
import vn.iotstar.service.IUserService_24162098;
import vn.iotstar.service.UserServiceImpl_24162098;

@WebServlet(urlPatterns = {"/login", "/logout", "/register"})
public class LoginController_24162098 extends HttpServlet {
    private static final long OTP_VALIDITY_MILLIS = 5 * 60 * 1000L;
    private IUserService_24162098 userService = new UserServiceImpl_24162098();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if (path.equals("/register")) {
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }
        if (path.equals("/logout")) {
            HttpSession session = req.getSession();
            session.invalidate();
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        if (req.getServletPath().equals("/register")) {
            handleRegister(req, resp);
            return;
        }

        String u = req.getParameter("username");
        String p = req.getParameter("password");

        User_24162098 user = userService.login(u, p);
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("account", user);
            if (user.isAdmin()) {
                resp.sendRedirect(req.getContextPath() + "/admin/users");
            } else {
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        } else {
            req.setAttribute("msg", "Tên đăng nhập hoặc mật khẩu không đúng!");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }

    private void handleRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String otpInput = req.getParameter("otp");
        if (otpInput == null || otpInput.isEmpty()) {
            User_24162098 pendingUser = new User_24162098(
                    req.getParameter("username"), req.getParameter("password"),
                    req.getParameter("phone"), req.getParameter("fullname"),
                    req.getParameter("email"), false, true, req.getParameter("images"));
            String otp = String.format("%06d", new java.security.SecureRandom().nextInt(1000000));
            if (userService.findByUsername(pendingUser.getUsername()) != null) {
                req.setAttribute("msg", "Username da ton tai.");
            } else if (userService.sendMailOTP(pendingUser.getEmail(), otp)) {
                req.getSession().setAttribute("pendingUser", pendingUser);
                req.getSession().setAttribute("registerOtp", otp);
                req.getSession().setAttribute("registerOtpCreatedAt", System.currentTimeMillis());
                req.setAttribute("verifyStep", true);
                req.setAttribute("msg", "OTP da duoc gui toi email cua ban.");
            } else {
                req.setAttribute("msg", "Khong gui duoc email OTP. Hay kiem tra cau hinh Gmail.");
            }
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }

        String expectedOtp = (String) req.getSession().getAttribute("registerOtp");
        User_24162098 pendingUser = (User_24162098) req.getSession().getAttribute("pendingUser");
        Object createdAtValue = req.getSession().getAttribute("registerOtpCreatedAt");
        long createdAt = createdAtValue instanceof Long ? (Long) createdAtValue : 0L;
        boolean otpValid = createdAt > 0 && System.currentTimeMillis() - createdAt <= OTP_VALIDITY_MILLIS;
        if (pendingUser != null && otpValid && otpInput.equals(expectedOtp)) {
            userService.register(pendingUser);
            req.getSession().removeAttribute("pendingUser");
            req.getSession().removeAttribute("registerOtp");
            req.getSession().removeAttribute("registerOtpCreatedAt");
            resp.sendRedirect(req.getContextPath() + "/login?registered=1");
        } else {
            req.setAttribute("verifyStep", true);
            req.setAttribute("msg", otpValid ? "OTP khong dung." : "OTP da het han. Hay gui lai ma moi.");
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
        }
    }
}