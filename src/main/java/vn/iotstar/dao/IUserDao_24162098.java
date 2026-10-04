package vn.iotstar.dao;

import java.util.List;
import vn.iotstar.entity.User_24162098;

public interface IUserDao_24162098 {
    User_24162098 findByUsername(String username);
    void insert(User_24162098 user);
    void update(User_24162098 user);
    void delete(String username);
    List<User_24162098> findAllPaging(int page, int pageSize);
    int countAll();
}