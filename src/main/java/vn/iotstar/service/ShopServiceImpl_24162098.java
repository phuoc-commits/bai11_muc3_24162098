package vn.iotstar.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import vn.iotstar.dao.IShopDao_24162098;
import vn.iotstar.dao.ShopDaoImpl_24162098;
import vn.iotstar.entity.CartLine_24162098;
import vn.iotstar.entity.Product_24162098;
import vn.iotstar.entity.OrderInvoice_24162098;
import vn.iotstar.entity.OrderStatus_24162098;

public class ShopServiceImpl_24162098 implements IShopService_24162098 {
    private final IShopDao_24162098 shopDao = new ShopDaoImpl_24162098();

    @Override
    public List<Product_24162098> findProducts() {
        return shopDao.findActiveProducts();
    }

    @Override
    public Product_24162098 findProduct(int productId) {
        return shopDao.findActiveProductById(productId);
    }

    @Override
    public List<CartLine_24162098> buildCart(Map<Integer, Integer> quantities) {
        List<CartLine_24162098> lines = new ArrayList<>();
        if (quantities == null) return lines;
        for (Map.Entry<Integer, Integer> entry : quantities.entrySet()) {
            Product_24162098 product = shopDao.findActiveProductById(entry.getKey());
            Integer quantity = entry.getValue();
            if (product != null && quantity != null && quantity > 0) {
                lines.add(new CartLine_24162098(product, quantity));
            }
        }
        return lines;
    }

    @Override
    public long checkoutCod(String username, Map<Integer, Integer> quantities,
            String recipientName, String phone, String address) throws SQLException {
        if (username == null || username.isBlank()) throw new SQLException("Please sign in");
        if (quantities == null || quantities.isEmpty()) throw new SQLException("Cart is empty");
        if (recipientName == null || recipientName.isBlank()
                || phone == null || phone.isBlank()
                || address == null || address.isBlank()) {
            throw new SQLException("Recipient name, phone and address are required");
        }
        return shopDao.placeCodOrder(username, quantities, recipientName.trim(), phone.trim(), address.trim());
    }

    @Override
    public OrderInvoice_24162098 findInvoice(long orderId, String username, boolean admin) {
        return shopDao.findInvoice(orderId, username, admin);
    }

    @Override
    public List<OrderInvoice_24162098> findOrderHistory(String username, boolean admin, OrderStatus_24162098 status) {
        return shopDao.findOrderHistory(username, admin, status);
    }
}
