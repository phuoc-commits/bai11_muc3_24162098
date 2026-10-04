package vn.iotstar.service;

import java.util.List;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import vn.iotstar.dao.IVideoDao_24162098;
import vn.iotstar.dao.VideoDaoImpl_24162098;
import vn.iotstar.entity.Video_24162098;

public class VideoServiceImpl_24162098 implements IVideoService_24162098 {
    private IVideoDao_24162098 videoDao = new VideoDaoImpl_24162098();

    @Override
    public Video_24162098 findById(String videoId) { return videoDao.findById(videoId); }

    @Override
    public List<Video_24162098> findByCategoryIdPaging(int categoryId, int page, int pageSize) {
        return videoDao.findByCategoryIdPaging(categoryId, page, pageSize);
    }

    @Override
    public int countByCategoryId(int categoryId) { return videoDao.countByCategoryId(categoryId); }

    @Override
    public int countTotalVideosByCategoryId(int categoryId) { return videoDao.countTotalVideosByCategoryId(categoryId); }

    @Override
    public boolean isFavorite(String videoId, String username) {
        return videoDao.isFavorite(videoId, username);
    }

    @Override
    public boolean toggleFavorite(String videoId, String username) {
        if (videoDao.isFavorite(videoId, username)) {
            videoDao.removeFavorite(videoId, username);
            return false;
        }
        videoDao.addFavorite(videoId, username);
        return true;
    }

    @Override
    public boolean shareVideo(String videoId, String username, String email) {
        Video_24162098 video = findById(videoId);
        if (video == null || email == null || email.trim().isEmpty()) return false;
        try {
            Properties props = MailConfig_24162098.load();
            String fromEmail = props.getProperty("mail.username");
            String password = props.getProperty("mail.app-password");
            if (fromEmail == null || password == null || password.equals("CHANGE_ME")) return false;
            props.put("mail.smtp.host", props.getProperty("mail.smtp.host", "smtp.gmail.com"));
            props.put("mail.smtp.port", props.getProperty("mail.smtp.port", "587"));
            props.put("mail.smtp.auth", props.getProperty("mail.smtp.auth", "true"));
            props.put("mail.smtp.starttls.enable", props.getProperty("mail.smtp.starttls", "true"));
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            });
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(email.trim()));
            message.setSubject("Ban duoc chia se mot video");
            message.setText("Video: " + video.getTitle() + "\n\n" + video.getDescription());
            Transport.send(message);
            videoDao.addShare(videoId, username, email.trim());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}