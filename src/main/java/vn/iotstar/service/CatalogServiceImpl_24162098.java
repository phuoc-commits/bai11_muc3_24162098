package vn.iotstar.service;

import java.util.List;
import vn.iotstar.dao.CatalogDaoImpl_24162098;
import vn.iotstar.dao.ICatalogDao_24162098;
import vn.iotstar.entity.Category_24162098;
import vn.iotstar.entity.Product_24162098;
import vn.iotstar.entity.ProductCategory_24162098;

public class CatalogServiceImpl_24162098 implements ICatalogService_24162098 {
    private final ICatalogDao_24162098 catalogDao = new CatalogDaoImpl_24162098();

    @Override public List<Category_24162098> findAllCategories() { return catalogDao.findAllCategories(); }
    @Override public List<Category_24162098> findActiveCategories() { return catalogDao.findActiveCategories(); }
    @Override public Category_24162098 findCategory(int categoryId) { return catalogDao.findCategoryById(categoryId); }
    @Override public void saveCategory(Category_24162098 category) { catalogDao.saveCategory(category); }
    @Override public void setCategoryStatus(int categoryId, boolean active) { catalogDao.setCategoryStatus(categoryId, active); }
    @Override public boolean deleteCategory(int categoryId) { return catalogDao.deleteCategory(categoryId); }
    @Override public List<ProductCategory_24162098> findAllProductCategories() { return catalogDao.findAllProductCategories(); }
    @Override public List<ProductCategory_24162098> findActiveProductCategories() { return catalogDao.findActiveProductCategories(); }
    @Override public ProductCategory_24162098 findProductCategory(int categoryId) { return catalogDao.findProductCategoryById(categoryId); }
    @Override public void saveProductCategory(ProductCategory_24162098 category) { catalogDao.saveProductCategory(category); }
    @Override public void setProductCategoryStatus(int categoryId, boolean active) { catalogDao.setProductCategoryStatus(categoryId, active); }
    @Override public boolean deleteProductCategory(int categoryId) { return catalogDao.deleteProductCategory(categoryId); }
    @Override public List<Product_24162098> findAllProducts() { return catalogDao.findAllProducts(); }
    @Override public Product_24162098 findProduct(int productId) { return catalogDao.findProductById(productId); }
    @Override public void saveProduct(Product_24162098 product) { catalogDao.saveProduct(product); }
    @Override public void setProductStatus(int productId, boolean active) { catalogDao.setProductStatus(productId, active); }
    @Override public boolean deleteProduct(int productId) { return catalogDao.deleteProduct(productId); }
}
