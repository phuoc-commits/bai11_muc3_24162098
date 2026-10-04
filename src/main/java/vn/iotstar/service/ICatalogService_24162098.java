package vn.iotstar.service;

import java.util.List;
import vn.iotstar.entity.Category_24162098;
import vn.iotstar.entity.Product_24162098;
import vn.iotstar.entity.ProductCategory_24162098;

public interface ICatalogService_24162098 {
    List<Category_24162098> findAllCategories();
    List<Category_24162098> findActiveCategories();
    Category_24162098 findCategory(int categoryId);
    void saveCategory(Category_24162098 category);
    void setCategoryStatus(int categoryId, boolean active);
    boolean deleteCategory(int categoryId);
    List<ProductCategory_24162098> findAllProductCategories();
    List<ProductCategory_24162098> findActiveProductCategories();
    ProductCategory_24162098 findProductCategory(int categoryId);
    void saveProductCategory(ProductCategory_24162098 category);
    void setProductCategoryStatus(int categoryId, boolean active);
    boolean deleteProductCategory(int categoryId);
    List<Product_24162098> findAllProducts();
    Product_24162098 findProduct(int productId);
    void saveProduct(Product_24162098 product);
    void setProductStatus(int productId, boolean active);
    boolean deleteProduct(int productId);
}
