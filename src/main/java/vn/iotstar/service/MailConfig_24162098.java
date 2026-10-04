package vn.iotstar.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class MailConfig_24162098 {
    private MailConfig_24162098() {}

    public static Properties load() throws IOException {
        Properties config = new Properties();
        try (InputStream input = MailConfig_24162098.class.getClassLoader().getResourceAsStream("mail.properties")) {
            if (input != null) config.load(input);
        }

        String username = System.getenv("MAIL_USERNAME");
        String password = System.getenv("MAIL_APP_PASSWORD");
        if (username != null && !username.isBlank()) config.setProperty("mail.username", username);
        if (password != null && !password.isBlank()) config.setProperty("mail.app-password", password);
        return config;
    }
}
