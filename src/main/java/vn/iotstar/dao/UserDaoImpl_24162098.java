package vn.iotstar.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import vn.iotstar.connection.DBConnect_24162098;
import vn.iotstar.entity.User_24162098;

public class UserDaoImpl_24162098 implements IUserDao_24162098 {

    @Override
    public User_24162098 findByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE Username = ?";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User_24162098(
                    rs.getString("Username"),
                    rs.getString("Password"),
                    rs.getString("Phone"),
                    rs.getString("Fullname"),
                    rs.getString("Email"),
                    rs.getBoolean("Admin"),
                    rs.getBoolean("Active"),
                    rs.getString("Images")
                );
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public void insert(User_24162098 user) {
        String sql = "INSERT INTO Users(Username, Password, Phone, Fullname, Email, Admin, Active, Images) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getFullname());
            ps.setString(5, user.getEmail());
            ps.setBoolean(6, user.isAdmin());
            ps.setBoolean(7, user.isActive());
            ps.setString(8, user.getImages());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void update(User_24162098 user) {
        String sql = "UPDATE Users SET Password=?, Phone=?, Fullname=?, Email=?, Admin=?, Active=?, Images=? WHERE Username=?";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getPhone());
            ps.setString(3, user.getFullname());
            ps.setString(4, user.getEmail());
            ps.setBoolean(5, user.isAdmin());
            ps.setBoolean(6, user.isActive());
            ps.setString(7, user.getImages());
            ps.setString(8, user.getUsername());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void delete(String username) {
        String sql = "DELETE FROM Users WHERE Username=?";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public List<User_24162098> findAllPaging(int page, int pageSize) {
        List<User_24162098> list = new ArrayList<>();
        String sql = "SELECT * FROM Users LIMIT ? OFFSET ?";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, (page - 1) * pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new User_24162098(
                    rs.getString("Username"),
                    rs.getString("Password"),
                    rs.getString("Phone"),
                    rs.getString("Fullname"),
                    rs.getString("Email"),
                    rs.getBoolean("Admin"),
                    rs.getBoolean("Active"),
                    rs.getString("Images")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public int countAll() {
        String sql = "SELECT COUNT(*) FROM Users";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }
}