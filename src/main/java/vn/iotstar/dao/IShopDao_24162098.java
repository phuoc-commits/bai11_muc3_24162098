package vn.iotstar.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import vn.iotstar.entity.OrderInvoice_24162098;
import vn.iotstar.entity.Product_24162098;
import vn.iotstar.entity.OrderStatus_24162098;

public interface IShopDao_24162098 {
    List<Product_24162098> findActiveProducts();
    Product_24162098 findActiveProductById(int productId);
    long placeCodOrder(String username, Map<Integer, Integer> quantities,
            String recipientName, String phone, String address) throws SQLException;
    OrderInvoice_24162098 findInvoice(long orderId, String username, boolean admin);
    List<OrderInvoice_24162098> findOrderHistory(String username, boolean admin, OrderStatus_24162098 status);
}
