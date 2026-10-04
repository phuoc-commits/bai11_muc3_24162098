CREATE DATABASE IF NOT EXISTS WebDB_24162098 CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE WebDB_24162098;

CREATE TABLE Users (
    Username VARCHAR(50) PRIMARY KEY,
    Password VARCHAR(50) NOT NULL,
    Phone VARCHAR(15),
    Fullname VARCHAR(50),
    Email VARCHAR(150),
    Admin BIT DEFAULT 0,
    Active BIT DEFAULT 1,
    Images VARCHAR(500)
);

CREATE TABLE Category (
    CategoryId INT AUTO_INCREMENT PRIMARY KEY,
    Categoryname NVARCHAR(100),
    Categorycode NVARCHAR(100),
    Images NVARCHAR(500),
    Status BIT DEFAULT 1
);

CREATE TABLE Videos (
    VideoId VARCHAR(50) PRIMARY KEY,
    Title NVARCHAR(200),
    Poster NVARCHAR(50),
    Views INT DEFAULT 0,
    Description NVARCHAR(500),
    Active BIT DEFAULT 1,
    CategoryId INT,
    FOREIGN KEY (CategoryId) REFERENCES Category(CategoryId) ON DELETE SET NULL
);

CREATE TABLE Shares (
    ShareId INT AUTO_INCREMENT PRIMARY KEY,
    Emails NVARCHAR(50),
    SharedDate DATE,
    Username VARCHAR(50),
    VideoId VARCHAR(50),
    FOREIGN KEY (Username) REFERENCES Users(Username) ON DELETE CASCADE,
    FOREIGN KEY (VideoId) REFERENCES Videos(VideoId) ON DELETE CASCADE
);

CREATE TABLE Favorites (
    FavoriteId INT AUTO_INCREMENT PRIMARY KEY,
    LikedDate DATE,
    VideoId VARCHAR(50),
    Username VARCHAR(50),
    FOREIGN KEY (Username) REFERENCES Users(Username) ON DELETE CASCADE,
    FOREIGN KEY (VideoId) REFERENCES Videos(VideoId) ON DELETE CASCADE
);

-- Thêm dữ liệu mẫu test
INSERT INTO Category (Categoryname, Categorycode, Status) VALUES 
('Lập trình', 'lap-trinh', 1),
('Âm nhạc', 'am-nhac', 1);

INSERT INTO Users (Username, Password, Fullname, Email, Admin, Active) VALUES 
('admin', '123', 'Quản trị viên', 'admin@gmail.com', 1, 1),
('user1', '123', 'Nguyễn Văn A', 'user1@gmail.com', 0, 1);

INSERT INTO Videos (VideoId, Title, Poster, Views, Description, Active, CategoryId) VALUES 
('V01', 'Học Java Servlet', 'poster1.jpg', 15, 'Mô tả bài học Java Servlet', 1, 1),
('V02', 'Học Spring Boot', 'poster2.jpg', 20, 'Mô tả bài học Spring Boot', 1, 1),
('V03', 'Nhạc Trẻ Hot', 'poster3.jpg', 5, 'Video âm nhạc hot', 1, 2);