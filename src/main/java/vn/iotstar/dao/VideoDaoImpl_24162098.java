package vn.iotstar.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import vn.iotstar.connection.DBConnect_24162098;
import vn.iotstar.entity.Video_24162098;

public class VideoDaoImpl_24162098 implements IVideoDao_24162098 {

    @Override
    public Video_24162098 findById(String videoId) {
        String sql = "SELECT v.*, c.Categoryname, " +
                     "(SELECT COUNT(*) FROM Shares s WHERE s.VideoId = v.VideoId) AS ShareCount, " +
                     "(SELECT COUNT(*) FROM Favorites f WHERE f.VideoId = v.VideoId) AS LikeCount " +
                     "FROM Videos v LEFT JOIN Category c ON v.CategoryId = c.CategoryId " +
                     "WHERE v.VideoId = ?";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, videoId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Video_24162098 v = new Video_24162098();
                v.setVideoId(rs.getString("VideoId"));
                v.setTitle(rs.getString("Title"));
                v.setPoster(rs.getString("Poster"));
                v.setViews(rs.getInt("Views"));
                v.setDescription(rs.getString("Description"));
                v.setActive(rs.getBoolean("Active"));
                v.setCategoryId(rs.getInt("CategoryId"));
                v.setCategoryName(rs.getString("Categoryname"));
                v.setShareCount(rs.getInt("ShareCount"));
                v.setLikeCount(rs.getInt("LikeCount"));
                return v;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    @Override
    public List<Video_24162098> findByCategoryIdPaging(int categoryId, int page, int pageSize) {
        List<Video_24162098> list = new ArrayList<>();
        String sql = "SELECT v.*, c.Categoryname, " +
                     "(SELECT COUNT(*) FROM Shares s WHERE s.VideoId = v.VideoId) AS ShareCount, " +
                     "(SELECT COUNT(*) FROM Favorites f WHERE f.VideoId = v.VideoId) AS LikeCount " +
                     "FROM Videos v LEFT JOIN Category c ON v.CategoryId = c.CategoryId " +
                     "WHERE v.CategoryId = ? AND c.Status = 1 LIMIT ? OFFSET ?";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ps.setInt(2, pageSize);
            ps.setInt(3, (page - 1) * pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Video_24162098 v = new Video_24162098();
                v.setVideoId(rs.getString("VideoId"));
                v.setTitle(rs.getString("Title"));
                v.setPoster(rs.getString("Poster"));
                v.setViews(rs.getInt("Views"));
                v.setDescription(rs.getString("Description"));
                v.setActive(rs.getBoolean("Active"));
                v.setCategoryId(rs.getInt("CategoryId"));
                v.setCategoryName(rs.getString("Categoryname"));
                v.setShareCount(rs.getInt("ShareCount"));
                v.setLikeCount(rs.getInt("LikeCount"));
                list.add(v);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    @Override
    public int countByCategoryId(int categoryId) {
        String sql = "SELECT COUNT(*) FROM Videos v JOIN Category c ON v.CategoryId = c.CategoryId WHERE v.CategoryId = ? AND c.Status = 1";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    @Override
    public int countTotalVideosByCategoryId(int categoryId) {
        return countByCategoryId(categoryId);
    }

    @Override
    public boolean isFavorite(String videoId, String username) {
        String sql = "SELECT 1 FROM Favorites WHERE VideoId = ? AND Username = ?";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, videoId);
            ps.setString(2, username);
            return ps.executeQuery().next();
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    @Override
    public void addFavorite(String videoId, String username) {
        String sql = "INSERT INTO Favorites(LikedDate, VideoId, Username) VALUES(CURDATE(), ?, ?)";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, videoId);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void removeFavorite(String videoId, String username) {
        String sql = "DELETE FROM Favorites WHERE VideoId = ? AND Username = ?";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, videoId);
            ps.setString(2, username);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @Override
    public void addShare(String videoId, String username, String email) {
        String sql = "INSERT INTO Shares(Emails, SharedDate, Username, VideoId) VALUES(?, CURDATE(), ?, ?)";
        try (Connection conn = DBConnect_24162098.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, username);
            ps.setString(3, videoId);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}