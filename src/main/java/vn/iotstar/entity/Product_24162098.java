package vn.iotstar.entity;

import java.math.BigDecimal;

public class Product_24162098 {
    private int productId;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
    private String image;
    private boolean active;
    private int productCategoryId;
    private String productCategoryName;

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public int getProductCategoryId() { return productCategoryId; }
    public void setProductCategoryId(int productCategoryId) { this.productCategoryId = productCategoryId; }
    public String getProductCategoryName() { return productCategoryName; }
    public void setProductCategoryName(String productCategoryName) { this.productCategoryName = productCategoryName; }
}
