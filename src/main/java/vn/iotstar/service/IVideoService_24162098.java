package vn.iotstar.service;

import java.util.List;
import vn.iotstar.entity.Video_24162098;

public interface IVideoService_24162098 {
    Video_24162098 findById(String videoId);
    List<Video_24162098> findByCategoryIdPaging(int categoryId, int page, int pageSize);
    int countByCategoryId(int categoryId);
    int countTotalVideosByCategoryId(int categoryId);
    boolean isFavorite(String videoId, String username);
    boolean toggleFavorite(String videoId, String username);
    boolean shareVideo(String videoId, String username, String email);
}