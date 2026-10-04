package vn.iotstar.service;

import java.util.List;
import vn.iotstar.entity.User_24162098;

public interface IUserService_24162098 {
    User_24162098 login(String username, String password);
    User_24162098 findByUsername(String username);
    boolean register(User_24162098 user);
    void update(User_24162098 user);
    void delete(String username);
    List<User_24162098> findAllPaging(int page, int pageSize);
    int countAll();
    boolean sendMailOTP(String toEmail, String otpCode);
}