package vn.iotstar.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vn.iotstar.entity.CartLine_24162098;
import vn.iotstar.entity.Product_24162098;
import vn.iotstar.entity.OrderInvoice_24162098;
import vn.iotstar.entity.OrderStatus_24162098;
import vn.iotstar.entity.User_24162098;
import vn.iotstar.service.IShopService_24162098;
import vn.iotstar.service.ShopServiceImpl_24162098;

@WebServlet(urlPatterns = {"/shop/products", "/cart", "/cart/add", "/cart/update", "/cart/remove", "/checkout", "/order/success", "/order/history"})
public class ShopController_24162098 extends HttpServlet {
    private final IShopService_24162098 shopService = new ShopServiceImpl_24162098();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();
        if (path.equals("/shop/products")) {
            req.setAttribute("products", shopService.findProducts());
            req.setAttribute("cartCount", getCart(req.getSession()).values().stream().mapToInt(Integer::intValue).sum());
            req.setAttribute("error", req.getParameter("error"));
            req.getRequestDispatcher("/views/shop/products.jsp").forward(req, resp);
            return;
        }
        if (path.equals("/cart")) {
            renderCart(req, resp);
            return;
        }
        if (path.equals("/checkout")) {
            if (currentUser(req) == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
            List<CartLine_24162098> lines = shopService.buildCart(getCart(req.getSession()));
            if (lines.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/cart?error=empty");
                return;
            }
            req.setAttribute("lines", lines);
            req.setAttribute("total", total(lines));
            req.setAttribute("account", currentUser(req));
            req.setAttribute("error", req.getParameter("error"));
            req.getRequestDispatcher("/views/shop/checkout.jsp").forward(req, resp);
            return;
        }
        if (path.equals("/order/success")) {
            User_24162098 user = currentUser(req);
            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
            long orderId = parseLong(req.getParameter("id"));
            OrderInvoice_24162098 invoice = shopService.findInvoice(orderId, user.getUsername(), user.isAdmin());
            if (invoice == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            req.setAttribute("invoice", invoice);
            req.getRequestDispatcher("/views/shop/order-success.jsp").forward(req, resp);
            return;
        }
        if (path.equals("/order/history")) {
            User_24162098 user = currentUser(req);
            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
            OrderStatus_24162098 status = OrderStatus_24162098.fromCode(req.getParameter("status"));
            req.setAttribute("orders", shopService.findOrderHistory(user.getUsername(), user.isAdmin(), status));
            req.setAttribute("selectedStatus", status == null ? "ALL" : status.name());
            req.getRequestDispatcher("/views/shop/order-history.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();
        Map<Integer, Integer> cart = getCart(req.getSession());

        if (path.equals("/cart/add")) {
            int productId = parseInt(req.getParameter("productId"));
            int quantity = parseInt(req.getParameter("quantity"));
            Product_24162098 product = shopService.findProduct(productId);
            int nextQuantity = cart.getOrDefault(productId, 0) + quantity;
            if (product == null || quantity < 1 || nextQuantity > product.getStock()) {
                resp.sendRedirect(req.getContextPath() + "/shop/products?error=stock");
                return;
            }
            cart.put(productId, nextQuantity);
            resp.sendRedirect(req.getContextPath() + "/cart?added=1");
            return;
        }

        if (path.equals("/cart/update")) {
            int productId = parseInt(req.getParameter("productId"));
            int quantity = parseInt(req.getParameter("quantity"));
            Product_24162098 product = shopService.findProduct(productId);
            if (product == null || quantity < 1 || quantity > product.getStock()) {
                resp.sendRedirect(req.getContextPath() + "/cart?error=stock");
                return;
            }
            cart.put(productId, quantity);
            resp.sendRedirect(req.getContextPath() + "/cart?updated=1");
            return;
        }

        if (path.equals("/cart/remove")) {
            cart.remove(parseInt(req.getParameter("productId")));
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        if (path.equals("/checkout")) {
            User_24162098 user = currentUser(req);
            if (user == null) {
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }
            try {
                long orderId = shopService.checkoutCod(user.getUsername(), new LinkedHashMap<>(cart),
                        req.getParameter("recipientName"), req.getParameter("phone"), req.getParameter("address"));
                req.getSession().removeAttribute("shoppingCart");
                resp.sendRedirect(req.getContextPath() + "/order/success?id=" + orderId);
            } catch (SQLException e) {
                resp.sendRedirect(req.getContextPath() + "/checkout?error="
                        + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8));
            }
        }
    }

    private void renderCart(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CartLine_24162098> lines = shopService.buildCart(getCart(req.getSession()));
        req.setAttribute("lines", lines);
        req.setAttribute("total", total(lines));
        req.setAttribute("error", req.getParameter("error"));
        req.setAttribute("added", req.getParameter("added"));
        req.setAttribute("updated", req.getParameter("updated"));
        req.getRequestDispatcher("/views/shop/cart.jsp").forward(req, resp);
    }

    @SuppressWarnings("unchecked")
    private Map<Integer, Integer> getCart(HttpSession session) {
        Object value = session.getAttribute("shoppingCart");
        if (value instanceof Map<?, ?>) return (Map<Integer, Integer>) value;
        Map<Integer, Integer> cart = new LinkedHashMap<>();
        session.setAttribute("shoppingCart", cart);
        return cart;
    }

    private User_24162098 currentUser(HttpServletRequest req) {
        Object account = req.getSession().getAttribute("account");
        return account instanceof User_24162098 ? (User_24162098) account : null;
    }

    private int parseInt(String value) {
        try { return Integer.parseInt(value); }
        catch (NumberFormatException e) { return -1; }
    }

    private long parseLong(String value) {
        try { return Long.parseLong(value); }
        catch (NumberFormatException e) { return -1L; }
    }

    private BigDecimal total(List<CartLine_24162098> lines) {
        return lines.stream().map(CartLine_24162098::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
