USE WebDB_24162098;

CREATE TABLE IF NOT EXISTS Products (
    ProductId INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(150) NOT NULL,
    Description TEXT,
    Price DECIMAL(12, 2) NOT NULL,
    Stock INT NOT NULL DEFAULT 0,
    Image VARCHAR(255),
    ProductCategoryId INT NULL,
    Active BIT NOT NULL DEFAULT 1,
    CHECK (Price >= 0),
    CHECK (Stock >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS CustomerOrders (
    OrderId BIGINT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(50) COLLATE utf8mb4_unicode_ci NOT NULL,
    OrderDate DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    TotalAmount DECIMAL(14, 2) NOT NULL,
    PaymentMethod VARCHAR(20) NOT NULL DEFAULT 'COD',
    Status VARCHAR(30) NOT NULL DEFAULT 'NEW',
    RecipientName VARCHAR(100) NOT NULL,
    Phone VARCHAR(20) NOT NULL,
    ShippingAddress VARCHAR(500) NOT NULL,
    FOREIGN KEY (Username) REFERENCES Users(Username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

UPDATE CustomerOrders SET Status = 'NEW' WHERE Status = 'PENDING';
ALTER TABLE CustomerOrders ALTER COLUMN Status SET DEFAULT 'NEW';

CREATE TABLE IF NOT EXISTS CustomerOrderItems (
    OrderItemId BIGINT AUTO_INCREMENT PRIMARY KEY,
    OrderId BIGINT NOT NULL,
    ProductId INT NULL,
    ProductName VARCHAR(150) NOT NULL,
    UnitPrice DECIMAL(12, 2) NOT NULL,
    Quantity INT NOT NULL,
    FOREIGN KEY (OrderId) REFERENCES CustomerOrders(OrderId) ON DELETE CASCADE,
    FOREIGN KEY (ProductId) REFERENCES Products(ProductId) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO Products (Name, Description, Price, Stock, Image, Active)
SELECT 'Tai nghe Bluetooth', 'Tai nghe không dây, pin bền và gọn nhẹ.', 590000, 12, 'product-headphones.svg', 1
WHERE NOT EXISTS (SELECT 1 FROM Products WHERE Name = 'Tai nghe Bluetooth');

INSERT INTO Products (Name, Description, Price, Stock, Image, Active)
SELECT 'Bàn phím cơ', 'Bàn phím cơ switch tactile, bố cục gọn.', 890000, 8, 'product-keyboard.svg', 1
WHERE NOT EXISTS (SELECT 1 FROM Products WHERE Name = 'Bàn phím cơ');

INSERT INTO Products (Name, Description, Price, Stock, Image, Active)
SELECT 'Bình nước thể thao', 'Bình giữ nhiệt dung tích 700 ml.', 250000, 20, 'product-bottle.svg', 1
WHERE NOT EXISTS (SELECT 1 FROM Products WHERE Name = 'Bình nước thể thao');

CREATE TABLE IF NOT EXISTS ProductCategories (
    ProductCategoryId INT AUTO_INCREMENT PRIMARY KEY,
    Name VARCHAR(100) NOT NULL,
    Code VARCHAR(100) NOT NULL UNIQUE,
    Description VARCHAR(500),
    Active BIT NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET @product_category_column_exists = (
    SELECT COUNT(*) FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'Products' AND COLUMN_NAME = 'ProductCategoryId'
);
SET @product_category_column_sql = IF(
    @product_category_column_exists = 0,
    'ALTER TABLE Products ADD COLUMN ProductCategoryId INT NULL AFTER Image',
    'SELECT 1'
);
PREPARE product_category_column_stmt FROM @product_category_column_sql;
EXECUTE product_category_column_stmt;
DEALLOCATE PREPARE product_category_column_stmt;

INSERT INTO ProductCategories (Name, Code, Description, Active)
SELECT 'Phụ kiện công nghệ', 'technology-accessories', 'Tai nghe, bàn phím và phụ kiện công nghệ.', 1
WHERE NOT EXISTS (SELECT 1 FROM ProductCategories WHERE Code = 'technology-accessories');

INSERT INTO ProductCategories (Name, Code, Description, Active)
SELECT 'Đồ dùng cá nhân', 'personal-items', 'Đồ dùng tiện ích cho sinh hoạt hàng ngày.', 1
WHERE NOT EXISTS (SELECT 1 FROM ProductCategories WHERE Code = 'personal-items');

UPDATE Products
SET ProductCategoryId = (SELECT ProductCategoryId FROM ProductCategories WHERE Code = 'personal-items')
WHERE ProductCategoryId IS NULL AND Name = 'Bình nước thể thao';

UPDATE Products
SET ProductCategoryId = (SELECT ProductCategoryId FROM ProductCategories WHERE Code = 'technology-accessories')
WHERE ProductCategoryId IS NULL;

ALTER TABLE Products MODIFY ProductCategoryId INT NOT NULL;

SET @product_category_fk_exists = (
    SELECT COUNT(*) FROM information_schema.REFERENTIAL_CONSTRAINTS
    WHERE CONSTRAINT_SCHEMA = DATABASE() AND CONSTRAINT_NAME = 'fk_products_product_category'
);
SET @product_category_fk_sql = IF(
    @product_category_fk_exists = 0,
    'ALTER TABLE Products ADD CONSTRAINT fk_products_product_category FOREIGN KEY (ProductCategoryId) REFERENCES ProductCategories(ProductCategoryId) ON DELETE RESTRICT ON UPDATE CASCADE',
    'SELECT 1'
);
PREPARE product_category_fk_stmt FROM @product_category_fk_sql;
EXECUTE product_category_fk_stmt;
DEALLOCATE PREPARE product_category_fk_stmt;
