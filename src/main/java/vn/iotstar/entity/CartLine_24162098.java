package vn.iotstar.entity;

import java.math.BigDecimal;

public class CartLine_24162098 {
    private final Product_24162098 product;
    private final int quantity;

    public CartLine_24162098(Product_24162098 product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product_24162098 getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public BigDecimal getSubtotal() { return product.getPrice().multiply(BigDecimal.valueOf(quantity)); }
}
