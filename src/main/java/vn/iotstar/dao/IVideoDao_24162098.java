package vn.iotstar.dao;

import java.util.List;
import vn.iotstar.entity.Video_24162098;

public interface IVideoDao_24162098 {
    Video_24162098 findById(String videoId);
    List<Video_24162098> findByCategoryIdPaging(int categoryId, int page, int pageSize);
    int countByCategoryId(int categoryId);
    int countTotalVideosByCategoryId(int categoryId); // Tương ứng Câu 6
    boolean isFavorite(String videoId, String username);
    void addFavorite(String videoId, String username);
    void removeFavorite(String videoId, String username);
    void addShare(String videoId, String username, String email);
}