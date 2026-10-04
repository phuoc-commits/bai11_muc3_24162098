package vn.iotstar.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import vn.iotstar.connection.DBConnect_24162098;
import vn.iotstar.entity.Product_24162098;
import vn.iotstar.entity.OrderInvoice_24162098;
import vn.iotstar.entity.OrderItem_24162098;
import vn.iotstar.entity.OrderStatus_24162098;

public class ShopDaoImpl_24162098 implements IShopDao_24162098 {
    @Override
    public List<Product_24162098> findActiveProducts() {
        List<Product_24162098> products = new ArrayList<>();
        String sql = "SELECT p.ProductId, p.Name, p.Description, p.Price, p.Stock, p.Image, p.ProductCategoryId, c.Name AS ProductCategoryName FROM Products p JOIN ProductCategories c ON p.ProductCategoryId = c.ProductCategoryId WHERE p.Active = 1 AND c.Active = 1 ORDER BY p.ProductId";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) products.add(mapProduct(rs));
        } catch (Exception e) { e.printStackTrace(); }
        return products;
    }

    @Override
    public Product_24162098 findActiveProductById(int productId) {
        String sql = "SELECT p.ProductId, p.Name, p.Description, p.Price, p.Stock, p.Image, p.ProductCategoryId, c.Name AS ProductCategoryName FROM Products p JOIN ProductCategories c ON p.ProductCategoryId = c.ProductCategoryId WHERE p.ProductId = ? AND p.Active = 1 AND c.Active = 1";
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
    public long placeCodOrder(String username, Map<Integer, Integer> quantities,
            String recipientName, String phone, String address) throws SQLException {
        if (quantities == null || quantities.isEmpty()) throw new SQLException("Cart is empty");
        Connection conn = DBConnect_24162098.getConnection();
        if (conn == null) throw new SQLException("Cannot connect to database");
        try (conn) {
            conn.setAutoCommit(false);
            try {
                List<Product_24162098> lockedProducts = new ArrayList<>();
                BigDecimal total = BigDecimal.ZERO;
                String lockSql = "SELECT p.ProductId, p.Name, p.Price, p.Stock, p.ProductCategoryId, c.Name AS ProductCategoryName FROM Products p JOIN ProductCategories c ON p.ProductCategoryId = c.ProductCategoryId WHERE p.ProductId = ? AND p.Active = 1 AND c.Active = 1 FOR UPDATE";
                try (PreparedStatement lock = conn.prepareStatement(lockSql)) {
                    for (Map.Entry<Integer, Integer> entry : new TreeMap<>(quantities).entrySet()) {
                        int quantity = entry.getValue() == null ? 0 : entry.getValue();
                        if (quantity < 1) throw new SQLException("Invalid quantity");
                        lock.setInt(1, entry.getKey());
                        try (ResultSet rs = lock.executeQuery()) {
                            if (!rs.next()) throw new SQLException("Product is unavailable");
                            int stock = rs.getInt("Stock");
                            if (quantity > stock) throw new SQLException("Insufficient stock for " + rs.getString("Name"));
                            Product_24162098 product = new Product_24162098();
                            product.setProductId(rs.getInt("ProductId"));
                            product.setName(rs.getString("Name"));
                            product.setPrice(rs.getBigDecimal("Price"));
                            product.setStock(stock);
                            product.setDescription("");
                            product.setProductCategoryId(rs.getInt("ProductCategoryId"));
                            product.setProductCategoryName(rs.getString("ProductCategoryName"));
                            lockedProducts.add(product);
                            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
                        }
                    }
                }

                long orderId;
                String orderSql = "INSERT INTO CustomerOrders(Username, TotalAmount, PaymentMethod, Status, RecipientName, Phone, ShippingAddress) VALUES(?, ?, 'COD', 'NEW', ?, ?, ?)";
                try (PreparedStatement order = conn.prepareStatement(orderSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    order.setString(1, username);
                    order.setBigDecimal(2, total);
                    order.setString(3, recipientName);
                    order.setString(4, phone);
                    order.setString(5, address);
                    order.executeUpdate();
                    try (ResultSet keys = order.getGeneratedKeys()) {
                        if (!keys.next()) throw new SQLException("Order ID was not generated");
                        orderId = keys.getLong(1);
                    }
                }

                String itemSql = "INSERT INTO CustomerOrderItems(OrderId, ProductId, ProductName, UnitPrice, Quantity) VALUES(?, ?, ?, ?, ?)";
                String stockSql = "UPDATE Products SET Stock = Stock - ? WHERE ProductId = ? AND Stock >= ?";
                try (PreparedStatement item = conn.prepareStatement(itemSql);
                     PreparedStatement stock = conn.prepareStatement(stockSql)) {
                    for (Product_24162098 product : lockedProducts) {
                        int quantity = quantities.get(product.getProductId());
                        item.setLong(1, orderId);
                        item.setInt(2, product.getProductId());
                        item.setString(3, product.getName());
                        item.setBigDecimal(4, product.getPrice());
                        item.setInt(5, quantity);
                        item.executeUpdate();

                        stock.setInt(1, quantity);
                        stock.setInt(2, product.getProductId());
                        stock.setInt(3, quantity);
                        if (stock.executeUpdate() != 1) throw new SQLException("Stock changed during checkout");
                    }
                }
                conn.commit();
                return orderId;
            } catch (SQLException | RuntimeException e) {
                conn.rollback();
                if (e instanceof SQLException) throw (SQLException) e;
                throw new SQLException("Could not complete COD order", e);
            }
        }
    }

    @Override
    public OrderInvoice_24162098 findInvoice(long orderId, String username, boolean admin) {
        String sql = "SELECT OrderId, Username, OrderDate, TotalAmount, PaymentMethod, Status, RecipientName, Phone, ShippingAddress "
                + "FROM CustomerOrders WHERE OrderId = ? AND (? = 1 OR Username = ?)";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, orderId);
            ps.setBoolean(2, admin);
            ps.setString(3, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                OrderInvoice_24162098 invoice = new OrderInvoice_24162098();
                invoice.setOrderId(rs.getLong("OrderId"));
                invoice.setUsername(rs.getString("Username"));
                invoice.setOrderDate(rs.getTimestamp("OrderDate"));
                invoice.setTotalAmount(rs.getBigDecimal("TotalAmount"));
                invoice.setPaymentMethod(rs.getString("PaymentMethod"));
                invoice.setStatus(rs.getString("Status"));
                invoice.setRecipientName(rs.getString("RecipientName"));
                invoice.setPhone(rs.getString("Phone"));
                invoice.setShippingAddress(rs.getString("ShippingAddress"));
                loadInvoiceItems(conn, invoice);
                return invoice;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<OrderInvoice_24162098> findOrderHistory(String username, boolean admin, OrderStatus_24162098 status) {
        List<OrderInvoice_24162098> orders = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT OrderId, Username, OrderDate, TotalAmount, PaymentMethod, Status, RecipientName, Phone, ShippingAddress FROM CustomerOrders WHERE 1 = 1");
        if (!admin) sql.append(" AND Username = ?");
        if (status != null) sql.append(" AND Status = ?");
        sql.append(" ORDER BY OrderDate DESC, OrderId DESC");
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int parameter = 1;
            if (!admin) ps.setString(parameter++, username);
            if (status != null) ps.setString(parameter, status.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderInvoice_24162098 order = new OrderInvoice_24162098();
                    order.setOrderId(rs.getLong("OrderId"));
                    order.setUsername(rs.getString("Username"));
                    order.setOrderDate(rs.getTimestamp("OrderDate"));
                    order.setTotalAmount(rs.getBigDecimal("TotalAmount"));
                    order.setPaymentMethod(rs.getString("PaymentMethod"));
                    order.setStatus(rs.getString("Status"));
                    order.setRecipientName(rs.getString("RecipientName"));
                    order.setPhone(rs.getString("Phone"));
                    order.setShippingAddress(rs.getString("ShippingAddress"));
                    orders.add(order);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return orders;
    }

    private void loadInvoiceItems(Connection conn, OrderInvoice_24162098 invoice) throws SQLException {
        String sql = "SELECT ProductName, UnitPrice, Quantity FROM CustomerOrderItems WHERE OrderId = ? ORDER BY OrderItemId";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, invoice.getOrderId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    OrderItem_24162098 item = new OrderItem_24162098();
                    item.setProductName(rs.getString("ProductName"));
                    item.setUnitPrice(rs.getBigDecimal("UnitPrice"));
                    item.setQuantity(rs.getInt("Quantity"));
                    invoice.getItems().add(item);
                }
            }
        }
    }

    private Product_24162098 mapProduct(ResultSet rs) throws SQLException {
        Product_24162098 product = new Product_24162098();
        product.setProductId(rs.getInt("ProductId"));
        product.setName(rs.getString("Name"));
        product.setDescription(rs.getString("Description"));
        product.setPrice(rs.getBigDecimal("Price"));
        product.setStock(rs.getInt("Stock"));
        product.setImage(rs.getString("Image"));
        product.setProductCategoryId(rs.getInt("ProductCategoryId"));
        product.setProductCategoryName(rs.getString("ProductCategoryName"));
        return product;
    }
}
