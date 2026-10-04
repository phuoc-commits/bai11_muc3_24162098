package vn.iotstar.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import vn.iotstar.connection.DBConnect_24162098;
import vn.iotstar.entity.Category_24162098;
import vn.iotstar.entity.Product_24162098;
import vn.iotstar.entity.ProductCategory_24162098;

public class CatalogDaoImpl_24162098 implements ICatalogDao_24162098 {
    @Override
    public List<Category_24162098> findAllCategories() {
        List<Category_24162098> categories = new ArrayList<>();
        String sql = "SELECT CategoryId, Categoryname, Categorycode, Images, Status FROM Category ORDER BY CategoryId DESC";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) categories.add(mapCategory(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return categories;
    }

    @Override
    public List<Category_24162098> findActiveCategories() {
        List<Category_24162098> categories = new ArrayList<>();
        String sql = "SELECT CategoryId, Categoryname, Categorycode, Images, Status FROM Category WHERE Status = 1 ORDER BY CategoryId";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) categories.add(mapCategory(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return categories;
    }

    @Override
    public Category_24162098 findCategoryById(int categoryId) {
        String sql = "SELECT CategoryId, Categoryname, Categorycode, Images, Status FROM Category WHERE CategoryId = ?";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapCategory(rs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public void saveCategory(Category_24162098 category) {
        boolean update = category.getCategoryId() > 0;
        String sql = update
                ? "UPDATE Category SET Categoryname = ?, Categorycode = ?, Images = ?, Status = ? WHERE CategoryId = ?"
                : "INSERT INTO Category(Categoryname, Categorycode, Images, Status) VALUES(?, ?, ?, ?)";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getCategoryname());
            ps.setString(2, category.getCategorycode());
            ps.setString(3, category.getImages());
            ps.setBoolean(4, category.isStatus());
            if (update) ps.setInt(5, category.getCategoryId());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void setCategoryStatus(int categoryId, boolean active) {
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE Category SET Status = ? WHERE CategoryId = ?")) {
            ps.setBoolean(1, active);
            ps.setInt(2, categoryId);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public boolean deleteCategory(int categoryId) {
        String sql = "DELETE FROM Category WHERE CategoryId = ? AND NOT EXISTS (SELECT 1 FROM Videos WHERE CategoryId = ?)";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ps.setInt(2, categoryId);
            return ps.executeUpdate() == 1;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public List<ProductCategory_24162098> findAllProductCategories() {
        List<ProductCategory_24162098> categories = new ArrayList<>();
        String sql = "SELECT ProductCategoryId, Name, Code, Description, Active FROM ProductCategories ORDER BY ProductCategoryId DESC";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) categories.add(mapProductCategory(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return categories;
    }

    @Override
    public List<ProductCategory_24162098> findActiveProductCategories() {
        List<ProductCategory_24162098> categories = new ArrayList<>();
        String sql = "SELECT ProductCategoryId, Name, Code, Description, Active FROM ProductCategories WHERE Active = 1 ORDER BY Name";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) categories.add(mapProductCategory(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return categories;
    }

    @Override
    public ProductCategory_24162098 findProductCategoryById(int categoryId) {
        String sql = "SELECT ProductCategoryId, Name, Code, Description, Active FROM ProductCategories WHERE ProductCategoryId = ?";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapProductCategory(rs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public void saveProductCategory(ProductCategory_24162098 category) {
        boolean update = category.getProductCategoryId() > 0;
        String sql = update
                ? "UPDATE ProductCategories SET Name = ?, Code = ?, Description = ?, Active = ? WHERE ProductCategoryId = ?"
                : "INSERT INTO ProductCategories(Name, Code, Description, Active) VALUES(?, ?, ?, ?)";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getCode());
            ps.setString(3, category.getDescription());
            ps.setBoolean(4, category.isActive());
            if (update) ps.setInt(5, category.getProductCategoryId());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void setProductCategoryStatus(int categoryId, boolean active) {
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE ProductCategories SET Active = ? WHERE ProductCategoryId = ?")) {
            ps.setBoolean(1, active);
            ps.setInt(2, categoryId);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public boolean deleteProductCategory(int categoryId) {
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM ProductCategories WHERE ProductCategoryId = ?")) {
            ps.setInt(1, categoryId);
            return ps.executeUpdate() == 1;
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public List<Product_24162098> findAllProducts() {
        List<Product_24162098> products = new ArrayList<>();
        String sql = "SELECT p.ProductId, p.Name, p.Description, p.Price, p.Stock, p.Image, p.Active, p.ProductCategoryId, c.Name AS ProductCategoryName FROM Products p JOIN ProductCategories c ON p.ProductCategoryId = c.ProductCategoryId ORDER BY p.ProductId DESC";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) products.add(mapProduct(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return products;
    }

    @Override
    public Product_24162098 findProductById(int productId) {
        String sql = "SELECT p.ProductId, p.Name, p.Description, p.Price, p.Stock, p.Image, p.Active, p.ProductCategoryId, c.Name AS ProductCategoryName FROM Products p JOIN ProductCategories c ON p.ProductCategoryId = c.ProductCategoryId WHERE p.ProductId = ?";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapProduct(rs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public void saveProduct(Product_24162098 product) {
        boolean update = product.getProductId() > 0;
        String sql = update
                ? "UPDATE Products SET Name = ?, Description = ?, Price = ?, Stock = ?, Image = ?, Active = ?, ProductCategoryId = ? WHERE ProductId = ?"
                : "INSERT INTO Products(Name, Description, Price, Stock, Image, Active, ProductCategoryId) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setBigDecimal(3, product.getPrice());
            ps.setInt(4, product.getStock());
            ps.setString(5, product.getImage());
            ps.setBoolean(6, product.isActive());
            ps.setInt(7, product.getProductCategoryId());
            if (update) ps.setInt(8, product.getProductId());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void setProductStatus(int productId, boolean active) {
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE Products SET Active = ? WHERE ProductId = ?")) {
            ps.setBoolean(1, active);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public boolean deleteProduct(int productId) {
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM Products WHERE ProductId = ?")) {
            ps.setInt(1, productId);
            return ps.executeUpdate() == 1;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    private Category_24162098 mapCategory(ResultSet rs) throws Exception {
        return new Category_24162098(rs.getInt("CategoryId"), rs.getString("Categoryname"),
                rs.getString("Categorycode"), rs.getString("Images"), rs.getBoolean("Status"));
    }

    private Product_24162098 mapProduct(ResultSet rs) throws Exception {
        Product_24162098 product = new Product_24162098();
        product.setProductId(rs.getInt("ProductId"));
        product.setName(rs.getString("Name"));
        product.setDescription(rs.getString("Description"));
        product.setPrice(rs.getBigDecimal("Price"));
        product.setStock(rs.getInt("Stock"));
        product.setImage(rs.getString("Image"));
        product.setActive(rs.getBoolean("Active"));
        product.setProductCategoryId(rs.getInt("ProductCategoryId"));
        product.setProductCategoryName(rs.getString("ProductCategoryName"));
        return product;
    }

    private ProductCategory_24162098 mapProductCategory(ResultSet rs) throws Exception {
        ProductCategory_24162098 category = new ProductCategory_24162098();
        category.setProductCategoryId(rs.getInt("ProductCategoryId"));
        category.setName(rs.getString("Name"));
        category.setCode(rs.getString("Code"));
        category.setDescription(rs.getString("Description"));
        category.setActive(rs.getBoolean("Active"));
        return category;
    }
}
