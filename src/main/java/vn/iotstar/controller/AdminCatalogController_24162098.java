package vn.iotstar.controller;

import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import vn.iotstar.entity.Category_24162098;
import vn.iotstar.entity.Product_24162098;
import vn.iotstar.entity.ProductCategory_24162098;
import vn.iotstar.entity.User_24162098;
import vn.iotstar.service.CatalogServiceImpl_24162098;
import vn.iotstar.service.ICatalogService_24162098;

@WebServlet(urlPatterns = {
    "/admin/categories", "/admin/category/form", "/admin/category/save", "/admin/category/status",
    "/admin/category/delete", "/admin/products", "/admin/product/form", "/admin/product/save", "/admin/product/status", "/admin/product/delete",
    "/admin/product-categories", "/admin/product-category/form", "/admin/product-category/save", "/admin/product-category/delete", "/admin/product-category/status"
})
public class AdminCatalogController_24162098 extends HttpServlet {
    private final ICatalogService_24162098 catalogService = new CatalogServiceImpl_24162098();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String path = req.getServletPath();
        req.setAttribute("error", req.getParameter("error"));
        if (path.equals("/admin/categories")) {
            req.setAttribute("categories", catalogService.findAllCategories());
            req.getRequestDispatcher("/views/admin/category-list.jsp").forward(req, resp);
            return;
        }
        if (path.equals("/admin/category/form")) {
            int id = parseInt(req.getParameter("id"));
            if (id > 0) req.setAttribute("category", catalogService.findCategory(id));
            req.getRequestDispatcher("/views/admin/category-form.jsp").forward(req, resp);
            return;
        }
        if (path.equals("/admin/products")) {
            req.setAttribute("products", catalogService.findAllProducts());
            req.getRequestDispatcher("/views/admin/product-list.jsp").forward(req, resp);
            return;
        }
        if (path.equals("/admin/product-categories")) {
            req.setAttribute("productCategories", catalogService.findAllProductCategories());
            req.getRequestDispatcher("/views/admin/product-category-list.jsp").forward(req, resp);
            return;
        }
        if (path.equals("/admin/product-category/form")) {
            int id = parseInt(req.getParameter("id"));
            if (id > 0) req.setAttribute("productCategory", catalogService.findProductCategory(id));
            req.getRequestDispatcher("/views/admin/product-category-form.jsp").forward(req, resp);
            return;
        }
        if (path.equals("/admin/product/form")) {
            int id = parseInt(req.getParameter("id"));
            if (id > 0) req.setAttribute("product", catalogService.findProduct(id));
            req.setAttribute("productCategories", catalogService.findAllProductCategories());
            req.getRequestDispatcher("/views/admin/product-form.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!isAdmin(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        req.setCharacterEncoding("UTF-8");
        String path = req.getServletPath();
        if (path.equals("/admin/category/save")) {
            String name = trim(req.getParameter("categoryname"));
            String code = trim(req.getParameter("categorycode"));
            if (name.isEmpty() || code.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/admin/category/form?error=required");
                return;
            }
            Category_24162098 category = new Category_24162098(parseInt(req.getParameter("categoryId")),
                    name, code, trim(req.getParameter("images")), "on".equals(req.getParameter("status")));
            catalogService.saveCategory(category);
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
            return;
        }
        if (path.equals("/admin/category/status")) {
            catalogService.setCategoryStatus(parseInt(req.getParameter("categoryId")), "true".equals(req.getParameter("active")));
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
            return;
        }
        if (path.equals("/admin/category/delete")) {
            boolean deleted = catalogService.deleteCategory(parseInt(req.getParameter("categoryId")));
            resp.sendRedirect(req.getContextPath() + "/admin/categories?error=" + (deleted ? "" : "category-in-use"));
            return;
        }
        if (path.equals("/admin/product-category/save")) {
            String name = trim(req.getParameter("name"));
            String code = trim(req.getParameter("code"));
            if (name.isEmpty() || code.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/admin/product-category/form?error=required");
                return;
            }
            ProductCategory_24162098 category = new ProductCategory_24162098();
            category.setProductCategoryId(parseInt(req.getParameter("productCategoryId")));
            category.setName(name);
            category.setCode(code);
            category.setDescription(trim(req.getParameter("description")));
            category.setActive("on".equals(req.getParameter("active")));
            catalogService.saveProductCategory(category);
            resp.sendRedirect(req.getContextPath() + "/admin/product-categories");
            return;
        }
        if (path.equals("/admin/product-category/delete")) {
            boolean deleted = catalogService.deleteProductCategory(parseInt(req.getParameter("productCategoryId")));
            resp.sendRedirect(req.getContextPath() + "/admin/product-categories?error=" + (deleted ? "" : "category-in-use"));
            return;
        }
        if (path.equals("/admin/product-category/status")) {
            catalogService.setProductCategoryStatus(parseInt(req.getParameter("productCategoryId")), "true".equals(req.getParameter("active")));
            resp.sendRedirect(req.getContextPath() + "/admin/product-categories");
            return;
        }
        if (path.equals("/admin/product/save")) {
            String name = trim(req.getParameter("name"));
            BigDecimal price;
            int stock = parseInt(req.getParameter("stock"));
            int productCategoryId = parseInt(req.getParameter("productCategoryId"));
            ProductCategory_24162098 productCategory = catalogService.findProductCategory(productCategoryId);
            try { price = new BigDecimal(req.getParameter("price")); }
            catch (Exception e) { price = null; }
            if (name.isEmpty() || price == null || price.signum() < 0 || stock < 0
                    || productCategory == null || !productCategory.isActive()) {
                resp.sendRedirect(req.getContextPath() + "/admin/product/form?error=invalid");
                return;
            }
            Product_24162098 product = new Product_24162098();
            product.setProductId(parseInt(req.getParameter("productId")));
            product.setName(name);
            product.setDescription(trim(req.getParameter("description")));
            product.setPrice(price);
            product.setStock(stock);
            product.setImage(trim(req.getParameter("image")));
            product.setActive("on".equals(req.getParameter("active")));
            product.setProductCategoryId(productCategoryId);
            catalogService.saveProduct(product);
            resp.sendRedirect(req.getContextPath() + "/admin/products");
            return;
        }
        if (path.equals("/admin/product/status")) {
            catalogService.setProductStatus(parseInt(req.getParameter("productId")), "true".equals(req.getParameter("active")));
            resp.sendRedirect(req.getContextPath() + "/admin/products");
            return;
        }
        if (path.equals("/admin/product/delete")) {
            catalogService.deleteProduct(parseInt(req.getParameter("productId")));
            resp.sendRedirect(req.getContextPath() + "/admin/products");
        }
    }

    private boolean isAdmin(HttpServletRequest req) {
        Object account = req.getSession().getAttribute("account");
        return account instanceof User_24162098 && ((User_24162098) account).isAdmin();
    }

    private int parseInt(String value) {
        try { return Integer.parseInt(value); }
        catch (Exception e) { return 0; }
    }

    private String trim(String value) { return value == null ? "" : value.trim(); }
}
