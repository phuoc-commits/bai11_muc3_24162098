package vn.iotstar.service;

import java.util.List;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

import vn.iotstar.dao.IUserDao_24162098;
import vn.iotstar.dao.UserDaoImpl_24162098;
import vn.iotstar.entity.User_24162098;

public class UserServiceImpl_24162098 implements IUserService_24162098 {
    private IUserDao_24162098 userDao = new UserDaoImpl_24162098();

    @Override
    public User_24162098 login(String username, String password) {
        User_24162098 user = userDao.findByUsername(username);
        if (user != null && user.getPassword().equals(password) && user.isActive()) {
            return user;
        }
        return null;
    }

    @Override
    public User_24162098 findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public boolean register(User_24162098 user) {
        if (userDao.findByUsername(user.getUsername()) != null) return false;
        userDao.insert(user);
        return true;
    }

    @Override
    public void update(User_24162098 user) { userDao.update(user); }

    @Override
    public void delete(String username) { userDao.delete(username); }

    @Override
    public List<User_24162098> findAllPaging(int page, int pageSize) {
        return userDao.findAllPaging(page, pageSize);
    }

    @Override
    public int countAll() { return userDao.countAll(); }

    @Override
    public boolean sendMailOTP(String toEmail, String otpCode) {
        try {
            Properties props = MailConfig_24162098.load();
            String fromEmail = props.getProperty("mail.username");
            String password = props.getProperty("mail.app-password");
            if (fromEmail == null || password == null || password.equals("CHANGE_ME")) return false;
            props.put("mail.smtp.host", props.getProperty("mail.smtp.host", "smtp.gmail.com"));
            props.put("mail.smtp.port", props.getProperty("mail.smtp.port", "587"));
            props.put("mail.smtp.auth", props.getProperty("mail.smtp.auth", "true"));
            props.put("mail.smtp.starttls.enable", props.getProperty("mail.smtp.starttls", "true"));

            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };

            Session session = Session.getInstance(props, auth);

            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(fromEmail));
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            msg.setSubject("Mã xác thực OTP đăng ký tài khoản");
            msg.setText("Mã OTP kích hoạt tài khoản của bạn là: " + otpCode);
            Transport.send(msg);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}